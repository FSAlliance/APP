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

/**
 * 提现界面
 */
public class MfrmWithdrawalsController extends BaseController implements MfrmWithdrawalsView.MfrmWithdrawalsViewDelegate, OnResponseListener<String> {

    private MfrmWithdrawalsView mfrmWithdrawalsView;
    private Object cancelObject = new Object();
    private RequestQueue queue;
    private User user;
    private double present;
    @Override
    protected void getBundleData() {

    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        int result = StatusBarUtil.StatusBarLightMode(this);
        if (result != 0) {
            StatusBarUtil.initWindows(this, getResources().getColor(R.color.white));
        }
        setContentView(R.layout.activity_withdrawals_controller);
        mfrmWithdrawalsView = (MfrmWithdrawalsView) findViewById(R.id.activity_withdrawals_view);
        mfrmWithdrawalsView.setDelegate(this);
        queue = NoHttp.newRequestQueue();
        user = LoginUtils.getUserInfo(this);
        if (user == null) {
            return;
        }
        mfrmWithdrawalsView.initData(user);
    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelBySign(cancelObject);
    }


    @Override
    public void onClickPresent(String presentMoneny) {
        user = LoginUtils.getUserInfo(this);
        if (user == null) {
            user = new User();
        }
        String uri = AppMacro.REQUEST_IP_PORT + AppMacro.REQUEST_GOODS_PATH + AppMacro.REQUEST_PRESENT;

        Request<String> request = NoHttp.createStringRequest(uri);
        request.setCancelSign(cancelObject);
        request.add("userId",user.getId());
        present = Double.parseDouble(presentMoneny) / 100;
        request.add("money", present);
        queue.add(0, request, this);
        L.e("tyd--"+request.url());
    }

    @Override
    public void onClickBack() {
        finish();
    }

    @Override
    public void onStart(int i) {

    }

    @Override
    public void onSucceed(int i, Response<String> response) {
        if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
            String result = (String) response.get();
            if (result == null || "".equals(result)) {
                T.showShort(this, R.string.present_fail);
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.has("ret") && jsonObject.getInt("ret") == 0) {
                    JSONObject jsonUser = jsonObject.optJSONObject("content");
                    user.setCashing((long) jsonUser.optDouble("DCashing") * 100);
                    user.setBalanceNum((long) jsonUser.optDouble("DBalanceNum") * 100);
                    LoginUtils.saveUserInfo(this, user);
                    Intent intent = new Intent(this, MfrmWalletController.class);
                    startActivity(intent);
                    finish();
                } else {
                    T.showShort(this, R.string.present_fail);
                }
            } catch (JSONException e) {
                T.showShort(this, R.string.present_fail);
                e.printStackTrace();
            }
        } else {
            T.showShort(this, R.string.present_fail);
        }
    }

    @Override
    public void onFailed(int i, Response<String> response) {
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
        T.showShort(this, R.string.present_fail);
    }

    @Override
    public void onFinish(int i) {

    }
}
