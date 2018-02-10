package com.mobile.fsaliance.mine;

import android.content.Intent;
import android.os.Bundle;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.common.AppMacro;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.LoginUtils;
import com.mobile.fsaliance.common.util.StatusBarUtil;
import com.mobile.fsaliance.common.util.T;
import com.mobile.fsaliance.common.vo.User;
import com.mobile.fsaliance.main.MainActivity;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;


public class MfrmBoundAlipayController extends BaseController implements MfrmBoundAlipayView.MfrmBoundAlipayViewDelegate, OnResponseListener<String> {

    private MfrmBoundAlipayView mfrmBoundAlipayView;
    private Object cancelObject = new Object();
    private RequestQueue queue;
    private User user;
    private String alipayAccount;
    private final int UPDATE_USER_ALIPAY = 0;
    @Override
    protected void getBundleData() {
        user = LoginUtils.getUserInfo(this);
//        Bundle bundle= getIntent().getExtras();
//        if (bundle == null) {
//            return;
//        }
//        user = (User) bundle.getSerializable("user");

    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        int result = StatusBarUtil.StatusBarLightMode(this);
        if (result != 0) {
            StatusBarUtil.initWindows(this, getResources().getColor(R.color.white));
        }
        setContentView(R.layout.activity_boundalipay_controller);
        mfrmBoundAlipayView = (MfrmBoundAlipayView) findViewById(R.id.activity_boundalipay_view);
        mfrmBoundAlipayView.setDelegate(this);
        queue = NoHttp.newRequestQueue();
        mfrmBoundAlipayView.initData(user);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelBySign(cancelObject);
    }

    @Override
    public void onClickBoundAlipay(String alipayAcount) {
        alipayAccount = alipayAcount;
        if (user == null) {
            T.showShort(this, R.string.bound_fail);
            L.e("user == null");
        }
        String uri = AppMacro.REQUEST_IP_PORT + AppMacro.REQUEST_GOODS_PATH+ AppMacro.REQUEST_UPDATE_USER_ALIPAY;
        Request<String> request = NoHttp.createStringRequest(uri);
        request.setCancelSign(cancelObject);
        request.add("userId", user.getId());
        request.add("alipayNum", alipayAcount);
        queue.add(UPDATE_USER_ALIPAY, request, this);
        L.e("tyd---"+request.url());
    }

    @Override
    public void onClickBack() {
        finish();
    }

    @Override
    public void onStart(int i) {
        mfrmBoundAlipayView.circleProgressBarView.showProgressBar();
    }

    @Override
    public void onSucceed(int i, Response<String> response) {
        if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
            String result = (String) response.get();
            if (result == null || "".equals(result)) {
                T.showShort(this, R.string.bound_fail);
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.has("ret") && jsonObject.getInt("ret") == 0) {
                    user.setAliPayAccount(alipayAccount);
                    LoginUtils.saveUserInfo(this, user);
                    Intent intent = new Intent();
                    intent.putExtra("boundAlipay", alipayAccount);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    T.showShort(this, R.string.bound_fail);
                }
            } catch (JSONException e) {
                T.showShort(this, R.string.bound_fail);
                e.printStackTrace();
            }
        } else {
            T.showShort(this, R.string.bound_fail);
        }
    }

    @Override
    public void onFailed(int i, Response<String> response) {
        mfrmBoundAlipayView.circleProgressBarView.hideProgressBar();
        Exception exception = response.getException();
        if (exception instanceof NetworkError) {
            T.showShort(this, R.string.network_error);
            return;
        }
        if (exception instanceof UnKnownHostError) {
            T.showShort(this, R.string.network_unknown_host_error);
            return;
        }
        if (exception instanceof SocketTimeoutException) {
            T.showShort(this, R.string.network_socket_timeout_error);
            return;
        }
        T.showShort(this, R.string.bound_fail);

    }

    @Override
    public void onFinish(int i) {
        mfrmBoundAlipayView.circleProgressBarView.hideProgressBar();
    }
}
