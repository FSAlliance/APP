package com.mobile.fsaliance.supers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseFragmentController;
import com.mobile.fsaliance.common.common.AppMacro;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.StatusBarUtil;
import com.mobile.fsaliance.common.util.T;

import com.mobile.fsaliance.common.vo.Good;
import com.mobile.fsaliance.goods.MfrmGoodsInfoController;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


public class MfrmSuperVouchersController extends BaseFragmentController implements
		MfrmSuperVouchersView.MfrmMineAssetDelegate, OnResponseListener {
	private MfrmSuperVouchersView mfrmSuperVouchersView;
	private RequestQueue queue;
	private static final int GET_ASSET_LIST = 0;
	private Object cancelObject = new Object();
	private List<Good> goodsList;
	private boolean refreshList = false;
	private boolean loadMoreList = false;
	private boolean mHasLoadedOnce;
	private boolean isPrepared;

	private static final int PAGE_SIZE = 10;//每页数据条数
	private static final int FIRST_PAGE = 0;//第几页
	private int pageNo = 0;
	private int lastCount = 0;//上次请求数据个数

	@Override
	protected View onCreateViewFunc(LayoutInflater inflater,
									ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_super_vouchers_controller,null);
		mfrmSuperVouchersView = (MfrmSuperVouchersView) view.findViewById(R.id.mfrm_super_vouchers_view);
		mfrmSuperVouchersView.setDelegate(this);
		queue = NoHttp.newRequestQueue();
		goodsList = new ArrayList<>();
		isPrepared = true;
		refreshList = false;
		loadMoreList = false;
		lazyLoad();
		return view;
	}




	/**
	 * @author tanyadong
	 * @Title getSearchAssetData
	 * @Description 获取搜索数据
	 * @date 2017/9/9 10:42
	 */
	private void getCustomGoodsData(long adzoneid, long pageNo) {
		String uri = AppMacro.REQUEST_IP_PORT + AppMacro.REQUEST_GOODS_PATH + "/Goods/Custom";
		Request<String> request = NoHttp.createStringRequest(uri);
		request.cancelBySign(cancelObject);
		request.add("adzoneid", adzoneid);
		request.add("platform", AppMacro.PLATFORM);
		request.add("pageNo", pageNo);
		request.add("pageSize", PAGE_SIZE);
		queue.add(0, request, this);
	}

	@Override
	protected void getBundleData() {

	}

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible || mHasLoadedOnce) {
			return;
		}
		L.e("tyd---bbbbbbbb");
		getCustomGoodsData(AppMacro.ADZONEID, FIRST_PAGE);
		mHasLoadedOnce = true;
	}


	/**
	 * @author tanyadong
	 * @Title: onResume
	 * @Description: 生命周期重进入
	 * @date 2016-9-19 下午6:57:28
	 */
	@Override
	public void onResume() {
		super.onResume();
		lazyLoad();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		queue.cancelBySign(cancelObject);
	}

	/**
	 * @param asset 数据
	 * @author yuanxueyuan
	 * @Title: onClickToDetail
	 * @Description: 点击详情
	 * @date 2017/12/26 22:08
	 */
	@Override
	public void onClickToDetail(Good asset) {
		if (goodsList == null || goodsList.size() <= 0) {
			L.e("assetList == null ");
			return;
		}
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		intent.setClass(context, MfrmGoodsInfoController.class);
		bundle.putSerializable("good", asset);
		intent.putExtras(bundle);
		startActivity(intent);
	}


	 /**
	 * @author  yuanxueyuan
	 * @Title:  pullDownRefresh
	 * @Description: 下拉刷新
	 * @date 2017/12/26 22:09
	 */
	@Override
	public void pullDownRefresh() {
		refreshList = true;
		getCustomGoodsData(AppMacro.ADZONEID, FIRST_PAGE);
	}


	/**
	 * @author yuanxueyuan
	 * @Title: onClickLoadMore
	 * @Description: 上拉加载更多
	 * @date 2017/12/26 22:09
	 */
	@Override
	public void onClickLoadMore() {
		loadMoreList = true;
		getCustomGoodsData(AppMacro.ADZONEID, pageNo);
	}

	@Override
	public void onStart(int i) {
		if (refreshList == true || loadMoreList == true) {
			return;
		}
		if (mfrmSuperVouchersView.circleProgressBarView != null) {
			mfrmSuperVouchersView.circleProgressBarView.showProgressBar();
		}
	}

	@Override
	public void onSucceed(int i, Response response) {
		if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
			String result = (String) response.get();
			goodsList = analyzeAssetsData(result);
			mfrmSuperVouchersView.showMyAssetList(goodsList);
		}
	}

	/**
	 * @author tanyadong
	 * @Title analyzeAssetsData
	 * @Description 解析查询到的资产
	 * @date 2017/9/9 20:57
	 */
	private List<Good> analyzeAssetsData(String result) {
		if (!loadMoreList) {
			if (goodsList != null) {
				goodsList.clear();
			}
		}
		if (null == result || "".equals(result)) {
			T.showShort(context, R.string.get_goods_failed);
			reloadNoDataList();
			L.e("result == null");
			return null;
		}
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("tbk_dg_item_coupon_get_response")) {
				JSONObject optJSONObject = jsonObject.optJSONObject("tbk_dg_item_coupon_get_response");
				if (optJSONObject == null) {
					return null;
				}
				JSONObject jsonObjectResult= optJSONObject.optJSONObject("results");
				if (jsonObjectResult == null) {
					return null;
				}
				JSONArray jsonArray = jsonObjectResult.optJSONArray("tbk_coupon");
				mfrmSuperVouchersView.isLoadMore = true;
				if (jsonArray.length() <= 0) {
					if (loadMoreList) {
						mfrmSuperVouchersView.isLoadMore = false;
						T.showShort(getActivity(), R.string.check_asset_no_more);
					} else {
						reloadNoDataList();
					}
					return null;
				} else {
					pageNo++;
					mfrmSuperVouchersView.setNoDataView(false);
				}
				if (goodsList == null){
					goodsList = new ArrayList<>();
				}
//			if (jsonObject.has("code") && jsonObject.optInt("code") == 0) {
//				JSONArray jsonArray = jsonObject.optJSONArray("content");
//				mfrmSuperVouchersView.isLoadMore = true;
//				if (jsonArray.length() <= 0) {
//					if (loadMoreList) {
//						mfrmSuperVouchersView.isLoadMore = false;
//						T.showShort(context, R.string.check_asset_no_more);
//					} else {
//						reloadNoDataList();
//					}
//					return null;
//				} else {
//					mfrmSuperVouchersView.setNoDataView(false);
//				}
//				if (assetList == null){
//					assetList = new ArrayList<>();
//				}
//				int arrCount = 0;
//				if (assetList != null) {
//					arrCount = assetList.size();
//				}
//				if (jsonArray.length() >= PAGE_SIZE) {
//					pageNo++;
//				} else {
//					if (lastCount < PAGE_SIZE && arrCount > 0) {
//						int index = (pageNo - 1) * PAGE_SIZE;//开始从某一位移除
//						for (int i = index; i < arrCount; i++) {
//							if (i >= index && i < index + lastCount) {
//								if (index < assetList.size()){
//									assetList.remove(index);
//								}
//							}
//						}
//					}
//				}
				for (int i = 0; i < jsonArray.length(); i++) {
					Good good = new Good();
					JSONObject jsonObjectContent = jsonArray.getJSONObject(i);
					good.setCommissionRate(jsonObjectContent.optString("commission_rate"));
					good.setCouponClickUrl(jsonObjectContent.optString("coupon_click_url"));
					good.setGoodsFinalPrice(jsonObjectContent.optString("zk_final_price"));
					good.setCouponInfo(jsonObjectContent.optString("coupon_info"));
					Double price = 0.00;
					String goodCouponInfo = good.getCouponInfo();
					if (goodCouponInfo != null && !goodCouponInfo.equals("")) {
						String[] strs = goodCouponInfo.split("元");
						if (strs.length >= 2) {
							goodCouponInfo = strs[1];
							goodCouponInfo = goodCouponInfo.replace("减", "");
							price = Double.parseDouble(goodCouponInfo);
						}
					}
					if (price < 10) {
						continue;
					}
					double money = Double.parseDouble(good.getGoodsFinalPrice()) - price;
					String goodPriceStr = String.format("%.2f", money);
					good.setCouponInfo(goodCouponInfo);
					good.setGoodsFinalPrice(goodPriceStr);
					good.setCouponRemainCount(jsonObjectContent.optInt("coupon_remain_count"));
					good.setCouponTotalCount(jsonObjectContent.optInt("coupon_total_count"));
					good.setItemDescription(jsonObjectContent.optString("item_description"));
					good.setItemUrl(jsonObjectContent.optString("item_url"));
					good.setNick(jsonObjectContent.optString("nick"));
					good.setGoodsImg(jsonObjectContent.optString("pict_url"));
					good.setShopTitle(jsonObjectContent.optString("shop_title"));
					good.setGoodsTitle(jsonObjectContent.optString("title"));
					good.setVolume(jsonObjectContent.optInt("volume"));
					goodsList.add(good);
				}
				lastCount = jsonArray.length();
			} else {
				T.showShort(context, R.string.get_goods_failed);
				reloadNoDataList();
				return null;
			}
		} catch (JSONException e) {
			T.showShort(context, R.string.get_goods_failed);
			reloadNoDataList();
			e.printStackTrace();
		}
		return  goodsList;
	}


	/**
	 * @author tanyadong
	 * @Title reloadNoDataList
	 * @Description 无数据刷新列表
	 * @date 2017/9/9 20:59
	 */
	private void reloadNoDataList() {
		if (goodsList == null || goodsList.size() <= 0) {
			mfrmSuperVouchersView.setNoDataView(true);
			mfrmSuperVouchersView.showMyAssetList(goodsList);
		}
	}


	@Override
	public void onFailed(int i, Response response) {
		if (refreshList == true) {
			if (goodsList != null) {
				goodsList.clear();
			}
		}
		Exception exception = response.getException();
		reloadNoDataList();
		if (exception instanceof NetworkError) {
			T.showShort(context, R.string.network_error);
			return;
		}
		if (exception instanceof UnKnownHostError) {
			T.showShort(context, R.string.network_unknown_host_error);
			return;
		}
		if (exception instanceof SocketTimeoutException) {
			T.showShort(context, R.string.network_socket_timeout_error);
			return;
		}
		if (exception instanceof ConnectException) {
			T.showShort(context, R.string.network_error);
			return;
		}
		T.showShort(context, R.string.get_goods_failed);
	}

	@Override
	public void onFinish(int i) {
		mfrmSuperVouchersView.circleProgressBarView.hideProgressBar();
		mfrmSuperVouchersView.endRefreshLayout();
		refreshList = false;
		loadMoreList = false;
	}


}
