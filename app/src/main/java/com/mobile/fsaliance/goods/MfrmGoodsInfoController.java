package com.mobile.fsaliance.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;

public class MfrmGoodsInfoController extends BaseController implements View.OnClickListener {

    private ImageView backImg;
    private TextView oneKeyCopyText, goodsInfoCodeText;
    private ImageView goodsInfoImg;


    @Override
    protected void getBundleData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //TODO 具体参数
        initValues();
    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_info_controller);

        initView();

        addListener();

    }

    /**
     * @author yuanxueyuan
     * @Title: initView
     * @Description: 初始化界面布局
     * @date 2017/12/24 23:00
     */
    private void initView() {
        backImg = (ImageView) findViewById(R.id.goods_info_back_img);
        oneKeyCopyText = (TextView) findViewById(R.id.goods_info_copy_one_key);
        goodsInfoImg = (ImageView) findViewById(R.id.goods_info_img);
        goodsInfoCodeText = (TextView) findViewById(R.id.goods_info_code);
    }

    /**
     * @author yuanxueyuan
     * @Title: initValues
     * @Description: 初始化数据
     * @date 2017/12/25 21:07
     */
    private void initValues() {
        goodsInfoImg.setImageResource(R.drawable.goods_price_discount);
        goodsInfoCodeText.setText("&USIHDASHDIASD");
    }

    /**
     * @author yuanxueyuan
     * @Title: addListener
     * @Description: 添加监听事件
     * @date 2017/12/24 23:02
     */
    private void addListener() {
        backImg.setOnClickListener(this);
        oneKeyCopyText.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //返回键
            case R.id.goods_info_back_img:
                finish();
                break;
            //一键复制
            case R.id.goods_info_copy_one_key:
                //TODO 一键复制
                break;
            default:
                break;
        }
    }
}
