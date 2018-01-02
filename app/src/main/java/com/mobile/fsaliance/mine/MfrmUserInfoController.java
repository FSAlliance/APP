package com.mobile.fsaliance.mine;

import android.content.Intent;
import android.os.Bundle;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.util.LoginUtils;
import com.mobile.fsaliance.common.util.StatusBarUtil;
import com.mobile.fsaliance.common.vo.User;
import com.mobile.fsaliance.main.MfrmLoginController;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.RequestQueue;


public class MfrmUserInfoController extends BaseController implements MfrmUserInfoView.MfrmUserInfoViewDelegate {

    private MfrmUserInfoView mfrmUserInfoView;
    private Object cancelObject = new Object();
    private RequestQueue queue;
    private User user;
    @Override
    protected void getBundleData() {

    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        int result = StatusBarUtil.StatusBarLightMode(this);
        if (result != 0) {
            StatusBarUtil.initWindows(this, getResources().getColor(R.color.white));
        }
        setContentView(R.layout.activity_user_info_controller);
        mfrmUserInfoView = (MfrmUserInfoView) findViewById(R.id.activity_userinfo_view);
        mfrmUserInfoView.setDelegate(this);
        queue = NoHttp.newRequestQueue();
        user = LoginUtils.getUserInfo(this);
        if (user == null) {
            return;
        }
        mfrmUserInfoView.initData(user);
    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelBySign(cancelObject);
    }


    @Override
    public void onClickModifyHeadImg() {

    }

    @Override
    public void onClickModifyNickName() {

    }

    @Override
    public void onClickModifyPassword() {

    }

    @Override
    public void onClickBoundAlipay() {

    }

    @Override
    public void onClickClickOff() {
        Intent intent = new Intent();
        intent.setClass(this, MfrmLoginController.class);
        startActivity(intent);
        finish();
    }
}
