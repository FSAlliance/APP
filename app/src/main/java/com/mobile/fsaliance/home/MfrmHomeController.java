package com.mobile.fsaliance.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.fsaliance.common.base.BaseFragmentController;
import com.mobile.fsaliance.common.common.AppMacro;
import com.mobile.fsaliance.common.util.LoginUtils;
import com.mobile.fsaliance.common.util.T;
import com.mobile.fsaliance.common.vo.Asset;
import com.mobile.fsaliance.common.vo.User;
import com.mobile.tiandy.asset.R;

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

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


public class MfrmHomeController extends BaseFragmentController implements
		MfrmHomeView.MfrmMineAssetDelegate, OnResponseListener {
	private MfrmHomeView mfrmHomeView;
	private RequestQueue queue;
	private static final int GET_ASSET_LIST = 0;
	private Object cancelObject = new Object();
	private List<Asset> assetList;
	private boolean isRefresh;
	private boolean mHasLoadedOnce;
	private User user;
	private boolean isPrepared;
	@Override
	protected View onCreateViewFunc(LayoutInflater inflater,
									ViewGroup container, Bundle savedInstanceState) {
		View view = null;
		view = inflater.inflate(R.layout.fragment_home_controller,
				null);
		mfrmHomeView = (MfrmHomeView) view
				.findViewById(R.id.mfrm_home_view);
		mfrmHomeView.setDelegate(this);
		queue = NoHttp.newRequestQueue();
		assetList = new ArrayList<>();
		isPrepared = true;
		user = LoginUtils.getUserInfo(getActivity());
		return view;
	}


	/**
	  * @author tanyadong
	  * @Title getMyAsset
	  * @Description 获取我的资产
	  * @date 2017/9/8 19:07
	*/
	private void getMyAsset(String jobId) {
		String uri = AppMacro.REQUEST_URL + "/asset/list";
		Request<String> request = NoHttp.createStringRequest(uri);
		request.cancelBySign(cancelObject);
		request.add("jobid", jobId);
		queue.add(GET_ASSET_LIST, request, this);
	}

	@Override
	protected void getBundleData() {

	}

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible || mHasLoadedOnce) {
			return;
		}
		if (user == null) {
			return;
		}
//		getMyAsset(user.getJobId());
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
		isRefresh = false;
		lazyLoad();
	}


	

	
	@Override
	public void onDestroy() {
		super.onDestroy();
		queue.cancelBySign(cancelObject);
	}

	@Override
	public void onClickLogoff() {
	}

	@Override
	public void onClickToQRCode() {
	}
	/**
	  * @author tanyadong
	  * @Title pullDownRefresh
	  * @Description 下拉刷新
	  * @date 2017/9/12 17:21
	*/
	@Override
	public void pullDownRefresh() {
//		isRefresh = true;
//		if (user == null) {
//			mfrmHomeView.endRefreshLayout();
//			return;
//		}
//		getMyAsset(user.getJobId());
	}

	@Override
	public void onStart(int i) {
//		if (isRefresh) {
//			return;
//		}
//		if (mfrmHomeView.circleProgressBarView != null) {
//			mfrmHomeView.circleProgressBarView.showProgressBar();
//		}
	}

	@Override
	public void onSucceed(int i, Response response) {
//		if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
//			if (assetList != null && assetList.size() > 0) {
//				assetList.clear();
//			}
//			String result = (String) response.get();
//			if (result == null || "".equals(result)) {
//				T.showShort(context, R.string.get_myasset_failed);
//				mfrmHomeView.setNoDataView(true);
//				return;
//			}
//			assetList = analyzeMyAssetData(result);
//			mfrmHomeView.showMyAssetList(assetList);
//		}
//	}
//
//	@Override
//	public void onFailed(int i, Response response) {
//		mfrmHomeView.circleProgressBarView.hideProgressBar();
//		if (assetList != null && assetList.size() != 0) {
//			assetList.clear();
//		}
//		mfrmHomeView.setNoDataView(true);
//		Exception exception = response.getException();
//		if (exception instanceof NetworkError) {
//			T.showShort(context, R.string.network_error);
//			return;
//		}
//		if (exception instanceof UnKnownHostError) {
//			T.showShort(context, R.string.network_unknown_host_error);
//			return;
//		}
//		if (exception instanceof SocketTimeoutException) {
//			T.showShort(context, R.string.network_socket_timeout_error);
//			return;
//		}
//		T.showShort(context, R.string.get_myasset_failed);
	}

	@Override
	public void onFailed(int i, Response response) {

	}

	@Override
	public void onFinish(int i) {
//		isRefresh = false;
//		mfrmHomeView.endRefreshLayout();
//		if (mfrmHomeView.circleProgressBarView != null) {
//			mfrmHomeView.circleProgressBarView.hideProgressBar();
//		}
	}


}
