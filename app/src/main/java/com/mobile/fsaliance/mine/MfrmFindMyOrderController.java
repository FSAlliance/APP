package com.mobile.fsaliance.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.goods.MfrmSearchGoodListController;

/**
 * @author yuanxueyuan
 * @Description: 找回我的订单
 * @date 2018/1/14  11:03
 */
public class MfrmFindMyOrderController extends BaseController implements View.OnClickListener {

    private final String TAG = "MfrmFindMyOrderController";
    private EditText searchEdt;
    private TextView searchText;
    private TextView titleTxt;
    private LinearLayout backLL;

    @Override
    protected void getBundleData() {
    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_find_my_order_controller);

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
        titleTxt = (TextView) findViewById(R.id.txt_title_middle);
        titleTxt.setText(R.string.mine_my_order_find);
        searchEdt = (EditText) findViewById(R.id.find_my_order_edt);
        searchText = (TextView) findViewById(R.id.find_my_order_btn);
        backLL = (LinearLayout) findViewById(R.id.ll_title_left);
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
        backLL.setOnClickListener(this);
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
            case R.id.ll_title_left:
                finish();
                break;
            //找回订单按钮
            case R.id.find_my_order_btn:
                String searchString = getValues();
                L.i(TAG,"searchString: "+searchString);
                //TODO 根据用户输入的搜索数据 进行搜索
                break;
            default:
                break;
        }
    }

     /**
     * @author  yuanxueyuan
     * @Title:  uploadUserOrder
     * @Description: 上传用户的订单信息
     * @date 2018/1/14 11:35
     */
    private void uploadUserOrder(){

    }

     /**
     * @author  yuanxueyuan
     * @Title:  saveUserOrder
     * @Description: 保存用户信息到本地
     * @date 2018/1/14 11:35
     */
    private void saveUserOrder(){
        //TODO 保存订单后四位到本地
    }
}
