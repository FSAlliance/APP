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
import com.mobile.fsaliance.common.util.T;
import com.mobile.fsaliance.common.vo.Order;
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
    private int index = 0;
    private int limit = 20;
    private static final int PUBLIC_DATA = 1;
    private static final int PUBLIC_DATA_PULLUPREFRESH = 2;
    private ArrayList<Order> orders = new ArrayList<>();
    private boolean flag = false;
    private String typeId;
    private boolean mHasLoadedOnce;
    private RequestQueue queue;
    public static TabFragment newInstance(String typeId) {
        Bundle bundle = new Bundle();
        bundle.putString("typeId", typeId);
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
        isPrepared = true;
        typeId = getArguments().getString("typeId");
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
        getOrderData();
    }

    @Override
    public void onResume() {
        super.onResume();
        flag = true;
        index = 0;
        getOrderData();
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
            adapter.updateList(orders);
            adapter.notifyDataSetChanged();
        }
    }

   /**
    * @author tanyadong
    * @Title: getOrderData
    * @Description: 获取订单数据
    * @date 2018/1/11 0011 22:41
    */

    public void getOrderData() {
        String uri = AppMacro.REQUEST_URL + "/asset/query";
        Request<String> request = NoHttp.createStringRequest(uri);
        request.cancelBySign(cancelObject);
//        request.add("param", param);
//        request.add("page", pageNo);
//        request.add("limit", PAGE_SIZE);
        queue.add(0, request, this);
        for (int i = 0; i < 20; i ++) {
            Order order = new Order();
            order.setMoney(10.0);
            order.setOrderItemTitle("aaaaaa");
            order.setOrderNumber("44444444");
            order.setOrderSellerNick("aaaaa");
            order.setOrderTime("wwwwwww");
            order.setOrderShopTitle("dianfu");
            if (i < 4) {
                order.setType(i);
            } else {
                order.setType(1);
            }

            orders.add(order);
        }
        setGridviewAdapter(orders);
    }

    /**
     * @author tanyadong
     * @Title: 上拉加载更多数据
     * @Description:
     * @date 2018/1/11 0011 22:41
     */

    private void getOrderPullUpRefresh() {
        String getPublicUrl = AppMacro.REQUEST_URL + "/asset/query";
        Request<String> request = NoHttp.createStringRequest(getPublicUrl);
        request.setCancelSign(cancelObject);
        request.add("index", index);
        request.add("limit", limit);
        request.add("typeId",typeId);
        // 添加url?key=value形式的参数
        //添加到访问队列中
        queue.add(PUBLIC_DATA_PULLUPREFRESH, request, this);
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
        index = 0;
        getOrderData();
    }

    /**
     * @author tanyadong
     * @Title:
     * @Description: 加载更多
     * @date 2018/1/12 0012 21:56
     */

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getOrderPullUpRefresh();
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
        if (null == result || "".equals(result)) {
            L.e("result == null");
            T.showShort(getActivity(), R.string.my_order_get_failed);
            return null;
        }
        ArrayList<Order> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has("ret")) {
                int ret = jsonObject.optInt("ret");
                if (ret == 0) {
                    JSONObject jsonContent = jsonObject.optJSONObject("content");

                    if (jsonContent == null || "".equals(jsonContent)) {
                        T.showShort(getActivity(), R.string.my_order_get_failed);
                        return null;
                    } else {

                        JSONArray jsonPublics = jsonContent.optJSONArray("channels");

                        for (int i = 0; i < jsonPublics.length(); i++) {
                            Order order = new Order();
//                            JSONObject jsPublic = (JSONObject) jsonPublics.get(i);
//                            publics.setHostId(jsPublic.optString("hostId"));
//                            publics.setChannelNum(jsPublic.optInt("channelNum"));
//                            publics.setUserName(jsPublic.optString("username"));
//                            publics.setPassword(AESUtil.decrypt(jsPublic.optString("password")));
//                            publics.setShareName(jsPublic.optString("shareName"));
//                            publics.setImageUrl(jsPublic.optString("imgUrl"));
                            list.add(order);
                        }
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
        try{
            switch (i) {
                case PUBLIC_DATA:
                    //200为接口能调通
                    if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
                        String result = (String) response.get();
                        orders = analyzeOrderListData(result);
                        if (orders != null) {
//                            setGridviewAdapter(publics);
                            index = orders.size();
                        }
                    }
                    break;
                case PUBLIC_DATA_PULLUPREFRESH:
                    //200为接口能调通
                    if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
                        String result = (String) response.get();
                        ArrayList<Order> list = analyzeOrderListData(result);
                        if (orders != null) {
//                            publics.addAll(list);
                            index = orders.size();
//                            setGridviewAdapter(publics);
                        }
                    }
                    break;
            }
        }catch (Exception e){
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
        super.onDestroy();
    }

    public void stopNohttp(){
        queue.cancelBySign(cancelObject);
    }
}
