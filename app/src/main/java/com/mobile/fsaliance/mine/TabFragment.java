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
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;


import java.net.ConnectException;
import java.net.SocketTimeoutException;


import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import static android.view.View.VISIBLE;

/**
 * Created by zhangming on 2017/9/21.
 */
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
//    private ArrayList<Public> publics = new ArrayList<>();
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
        mRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.my_all_order_freshlayout);
        initFresh();
    }

    private void addListener() {
    }

//    public void setGridviewAdapter(ArrayList<Public> publics) {
//        if (publics == null || publics.size() < 0) {
//            L.e("publics == null || publics.size() < 0");
//            return;
//        }
//        setNoDataTxt(publics.size());
//        if (adapter == null) {
////            adapter = new MyOrder_all_Adapter(InitApplication.getInstance().getApplicationContext(), publics);
//            listView.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//        } else {
////            adapter.updateList(publics);
//            adapter.notifyDataSetChanged();
//        }
//    }

    /**
     * @author JDY
     * @Description: $获取公共视界初始化数据
     * @date ${tags}
     */
    public void getOrderData() {
        String uri = AppMacro.REQUEST_URL + "/asset/query";
        Request<String> request = NoHttp.createStringRequest(uri);
        request.cancelBySign(cancelObject);
//        request.add("param", param);
//        request.add("page", pageNo);
//        request.add("limit", PAGE_SIZE);
        queue.add(0, request, this);
    }

    /**
     * @author JDY
     * @Description: $获取公共视界上拉加载数据
     * @date ${tags}
     */
    private void getPublicDataPullUpRefresh() {
//        String getPublicUrl = AppMacro.WEB_SERVICE_URL + AppMacro.GET_PUBLIC_DATA_URL;
//        //获取访问类
//        NetWorkServer netWork = NetWorkServer.getInstance();
//        //拼写URL路径和请求类型, 不填写默认为Get请求
//        Request<String> request = new StringRequest(getPublicUrl);
//        request.setCancelSign(cancelObject);
//        request.add("index", index);
//        request.add("limit", limit);
//        request.add("typeId",typeId);
//        // 添加url?key=value形式的参数
//        //添加到访问队列中
//        netWork.add(PUBLIC_DATA_PULLUPREFRESH, request, this);
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
        mRefreshLayout.setDelegate(this);
        //true代表开启上拉加载更多
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(this.getActivity(), true);
        mRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
    }

    /**
     * @author JDY
     * @Description: 停止刷新
     * @date ${tags}
     */
    public void endRefreshLayout() {
        mRefreshLayout.endLoadingMore();
        mRefreshLayout.endRefreshing();

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
//        publics.clear();
        flag = true;
        index = 0;
        getOrderData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getPublicDataPullUpRefresh();
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
     * @author JDY
     * @Description: 解析公共视界数据
     * @date ${tags}
     */
//    private ArrayList<Public> analyzePublicListData(String result) {
//        if (null == result || "".equals(result)) {
//            L.e("result == null");
//            T.showShort(getActivity(), R.string.get_public_data_failed);
//            return null;
//        }
//        ArrayList<Public> list = new ArrayList<>();
//        try {
//            JSONObject jsonObject = new JSONObject(result);
//            if (jsonObject.has("ret")) {
//                int ret = jsonObject.optInt("ret");
//                if (ret == 0) {
//                    JSONObject jsonContent = jsonObject.optJSONObject("content");
//
//                    if (jsonContent == null || "".equals(jsonContent)) {
//                        T.showShort(getActivity(), R.string.get_public_data_failed);
//                        return null;
//                    } else {
//
//                        JSONArray jsonPublics = jsonContent.optJSONArray("channels");
//
//                        for (int i = 0; i < jsonPublics.length(); i++) {
//                            Public publics = new Public();
//                            JSONObject jsPublic = (JSONObject) jsonPublics.get(i);
//                            publics.setHostId(jsPublic.optString("hostId"));
//                            publics.setChannelNum(jsPublic.optInt("channelNum"));
//                            publics.setUserName(jsPublic.optString("username"));
//                            publics.setPassword(AESUtil.decrypt(jsPublic.optString("password")));
//                            publics.setShareName(jsPublic.optString("shareName"));
//                            publics.setImageUrl(jsPublic.optString("imgUrl"));
//                            list.add(publics);
//                        }
//                    }
//                } else {
//                    T.showShort(getActivity(), R.string.my_order_get_failed);
//                }
//            } else {
//                T.showShort(getActivity(), R.string.my_order_get_failed);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            T.showShort(getActivity(), R.string.my_order_get_failed);
//        }
//
//        return list;
//    }

    @Override
    public void onSucceed(int i, Response response) {
        try{
            switch (i) {
                case PUBLIC_DATA:
                    //200为接口能调通
                    if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
                        String result = (String) response.get();
//                        publics = analyzePublicListData(result);
//                        if (publics != null) {
////                            setGridviewAdapter(publics);
//                            index = publics.size();
//                        }
                    }
                    break;
                case PUBLIC_DATA_PULLUPREFRESH:
                    //200为接口能调通
                    if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
                        String result = (String) response.get();
//                        ArrayList<Public> list = analyzePublicListData(result);
//                        if (publics != null) {
////                            publics.addAll(list);
//                            index = publics.size();
////                            setGridviewAdapter(publics);
//                        }
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
