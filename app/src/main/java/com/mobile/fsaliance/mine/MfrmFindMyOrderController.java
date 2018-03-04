package com.mobile.fsaliance.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.common.AppMacro;
import com.mobile.fsaliance.common.common.CircleProgressBarView;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.LoginUtils;
import com.mobile.fsaliance.common.util.StatusBarUtil;
import com.mobile.fsaliance.common.util.T;
import com.mobile.fsaliance.common.vo.Order;
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
import org.w3c.dom.Text;

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
    private LinearLayout findMyOrderStateLL,findMyOrderInfoLL;
    private ImageView findMyOrderStateImg;
    private TextView findMyOrderStateText,findMyOrderIdText, findMyOrderNameText, findMyOrderTimeText;

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
        int result = StatusBarUtil.StatusBarLightMode(this);
        if (result != 0) {
            StatusBarUtil.initWindows(this, getResources().getColor(R.color.white));
        }
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
        findMyOrderStateLL = (LinearLayout) findViewById(R.id.ll_find_my_order_state);
        findMyOrderStateImg = (ImageView) findViewById(R.id.img_find_my_order_state);
        findMyOrderStateText = (TextView) findViewById(R.id.text_find_my_order_state);
        findMyOrderInfoLL = (LinearLayout) findViewById(R.id.ll_find_my_order_info);
        findMyOrderIdText = (TextView) findViewById(R.id.text_find_my_order_id);
        findMyOrderNameText = (TextView) findViewById(R.id.text_find_my_order_name);
        findMyOrderTimeText = (TextView) findViewById(R.id.text_find_my_order_time);
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
        return searchEdt.getText().toString().trim();
    }

    /**
     * @param state 控制显隐
     * @author yuanxueyuan
     * @Title: showFindState
     * @Description: 显隐找回结果
     * @date 2018/3/3 15:29
     */
    private void showFindState(boolean state) {
        if (state) {
            findMyOrderStateLL.setVisibility(View.VISIBLE);
        } else {
            findMyOrderStateLL.setVisibility(View.GONE);
        }
    }

    /**
     * @param state 找回订单状态
     * @author yuanxueyuan
     * @Title:  setFindState
     * @Description: 设置找回订单状态
     * @date 2018/3/3 15:40
     */
    private void setFindState(boolean state) {
        //设置显示
        showFindState(true);
        //找回成功
        if (state) {
            findMyOrderStateImg.setImageResource(R.drawable.find_my_order_success);
            findMyOrderStateText.setText(R.string.find_my_order_success);
        } else {
            findMyOrderStateImg.setImageResource(R.drawable.find_my_order_error);
            findMyOrderStateText.setText(R.string.find_my_order_error);
        }
    }

    /**
     * @param state 控制显隐
     * @author yuanxueyuan
     * @Title: showFindState
     * @Description: 显隐订单详情
     * @date 2018/3/3 15:29
     */
    private void showOrderInfo(boolean state) {
        if (state) {
            findMyOrderInfoLL.setVisibility(View.VISIBLE);
        } else {
            findMyOrderInfoLL.setVisibility(View.GONE);
        }
    }

    /**
     * @param order 订单信息
     * @author yuanxueyuan
     * @Title: setMyOrder
     * @Description: 设置订单信息
     * @date 2018/3/3 15:42
     */
    private void setMyOrder(Order order) {
        if (order == null) {
            L.e("order == null");
            return;
        }
        showOrderInfo(true);
        findMyOrderIdText.setText(order.getOrderNumber());
        findMyOrderNameText.setText(order.getOrderItemTitle());
        findMyOrderTimeText.setText(order.getOrderTime());
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
        request.add("userId",  "10a9b01e-cdeb-441f-ab65-154ba1c64395");
        L.i("QQQQQQQQQQ","url: "+request.url());
        queue.add(FIND_MY_ORDER, request, this);
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
                        if (ret == 0 ) {
                            Order order = analysisOrder(jsonObject);
                            if (order == null) {
                                T.showShort(this, R.string.find_my_order_error);
                                return;
                            }
                            setFindState(true);
                            setMyOrder(order);
                        } else if (ret == AppMacro.FIND_MY_ORDER_NO_HAVE){
                            setFindState(false);
                            showOrderInfo(false);
                            findMyOrderStateText.setText(R.string.find_my_order_no_have);
                        } else if (ret == AppMacro.FIND_MY_ORDER_HAVE){
                            setFindState(false);
                            showOrderInfo(false);
                            findMyOrderStateText.setText(R.string.find_my_order_have);
                        }
                    } catch (JSONException e) {
                        setFindState(false);
                        showOrderInfo(false);
                        e.printStackTrace();
                    }
                } else {
                    setFindState(false);
                    showOrderInfo(false);
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
        setFindState(false);
        showOrderInfo(false);
    }

    @Override
    public void onFinish(int i) {
        if (circleProgressBarView != null) {
            circleProgressBarView.hideProgressBar();
        }
    }

    /**
     * @param jsonObject 返回的数据
     * @author yuanxueyuan
     * @Title: analysisOrder
     * @Description: 解析返回的数据
     * @date 2018/3/3 15:51
     */
    private Order analysisOrder(JSONObject jsonObject) {
        Order myOrder = new Order();
        if (jsonObject == null) {
            L.e("jsonObject == null");
            return myOrder;
        }
        JSONObject order = jsonObject.optJSONObject("content");
        if (order == null) {
            L.e("order == null");
            return myOrder;
        }
        String orderId = order.optString("id");
        String orderName = order.optString("auctionTitle");
        String orderTime = order.optString("createTime");
        myOrder.setOrderNumber(orderId);
        myOrder.setOrderItemTitle(orderName);
        myOrder.setOrderTime(orderTime);
        return myOrder;
    }

}
