package com.mobile.fsaliance.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.util.L;

public class MfrmSearchGoodsController extends BaseController implements View.OnClickListener {

    private final String TAG = "MfrmSearchGoodsController";
    private EditText searchEdt;
    private TextView searchText;
    private ImageView backImg;

    @Override
    protected void getBundleData() {
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
     * @Title: initValues
     * @Description: 初始化数据
     * @date 2017/12/25 21:12
     */
    private void initValues() {
        //TODO 本地获取搜索记录
    }

    /**
     * @author yuanxueyuan
     * @Title: getValues
     * @Description: 获取搜索的数据
     * @date 2017/12/25 21:13
     */
    private String getValues() {
        if (searchEdt == null) {
            return "";
        }
        return searchEdt.getText().toString().trim();
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
                String searchString = getValues();
                L.i(TAG,"searchString: "+searchString);
                //TODO 根据用户输入的搜索数据 进行搜索
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("search_goods", searchString);
                intent.putExtras(bundle);
                intent.setClass(this,MfrmSearchGoodListController.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
