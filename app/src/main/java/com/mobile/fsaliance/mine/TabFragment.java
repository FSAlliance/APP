package com.mobile.fsaliance.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseFragmentController;
import com.mobile.fsaliance.common.common.AppMacro;
import com.mobile.fsaliance.common.common.CircleProgressBarView;
import com.mobile.fsaliance.common.common.InitApplication;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.LoginUtils;
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


import org.json.JSONArray;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import static android.view.View.VISIBLE;

/**
 * @author tanyadong
 * @Description: 订单fragment
 * @date 2018/1/11 0011 22:40
 * */

public class TabFragment extends BaseFragmentController implements BGARefreshLayout.BGARefreshLayoutDelegate, OnResponseListener<String> {
    private ListView listView;
    private BGARefreshLayout mRefreshLayout;
    public CircleProgressBarView circleProgressBarView;
    private MyOrder_all_Adapter adapter;
    private LinearLayout noDataLl;
    private View view = null;
    private boolean isPrepared;
    private static Object cancelObject = new Object();
    private static final int PUBLIC_DATA = 1;
    private static final int PUBLIC_DATA_PULLUPREFRESH = 2;
    private ArrayList<Order> orders = new ArrayList<>();
    private boolean flag = false;
    private int typeId;
    private int tempTypeId;
    private boolean mHasLoadedOnce;
    private RequestQueue queue;
    private User user;
    private final int GET_MY_ORDER = 0;
    private final int GET_MY_ORDER_MORE = 1;
    private int firstPage = AppMacro.FIRST_PAGE + 1;
    private int moreData = AppMacro.FIRST_PAGE + 2;

    public static TabFragment newInstance(int typeId) {
        Bundle bundle = new Bundle();
        bundle.putInt("typeId", typeId);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View onCreateViewFunc(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_order_all, null);
        initView();
        addListener();
        isPrepared = true;
        queue = NoHttp.newRequestQueue();
        user = LoginUtils.getUserInfo(getContext());
        isPrepared = true;
        typeId = getArguments().getInt("typeId");
        lazyLoad();
        flag = false;
        return view;
    }


    @Override
    protected void getBundleData() {

    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        tempTypeId = typeId;
        getOrderData(firstPage, typeId, user);
    }

    @Override
    public void onResume() {
        super.onResume();
        flag = true;
//        getOrderData(firstPage, typeId, user);
    }

    private void initView() {
        circleProgressBarView = (CircleProgressBarView) view.findViewById(R.id.circleProgressBarView);
        listView = (ListView) view.findViewById(R.id.listView_my_order);
        noDataLl = (LinearLayout) view.findViewById(R.id.my_order_no_date);
        initFresh();
    }

    private void addListener() {
    }

    public void setGridviewAdapter(ArrayList<Order> orderArrayList) {
        if (orderArrayList == null || orderArrayList.size() < 0) {
            L.e("publics == null || publics.size() < 0");
            return;
        }
        setNoDataTxt(orderArrayList.size());
        if (adapter == null) {
            adapter = new MyOrder_all_Adapter(getActivity(), orderArrayList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            adapter.updateList(orderArrayList);
            adapter.notifyDataSetChanged();
        }
    }

   /**
     * @author tanyadong
     * @Title: getOrderData
     * @Description: 获取订单数据
     * @date 2018/1/11 0011 22:41
     */

    public void getOrderData(int pageNo, int type,User user) {
        if (user == null) {
            //获取订单失败
            return;
        }
        String uri = AppMacro.REQUEST_IP_PORT + AppMacro.REQUEST_GOODS_PATH + AppMacro.REQUEST_GET_ORDER;
        Request<String> request = NoHttp.createStringRequest(uri);
        request.cancelBySign(cancelObject);
        request.add("orderType", type);
        request.add("userId", user.getId());
        request.add("pageNo", pageNo);
        request.add("pageSize", AppMacro.PAGE_SIZE);
        L.i("QQQQQQQQQQQ","url: "+request.url());
        queue.add(GET_MY_ORDER, request, this);
    }

    /**
     * @author tanyadong
     * @Title: getOrderData
     * @Description: 获取订单数据
     * @date 2018/1/11 0011 22:41
     */

    public void getOrderMore(int pageNo, int type,User user) {
        if (user == null) {
            //获取订单失败
            return;
        }
        String uri = AppMacro.REQUEST_IP_PORT + AppMacro.REQUEST_GOODS_PATH + AppMacro.REQUEST_GET_ORDER;
        Request<String> request = NoHttp.createStringRequest(uri);
        request.cancelBySign(cancelObject);
        request.add("orderType", type);
        request.add("userId", user.getId());
        request.add("pageNo", pageNo);
        request.add("pageSize", AppMacro.PAGE_SIZE);
        L.i("QQQQQQQQQQQ","url: "+request.url());
        queue.add(GET_MY_ORDER_MORE, request, this);
    }


    public  void setNoDataTxt(int size) {
        if (size < 1) {
            noDataLl.setVisibility(VISIBLE);
        } else {
            noDataLl.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化上下拉刷新控件
     */
    private void initFresh() {
        mRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.my_all_order_freshlayout);
        mRefreshLayout.setDelegate(this);
        //true代表开启上拉加载更多
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(this.getActivity(), true);
        mRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
    }

 /**
  * @author tanyadong
  * @Title: endRefreshLayout
  * @Description: 停止刷新
  * @date 2018/1/11 0011 22:42
  */

    public void endRefreshLayout() {
        mRefreshLayout.endLoadingMore();
        mRefreshLayout.endRefreshing();

    }
    /**
     * @author tanyadong
     * @Title:
     * @Description: 下拉刷新
     * @date 2018/1/12 0012 21:57
     */

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        orders.clear();
        flag = true;
        getOrderData(firstPage, typeId, user);
    }

    /**
     * @author tanyadong
     * @Title: onBGARefreshLayoutBeginLoadingMore
     * @Description: 加载更多
     * @date 2018/1/12 0012 21:56
     */

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getOrderMore(moreData, typeId, user);
        return true;
    }


