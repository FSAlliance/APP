package com.mobile.fsaliance.mine;

import android.content.Intent;
import android.os.Bundle;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.LoginUtils;
import com.mobile.fsaliance.common.util.StatusBarUtil;
import com.mobile.fsaliance.common.vo.User;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.RequestQueue;


public class MfrmWalletController extends BaseController implements MfrmWalletView.MfrmWalletViewDelegate {

    private MfrmWalletView mfrmWalletView;
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
        setContentView(R.layout.activity_wallet_controller);
        mfrmWalletView = (MfrmWalletView) findViewById(R.id.activity_wallet_view);
        mfrmWalletView.setDelegate(this);
        queue = NoHttp.newRequestQueue();
        user = LoginUtils.getUserInfo(this);
        if (user == null) {
            L.e("user == null");
            return;
        }
        mfrmWalletView.initData(user);
    }


    @Override
    public void onClickBack() {
        finish();
    }

    /**
     * @author yuanxueyuan
     * @Title: onClickWalletPresent
     * @Description: 提现
     * @date 2018/1/3 21:54
     */
    @Override
    public void onClickWalletPresent() {
        if (user == null) {
            L.e("user == null");
            return;
        }
        Intent intent = new Intent();
        String myAlipayNum = user.getAliPayAccount();
        if (myAlipayNum == null || "".equals(myAlipayNum)) {
            //跳转填写支付宝界面
            intent.setClass(this, MfrmBoundAlipayController.class);
        } else {
            //跳转提现界面
            intent.setClass(this, MfrmWithdrawalsController.class);
        }
        startActivity(intent);
    }


    /**
     * @author yuanxueyuan
     * @Title: onClickWalletPresent
     * @Description: 点击收入记录
     * @date 2018/1/3 21:54
     */
    @Override
    public void onClickRecordOfIncome() {
        Intent intent = new Intent();
        intent.setClass(this, MfrmIncomeListController.class);
        startActivity(intent);
    }

    /**
     * @author yuanxueyuan
     * @Title: onClickWalletPresent
     * @Description: 点击提现记录
     * @date 2018/1/3 21:54
     */
    @Override
    public void onClickPresentRecord() {
        Intent intent = new Intent();
        intent.setClass(this, MfrmPresentRecordListController.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelBySign(cancelObject);
    }

}
