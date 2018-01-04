package com.mobile.fsaliance.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.util.T;

public class MfrmGoodsInfoController extends BaseController implements View.OnClickListener {

    private TextView oneKeyCopyText, goodsInfoCodeText;
    private ImageView goodsInfoImg;
    private TextView titleTxt;
    private LinearLayout backLL;


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
        setContentView(R.layout.activity_goods_info_controller);

        //初始化界面
        initView();

        //添加监听事件
        addListener();

        //初始化数据
        initValues();

    }

    /**
     * @author yuanxueyuan
     * @Title: initView
     * @Description: 初始化界面布局
     * @date 2017/12/24 23:00
     */
    private void initView() {
        titleTxt = (TextView) findViewById(R.id.txt_title_middle);
        titleTxt.setText(R.string.goods_info_title);
        backLL = (LinearLayout) findViewById(R.id.ll_title_left);
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
     * @author  yuanxueyuan
     * @Title:  getGoodCode
     * @Description: 获取商品优惠卷的
     * @date 2017/12/25 22:15
     */
    private String getGoodCode(){
        if (goodsInfoCodeText == null) {
            return "";
        }
        return goodsInfoCodeText.getText().toString().trim();
    }

    /**
     * @author yuanxueyuan
     * @Title: addListener
     * @Description: 添加监听事件
     * @date 2017/12/24 23:02
     */
    private void addListener() {
        backLL.setOnClickListener(this);
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
            case R.id.ll_title_left:
                finish();
                break;
            //一键复制
            case R.id.goods_info_copy_one_key:
                String goodCode = getGoodCode();
                if ("".equals(goodCode)) {
                    T.showShort(this, "出现错误啦");
                }
                //TODO 复制到剪切板
                break;
            default:
                break;
        }
    }
}
