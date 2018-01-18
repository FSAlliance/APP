package com.mobile.fsaliance.mine;

import android.content.Intent;
import android.os.Bundle;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.common.AppMacro;
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
    @Override
    protected void getBundleData() {
        Bundle bundle= getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        user = (User) bundle.getSerializable("user");

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
//        user = LoginUtils.getUserInfo(this);
//        if (user == null) {
//            return;
//        }

    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelBySign(cancelObject);
    }





    @Override
    public void onClickBoundAlipay(String alipayAcount) {
        user = LoginUtils.getUserInfo(this);
        alipayAccount = alipayAcount;
        if (user == null) {
            user = new User();
        }
        LoginUtils.saveUserInfo(this, user);

        String uri = AppMacro.REQUEST_URL + "/user/login";
        Request<String> request = NoHttp.createStringRequest(uri);
        request.setCancelSign(cancelObject);
        request.add("user", "aaa");
        request.add("alipayAcount", alipayAcount);
        queue.add(0, request, this);
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
                if (jsonObject.has("code") && jsonObject.getInt("code") == 0) {
                    JSONObject jsonUser = jsonObject.optJSONObject("content");
                    if (user == null) {
                        user = new User();
                    }
                    user.setAliPayAccount(alipayAccount);
                    LoginUtils.saveUserInfo(this, user);
                    Intent intent = new Intent(this, MainActivity.class);
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
        // TODO: 2018/1/18 0018 临时数据测试 
        Intent intent = new Intent();
        intent.putExtra("boundAlipay", "aaaaa");
        setResult(RESULT_OK, intent);
        finish();
    }
}
