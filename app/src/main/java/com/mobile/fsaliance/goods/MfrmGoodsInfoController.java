package com.mobile.fsaliance.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;

public class MfrmGoodsInfoController extends BaseController {


    @Override
    protected void getBundleData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //TODO 具体参数
    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_goods_info_controller);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
