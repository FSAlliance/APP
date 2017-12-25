package com.mobile.fsaliance.mine;

import android.os.Bundle;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.util.LoginUtils;
import com.mobile.fsaliance.common.util.StatusBarUtil;
import com.mobile.fsaliance.common.vo.User;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.RequestQueue;


public class MfrmWithdrawalsController extends BaseController implements MfrmWithdrawalsView.MfrmWithdrawalsViewDelegate {

    private MfrmWithdrawalsView mfrmWithdrawalsView;
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


}