    @Override
    public void onStart(int i) {
        switch (i) {
            case PUBLIC_DATA:
                if (flag) {
                    return;
                }
                circleProgressBarView.showProgressBar();
                break;
            default:
                break;
        }
    }

 /**
  * @author tanyadong
  * @Title: analyzeOrderListData
  * @Description: 解析订单
  * @date 2018/1/11 0011 22:33
  */

    private ArrayList<Order> analyzeOrderListData(String result) {
        ArrayList<Order> list = new ArrayList<>();
        if (null == result || "".equals(result)) {
            L.e("result == null");
            T.showShort(getActivity(), R.string.my_order_get_failed);
            return list;
        }
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has("ret")) {
                int ret = jsonObject.optInt("ret");
                if (ret == 0 || ret == -18) {
                    JSONArray orderList = jsonObject.optJSONArray("content");
                    if (orderList == null || "".equals(orderList)) {
//                        T.showShort(getActivity(), R.string.my_order_get_failed);
                        //没有数据了
                        return list;
                    }
                    for (int i = 0; i < orderList.length(); i++) {
                        JSONObject orderJson = orderList.getJSONObject(i);
                        if (orderJson == null) {
                            continue;
                        }
                        Order order = new Order();
                        double money = orderJson.optDouble("totalAlipayFeeString");
                        String title = orderJson.optString("auctionTitle");
                        String orderNumber = orderJson.optString("id");
                        String createTime = orderJson.optString("createTime");
                        String shopTitle = orderJson.optString("exnickname");
                        String earningTime = orderJson.optString("earningTime");
                        int type = orderJson.optInt("payStatus");
                        order.setMoney(money);
                        order.setOrderItemTitle(title);
                        order.setOrderNumber(orderNumber);
                        order.setOrderTime(createTime);
                        order.setOrderShopTitle(shopTitle);
                        order.setType(type);
                        order.setEarningTime(earningTime);
                        list.add(order);
                    }
                } else {
                    T.showShort(getActivity(), R.string.my_order_get_failed);
                }
            } else {
                T.showShort(getActivity(), R.string.my_order_get_failed);
            }
        } catch (Exception e) {
            e.printStackTrace();
            T.showShort(getActivity(), R.string.my_order_get_failed);
        }

        return list;
    }

    @Override
    public void onSucceed(int i, Response response) {
        try {
            switch (i) {
                case GET_MY_ORDER:
                    //200为接口能调通
                    L.i("QQQQQQQQQQQ","GET_MY_ORDER");
                    if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
                        String result = (String) response.get();
                        orders = analyzeOrderListData(result);
                        if (orders != null && orders.size() > 0) {
                            setGridviewAdapter(orders);
                        } else {
                            setNoDataTxt(0);
                        }
                    }
                    break;
                case GET_MY_ORDER_MORE:
                    //200为接口能调通
                    if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
                        String result = (String) response.get();
                        ArrayList<Order> orders = analyzeOrderListData(result);
                        if (orders != null && orders.size() > 0) {
                            this.orders.addAll(orders);
                            setGridviewAdapter(this.orders);
                            if (tempTypeId == typeId) {
                                moreData = moreData + 1;
                            }
                        } else {
                            //没有更多数据
                            T.showShort(getActivity(),R.string.my_order_get_no_more);
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            T.showShort(getActivity(), R.string.my_order_get_failed);
        }
    }

    @Override
    public void onFailed(int i, Response response) {
        Exception exception = response.getException();
        if (exception instanceof NetworkError) {
            T.showShort(InitApplication.getInstance().getApplicationContext(), R.string.network_error);
            return;
        }
        if (exception instanceof SocketTimeoutException) {
            T.showShort(InitApplication.getInstance().getApplicationContext(), R.string.network_socket_timeout_error);
            return;
        }
        if (exception instanceof UnKnownHostError) {
            T.showShort(InitApplication.getInstance().getApplicationContext(), R.string.network_unknown_host_error);
            return;
        }
        if (exception instanceof ConnectException) {
            T.showShort(InitApplication.getInstance().getApplicationContext(), R.string.network_error);
            return;
        }
        switch (i) {
            case PUBLIC_DATA:
                T.showShort(InitApplication.getInstance().getApplicationContext(), R.string.my_order_get_failed);
                break;
            case PUBLIC_DATA_PULLUPREFRESH:
                T.showShort(InitApplication.getInstance().getApplicationContext(), R.string.my_order_get_failed);
                break;
            default:
                break;
        }
//        setGridviewAdapter(publics);
    }

    @Override
    public void onFinish(int i) {
        endRefreshLayout();
        circleProgressBarView.hideProgressBar();
        mHasLoadedOnce = true;
    }



    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        stopNohttp();
        super.onDestroy();
    }

    public void stopNohttp(){
        queue.cancelBySign(cancelObject);
    }
}
