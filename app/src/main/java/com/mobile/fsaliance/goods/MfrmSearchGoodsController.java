package com.mobile.fsaliance.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;

public class MfrmSearchGoodsController extends BaseController implements View.OnClickListener {

    private EditText searchEdt;
    private TextView searchText;
    private ImageView backImg;

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
        setContentView(R.layout.activity_search_goods_controller);

        initView();

        addListener();
    }


    /**
     * @author yuanxueyuan
     * @Title: initView
     * @Description: 初始化界面
     * @date 2017/12/24 22:41
     */
    private void initView() {
        searchEdt = (EditText) findViewById(R.id.search_goods_edt);
        searchText = (TextView) findViewById(R.id.search_goods_btn);
        backImg = (ImageView) findViewById(R.id.search_goods_back_img);
    }

    /**
     * @author yuanxueyuan
     * @Title: addListener
     * @Description: 添加监听方法
     * @date 2017/12/24 22:41
     */
    private void addListener() {
        backImg.setOnClickListener(this);
        searchText.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //返回键
            case R.id.search_goods_back_img:
                finish();
                break;
            //搜索按钮
            case R.id.search_goods_btn:
                //TODO 跳转列表界面
                break;
            default:
                break;
        }
    }
}
