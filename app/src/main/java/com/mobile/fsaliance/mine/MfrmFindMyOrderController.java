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
import com.mobile.fsaliance.common.common.AppMacro;
import com.mobile.fsaliance.common.common.CircleProgressBarView;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.LoginUtils;
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
 * @author yuanxueyuan
 * @Description: 找回我的订单
 * @date 2018/1/14  11:03
 */
public class MfrmFindMyOrderController extends BaseController implements View.OnClickListener, OnResponseListener {

    private final String TAG = "MfrmFindMyOrderController";
    private EditText searchEdt;
    private TextView searchText;
    private TextView titleTxt;
    private LinearLayout backLL;
    private CircleProgressBarView circleProgressBarView;
    private Object cancelObject = new Object();
    private RequestQueue queue;
    private User user;
    private final int FIND_MY_ORDER = 0;
    private String myOrder;//用户输入的订单后四位

    @Override
    protected void getBundleData() {
    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_find_my_order_controller);
        queue = NoHttp.newRequestQueue();

        initView();

        addListener();

        user = initValues();
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
        circleProgressBarView = (CircleProgressBarView) findViewById(R.id.find_my_order_circleProgressBarView);
    }

    /**
     * @author yuanxueyuan
     * @Title: initValues
     * @Description: 初始化数据
     * @date 2017/12/25 21:12
     */
    private User initValues() {
        return LoginUtils.getUserInfo(this);
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
        String myOrder = searchEdt.getText().toString().trim();
        if (myOrder.length() >= 4) {
            return myOrder.substring(myOrder.length() - 4, myOrder.length());
        }
        return "";
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
                myOrder = getValues();
                findMyOrder(user, myOrder);
                break;
            default:
                break;
        }
    }

     /**
     * @author  yuanxueyuan
     * @Title:  findMyOrder
     * @Description: 找回订单
     * @date 2018/1/14 11:35
     */
    private void findMyOrder(User user, String myOrder){
        if (user == null) {
            return;
        }
        if (myOrder == null || "".equals(myOrder)) {
            T.showShort(this, R.string.find_my_order_null);
            return;
        }
        String uri = AppMacro.REQUEST_IP_PORT + AppMacro.REQUEST_GOODS_PATH+ AppMacro.REQUEST_FIND_MY_ORDER;
        Request<String> request = NoHttp.createStringRequest(uri);
        request.setCancelSign(cancelObject);
        request.add("orderId",myOrder);
        request.add("userId",  user.getId());
        queue.add(FIND_MY_ORDER, request, this);
    }

     /**
     * @author  yuanxueyuan
     * @Title:  saveUserOrder
     * @Description: 保存用户信息到本地
     * @date 2018/1/14 11:35
     */
    private void saveUserOrder(User user){
        if (user == null) {
            return;
        }
        String userOrder = user.getMyOrder();
        String order = userOrder+";"+myOrder;
        user.setMyOrder(order);
        LoginUtils.saveUserInfo(this,user);
    }

    @Override
    public void onStart(int i) {
        if (circleProgressBarView != null) {
            circleProgressBarView.showProgressBar();
        }
    }

    @Override
    public void onSucceed(int i, Response response) {
        switch (i) {
            case FIND_MY_ORDER:
                if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
                    String result = (String) response.get();
                    if (result == null || "".equals(result)) {
                        T.showShort(this, R.string.find_my_order_error);
                        return;
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int ret = jsonObject.optInt("ret");
                        if (ret == 0 || ret == AppMacro.FIND_MY_ORDER_HAVE) {
                            //保存数据
                            saveUserOrder(user);
                            T.showShort(this, R.string.find_my_order_success);
                            finish();
                        } else if (ret == AppMacro.FIND_MY_ORDER_MAX){
                            T.showShort(this, R.string.find_my_order_max);
                        } else {
                            T.showShort(this, R.string.find_my_order_error);
                        }
                    } catch (JSONException e) {
                        T.showShort(this, R.string.find_my_order_error);
                        e.printStackTrace();
                    }
                } else {
                    T.showShort(this, R.string.find_my_order_error);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailed(int i, Response response) {
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
        T.showShort(this, R.string.find_my_order_error);
    }

    @Override
    public void onFinish(int i) {
        if (circleProgressBarView != null) {
            circleProgressBarView.hideProgressBar();
        }
    }
}
