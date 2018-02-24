package com.mobile.fsaliance.main;

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

public class MfrmRegisterController extends BaseController implements MfrmRegisterView.MfrmRegisterDelegate, OnResponseListener<String> {

    private MfrmRegisterView mfrmRegisterView;
    private Object cancelObject = new Object();
    private RequestQueue queue;
    @Override
    protected void getBundleData() {

    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_controller);
        int result = StatusBarUtil.StatusBarLightMode(this);
        if (result != 0) {
            StatusBarUtil.initWindows(this, getResources().getColor(R.color.white));
        }
        mfrmRegisterView = (MfrmRegisterView) findViewById(R.id.activity_register_view);
        mfrmRegisterView.setDelegate(this);
        queue = NoHttp.newRequestQueue();
    }

    /**
     * @author tanyadong
     * @Title onClickRegister
     * @Description 点击注册
     * @date 2017/9/6 22:15
     */
    @Override
    public void onClickRegister(String refereeAcount, String userName, String password) {
        User user = LoginUtils.getUserInfo(this);
        if (user == null) {
            user = new User();
        }
        user.setPassword(password);
        LoginUtils.saveUserInfo(this, user);
        String uri = AppMacro.REQUEST_IP_PORT + AppMacro.REQUEST_GOODS_PATH +AppMacro.REQUEST_REGISTER;
        Request<String> request = NoHttp.createStringRequest(uri);
        request.setCancelSign(cancelObject);
        request.add("phoneNum", userName);
        request.add("password",password);
        request.add("shareCode",refereeAcount);
        queue.add(0, request, this);
        L.e("tyd   "+request.url());
    }


    @Override
    public void onClickBack() {
        finish();
    }
    
    /**
     * @author tanyadong
     * @Title: onClickToLogin
     * @Description: 点击登录
     * @date 2017/12/22 0022 23:59
     */
    
    @Override
    public void onClickToLogin() {
        Intent intent = new Intent(this, MfrmLoginController.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onFailed(int i, Response response) {
        mfrmRegisterView.circleProgressBarView.hideProgressBar();

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
        T.showShort(this, R.string.register_failed);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelBySign(cancelObject);

    }

    @Override
    public void onStart(int i) {
        if (mfrmRegisterView.circleProgressBarView != null) {
            mfrmRegisterView.circleProgressBarView.showProgressBar();
        }
    }

    @Override
    public void onSucceed(int i, Response<String> response) {
        if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
            String result = (String) response.get();
            if (result == null || "".equals(result)) {
                T.showShort(this, R.string.register_failed);
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.has("ret") && jsonObject.getInt("ret") == 0) {
                    User user = new User();
                    JSONObject jsonObject1 = jsonObject.getJSONObject("content");
                    user.setId(jsonObject1.optString("SUserId"));
                    user.setNickName(jsonObject1.optString("SName"));
                    user.setPhoneNum(jsonObject1.optString("SPhoneNum"));
                    user.setPassword(jsonObject1.optString("SPassword"));
                    user.setShareCode(jsonObject1.optString("SInviteNum"));
                    LoginUtils.saveUserInfo(this,user);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (jsonObject.getInt("ret") == -15 ){
                    T.showShort(this, R.string.register_exist);
                } else {
                    T.showShort(this, R.string.register_failed);
                }
            } catch (JSONException e) {
                T.showShort(this, R.string.register_failed);
                e.printStackTrace();
            }
        } else {
            T.showShort(this, R.string.register_failed);
        }
    }

    @Override
    public void onFinish(int i) {
        if (mfrmRegisterView.circleProgressBarView != null) {
            mfrmRegisterView.circleProgressBarView.hideProgressBar();
        }
    }
}
