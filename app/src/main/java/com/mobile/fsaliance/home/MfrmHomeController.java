package com.mobile.fsaliance.home;

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
import com.mobile.fsaliance.common.vo.Asset;
import com.mobile.fsaliance.common.vo.Favorite;
import com.mobile.fsaliance.goods.MfrmGoodsInfoController;
import com.mobile.fsaliance.goods.MfrmSearchGoodListController;
import com.mobile.fsaliance.goods.MfrmSearchGoodsController;

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


public class MfrmHomeController extends BaseFragmentController implements
		MfrmHomeView.MfrmSearchDelegate, OnResponseListener {
	private MfrmHomeView mfrmHomeView;
	private RequestQueue queue;
	private static final int INIT = 0;
	private static final int SEARCH_ASSET_LIST = 1;
	private static final int SEARCH_ASSET_LIST_UP = 2;
	private static final int GET_FAVORITE = 3;//获取选品库
	private List<Asset> assetList;
	private int pageNo = 0;
	private static final int PAGE_SIZE = 5;//每页数据条数
	private static final int FIRST_PAGE = 0;//第几页
	private boolean refreshList = false;
	private boolean loadMoreList = false;
	private Object cancelObject = new Object();
	private int lastCount = 0;//上次请求数据个数
	private boolean mHasLoadedOnce;

	private boolean isPrepared;
	private List<Favorite> favoriteList;

	@Override
	protected View onCreateViewFunc(LayoutInflater inflater,
									ViewGroup container, Bundle savedInstanceState) {
		View view = null;
		view = inflater.inflate(R.layout.fragment_home_controller,
				null);
		mfrmHomeView = (MfrmHomeView) view
				.findViewById(R.id.mfrm_home_view);
		int result = StatusBarUtil.StatusBarLightMode(getActivity());
		if (result != 0) {
			StatusBarUtil.initWindows(getActivity(), getResources().getColor(R.color.login_btn_color));
		}
		mfrmHomeView.setDelegate(this);
		queue = NoHttp.newRequestQueue();
		assetList = new ArrayList<>();
		favoriteList = new ArrayList<>();
		isPrepared = true;
		refreshList = false;
		loadMoreList = false;
		lazyLoad();
		getFavoriteGroup();
		return view;
	}

	/**
	  * @author tanyadong
	  * @Title getSearchAssetData
	  * @Description 获取搜索数据
	  * @date 2017/9/9 10:42
	*/
	private void getCustomGoodsData(int i ,String param, int pageNo) {
		String uri = AppMacro.REQUEST_IP_PORT + AppMacro.REQUEST_GOODS_PATH + "/Goods/Custom";
		Request<String> request = NoHttp.createStringRequest(uri);
		request.cancelBySign(cancelObject);
		request.add("param", param);
		request.add("page", pageNo);
		request.add("limit", PAGE_SIZE);
		queue.add(i, request, this);
	}

	/**
	 * @author tanyadong
	 * @Title getFavoriteGroup
	 * @Description 获取选品库
	 * @date 2017/9/9 10:42
	 */
	private void getFavoriteGroup() {
		String uri = AppMacro.REQUEST_IP_PORT + AppMacro.REQUEST_GOODS_PATH + "/Goods/favoriteGroup";
		Request<String> request = NoHttp.createStringRequest(uri);
		request.cancelBySign(cancelObject);
		request.add("pageNo", 1);
		request.add("pageSize", 10);
		L.i("QQQQQQQQQQQQ","url: "+request.url());
		queue.add(GET_FAVORITE, request, this);
	}

	@Override
	protected void getBundleData() {

	}

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible || mHasLoadedOnce) {
			return;
		}
		getCustomGoodsData(INIT, "", FIRST_PAGE);
		mHasLoadedOnce = true;
	}

	@Override
	public void onResume() {
		super.onResume();
	}



	@Override
	public void onDestroy() {
		super.onDestroy();
		queue.cancelBySign(cancelObject);
	}

	@Override
	public void onClickSearch() {
		Intent intent = new Intent();
		intent.setClass(context, MfrmSearchGoodsController.class);
		startActivity(intent);
	}

	@Override
	public void onClickOne() {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		if (favoriteList == null || favoriteList.size() <= 0) {
			L.e("favoriteList == null");
			return;
		}
		bundle.putSerializable("favorite", favoriteList.get(0));
		bundle.putString("search_goods", "ONE");
		intent.putExtras(bundle);
		intent.setClass(context,MfrmSearchGoodListController.class);
		startActivity(intent);
	}

	@Override
	public void onClickTwo() {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		if (favoriteList == null || favoriteList.size() <= 1) {
			L.e("favoriteList == null");
			return;
		}
		bundle.putSerializable("favorite", favoriteList.get(1));
		bundle.putString("search_goods", "TWO");
		intent.putExtras(bundle);
		intent.setClass(context,MfrmSearchGoodListController.class);
		startActivity(intent);
	}

	@Override
	public void onClickThree() {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		if (favoriteList == null || favoriteList.size() <= 2) {
			L.e("favoriteList == null");
			return;
		}
		bundle.putSerializable("favorite", favoriteList.get(2));
		bundle.putString("search_goods", "THREE");
		intent.putExtras(bundle);
		intent.setClass(context,MfrmSearchGoodListController.class);
		startActivity(intent);
	}

	@Override
	public void onClickFour() {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		if (favoriteList == null || favoriteList.size() <= 3) {
			L.e("favoriteList == null");
			return;
		}
		bundle.putSerializable("favorite", favoriteList.get(3));
		bundle.putString("search_goods", "FOUR");
		intent.putExtras(bundle);
		intent.setClass(context,MfrmSearchGoodListController.class);
		startActivity(intent);

	}

	@Override
	public void onClickFive() {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		if (favoriteList == null || favoriteList.size() <= 4) {
			L.e("favoriteList == null");
			return;
		}
		bundle.putSerializable("favorite", favoriteList.get(4));
		bundle.putString("search_goods", "FIVE");
		intent.putExtras(bundle);
		intent.setClass(context,MfrmSearchGoodListController.class);
		startActivity(intent);

	}

	@Override
	public void onClickSix() {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		if (favoriteList == null || favoriteList.size() <= 5) {
			L.e("favoriteList == null");
			return;
		}
		bundle.putSerializable("favorite", favoriteList.get(5));
		bundle.putString("search_goods", "SIX");
		intent.putExtras(bundle);
		intent.setClass(context,MfrmSearchGoodListController.class);
		startActivity(intent);

	}

	@Override
	public void onClickSeven() {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		if (favoriteList == null || favoriteList.size() <= 6) {
			L.e("favoriteList == null");
			return;
		}
		bundle.putSerializable("favorite", favoriteList.get(6));
		bundle.putString("search_goods", "SEVEN");
		intent.putExtras(bundle);
		intent.setClass(context,MfrmSearchGoodListController.class);
		startActivity(intent);

	}

	@Override
	public void onClickEight() {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		if (favoriteList == null || favoriteList.size() <= 7) {
			L.e("favoriteList == null");
			return;
		}
		bundle.putSerializable("favorite", favoriteList.get(7));
		bundle.putString("search_goods", "EIGHT");
		intent.putExtras(bundle);
		intent.setClass(context,MfrmSearchGoodListController.class);
		startActivity(intent);

	}

	/**
	  * @author tanyadong
	  * @Title onClickPullDown
	  * @Description 下拉刷新
	  * @date 2017/9/16 14:10
	*/
	@Override
	public void onClickPullDown(String strSearch) {
		refreshList = true;
		pageNo = 0;
		getCustomGoodsData(SEARCH_ASSET_LIST, strSearch, FIRST_PAGE);
	}

	/**
	 * @author tanyadong
	 * @Title onClickLoadMore
	 * @Description 上啦加载
	 * @date 2017/9/16 14:10
	 */
	@Override
	public void onClickLoadMore(String strSearch) {
		loadMoreList = true;
		getCustomGoodsData(SEARCH_ASSET_LIST_UP, strSearch, pageNo);
	}

	@Override
	public void onClickToDetail(Asset asset) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		intent.setClass(context, MfrmGoodsInfoController.class);
		//TODO 填写具体的参数
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void onClickToDetailEx(int position) {
		if (assetList == null || assetList.size() <= 0) {
			L.e("assetList == null ");
			return;
		}

		if ((assetList.size() - 1) < position) {
			L.e("assetList == null ");
			return;
		}
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		intent.setClass(context, MfrmGoodsInfoController.class);
		//TODO 填写具体的参数
		intent.putExtras(bundle);
		startActivity(intent);

	}


	@Override
	public void onStart(int i) {
		if (refreshList == true || loadMoreList == true) {
			return;
		}
		mfrmHomeView.circleProgressBarView.showProgressBar();
	}

	@Override
	public void onSucceed(int i, Response response) {
		if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
			String result = (String) response.get();
			if (GET_FAVORITE == i) {
				L.i("QQQQQQQQQQ", "result: " + result);
				analyzeFavoriteGroup(result);
				L.i("QQQQQQQQQQQQ","List"+favoriteList.toString());
				if (mfrmHomeView == null) {
					L.e("mfrmHomeView == null");
					return;
				}
				mfrmHomeView.initFavoriteView(favoriteList);
				return;
			} else {
				assetList = analyzeAssetsData(result);
				mfrmHomeView.showSearchAssetList(assetList, i);
			}
		}
	}

	private void analyzeFavoriteGroup(String result){
		if (result == null) {
			T.showShort(context, "");
			return;
		}

		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("tbk_uatm_favorites_get_response")) {
				//成功
				JSONObject response = jsonObject.optJSONObject("tbk_uatm_favorites_get_response");
				if (response == null) {
					L.e("response == null");
					return;
				}
				JSONObject results = response.optJSONObject("results");
				if (results == null) {
					L.e("results == null");
					return;
				}
				JSONArray favoriteList = results.optJSONArray("tbk_favorites");
				if (favoriteList == null || favoriteList.length() <= 0) {
					L.e("favoriteList == null");
					return;
				}
				if (this.favoriteList == null) {
					this.favoriteList = new ArrayList<>();
				}
				for (int i = 0; i < favoriteList.length(); i++) {
					JSONObject favoriteJSON = (JSONObject) favoriteList.get(i);
					Favorite favorite = new Favorite();
					String favoriteID = favoriteJSON.optString("favorites_id");
					String favoriteName = favoriteJSON.optString("favorites_title");
					int favoriteType= favoriteJSON.optInt("type");
					if (favoriteID != null && !"".equals(favoriteID)) {
						favorite.setFavoriteID(favoriteID);
					}
					if (favoriteName != null && !"".equals(favoriteName)) {
						favorite.setFavoriteName(favoriteName);
					}
					favorite.setType(favoriteType);
					this.favoriteList.add(favorite);
				}
			} else {
				//失败
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}


	}

	/**
	  * @author tanyadong
	  * @Title analyzeAssetsData
	  * @Description 解析查询到的资产
	  * @date 2017/9/9 20:57
	*/
	private List<Asset> analyzeAssetsData(String result) {
		if (!loadMoreList) {
			if (assetList != null) {
				assetList.clear();
			}
		}
		if (null == result || "".equals(result)) {
			T.showShort(context, R.string.get_myasset_failed);
			reloadNoDataList();
			L.e("result == null");
			return null;
		}
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("code") && jsonObject.optInt("code") == 0) {
				JSONArray jsonArray = jsonObject.optJSONArray("content");
				mfrmHomeView.isLoadMore = true;
				if (jsonArray.length() <= 0) {
					if (loadMoreList) {
						mfrmHomeView.isLoadMore = false;
						T.showShort(getActivity(), R.string.check_asset_no_more);
					} else {
						reloadNoDataList();
					}
					return null;
				} else {
					pageNo++;
					mfrmHomeView.setNoDataView(false);
				}
				if (assetList == null){
					assetList = new ArrayList<>();
				}
				/*int arrCount = 0;
				if (assetList != null) {
					arrCount = assetList.size();
				}*/
				/*if (jsonArray.length() >= PAGE_SIZE) {
					pageNo++;
				} else {
					if (lastCount < PAGE_SIZE && arrCount > 0) {
						int index = (pageNo - 1) * PAGE_SIZE;//开始从某一位移除
						for (int i = index; i < arrCount; i++) {
							if (i >= index && i < index + lastCount) {
								if (index < assetList.size()){
									assetList.remove(index);
								}
							}
						}
					}
				}*/
				for (int i = 0; i < jsonArray.length(); i++) {
					Asset asset = new Asset();
					JSONObject jsonObjectContent = jsonArray.getJSONObject(i);
					asset.setState(jsonObjectContent.getInt("state"));
					asset.setType(jsonObjectContent.getString("type"));
					asset.setCodeId(jsonObjectContent.getString("codeId"));
					asset.setJobId(jsonObjectContent.getString("jobId"));
					asset.setUserName(jsonObjectContent.optString("user"));
					asset.setName(jsonObjectContent.getString("name"));
					asset.setBoard(jsonObjectContent.getString("board"));
					asset.setBox(jsonObjectContent.getString("box"));
					asset.setBuild(jsonObjectContent.getString("build"));
					asset.setCenter(jsonObjectContent.getString("center"));
					asset.setCost(jsonObjectContent.getString("cost"));
					asset.setCostIt(jsonObjectContent.getString("costIt"));
					asset.setCount(jsonObjectContent.getString("count"));
					asset.setCpu(jsonObjectContent.getString("cpu"));
					asset.setDisk(jsonObjectContent.getString("disk"));
					asset.setFloor(jsonObjectContent.getString("floor"));
					asset.setHardDriver(jsonObjectContent.getString("hardDriver"));
					asset.setLeavePlace(jsonObjectContent.getString("leavePlace"));
					asset.setMemory(jsonObjectContent.getString("memory"));
					asset.setModel(jsonObjectContent.getString("model"));
					asset.setMoney(jsonObjectContent.getString("money"));
					asset.setOther(jsonObjectContent.getString("other"));
					asset.setPart(jsonObjectContent.getString("part"));
					asset.setPlace(jsonObjectContent.getString("place"));
					asset.setRealPlace(jsonObjectContent.getString("realPlace"));
					asset.setSaver(jsonObjectContent.getString("saver"));
					asset.setRealSaver(jsonObjectContent.getString("realSaver"));
					asset.setPrice(jsonObjectContent.getString("price"));
					asset.setSoftDriver(jsonObjectContent.getString("softDriver"));
					asset.setTime(jsonObjectContent.getString("time"));
					asset.setVideoCard(jsonObjectContent.getString("videoCard"));
					assetList.add(asset);
				}
				lastCount = jsonArray.length();
			} else {
				T.showShort(context, R.string.get_myasset_failed);
				reloadNoDataList();
				return null;
			}
		} catch (JSONException e) {
			T.showShort(context, R.string.get_myasset_failed);
			reloadNoDataList();
			e.printStackTrace();
		}
		return  assetList;
	}

	/**
	  * @author tanyadong
	  * @Title reloadNoDataList
	  * @Description 无数据刷新列表
	  * @date 2017/9/9 20:59
	*/
	private void reloadNoDataList() {
		if (assetList == null || assetList.size() <= 0) {
			mfrmHomeView.setNoDataView(true);
			mfrmHomeView.showSearchAssetList(assetList, 0);
		}
	}

	@Override
	public void onFailed(int i, Response response) {
		if (refreshList == true) {
			if (assetList != null) {
				assetList.clear();
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
		T.showShort(context, R.string.login_failed);
	}

	@Override
	public void onFinish(int i) {
		mfrmHomeView.circleProgressBarView.hideProgressBar();
		mfrmHomeView.endRefreshLayout();
		refreshList = false;
		loadMoreList = false;
	}
}
