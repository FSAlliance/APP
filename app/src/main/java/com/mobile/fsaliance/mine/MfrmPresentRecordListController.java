package com.mobile.fsaliance.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.common.AppMacro;
import com.mobile.fsaliance.common.common.CircleProgressBarView;
import com.mobile.fsaliance.common.common.InitApplication;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.StatusBarUtil;
import com.mobile.fsaliance.common.util.T;
import com.mobile.fsaliance.common.vo.IncomeRecord;
import com.mobile.fsaliance.common.vo.PresentRecord;
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

import static android.view.View.INVISIBLE;


public class MfrmPresentRecordListController extends BaseController implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, OnResponseListener<String> {

    private Object cancelObject = new Object();
    private RequestQueue queue;
    private ListView presentReccordListview;
    private ImageView incomeListBackImg;
    private TextView titleTxt;
    private LinearLayout titleLiftLl, titleRightLl;
    private BGARefreshLayout refreshLayout;
    private int index = 0;
    private int pageSize = 20;
    private PresentRecordListViewAdapter presentRecordListViewAdapter;
    public CircleProgressBarView circleProgressBarView;
    List<PresentRecord> list = new ArrayList<>();
    @Override
    protected void getBundleData() {

    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        int result = StatusBarUtil.StatusBarLightMode(this);
        if (result != 0) {
            StatusBarUtil.initWindows(this, getResources().getColor(R.color.white));
        }
        setContentView(R.layout.activity_presentlist_controller);
        queue = NoHttp.newRequestQueue();
        initView();
        addListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        for (int i = 0; i < 20; i ++) {
            PresentRecord presentRecord = new PresentRecord();
            presentRecord.setPresentMoneny("20");
            presentRecord.setPresentTime("2017-10-30");
            if (i < 8) {
                presentRecord.setState(0);
            } else {
                presentRecord.setState(1);
            }
            list.add(presentRecord);
        }
        showPresentRecordList(list);
    }

    private void initView() {
        initRefresh();
        presentReccordListview = (ListView) findViewById(R.id.presentrecord_list_view);
        circleProgressBarView = (CircleProgressBarView) findViewById(R.id.circleProgressBarView);
        titleLiftLl = (LinearLayout) findViewById(R.id.ll_title_left);
        titleRightLl = (LinearLayout) findViewById(R.id.ll_title_right);
        titleRightLl.setVisibility(INVISIBLE);
        incomeListBackImg = (ImageView) findViewById(R.id.img_back);
        incomeListBackImg.setImageResource(R.drawable.goback);
        titleTxt = (TextView) findViewById(R.id.txt_title);
        titleTxt.setText(getResources().getString(R.string.ming_present_record));
    }

    private void initRefresh() {
        refreshLayout = (BGARefreshLayout)findViewById(R.id.presentrecord_freshlayout);
        refreshLayout.setDelegate(this);
        //true代表开启上拉加载更多
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        refreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
    }

    private void addListener() {
        titleLiftLl.setOnClickListener(this);
    }

    /**
     * @author tanyadong
     * @Title: getIncomeListData
     * @Description: 获取提现记录
     * @date 2018/1/14 0014 13:00
     */

    public void getPresentListData() {
        String uri = AppMacro.REQUEST_URL + "/asset/query";
        Request<String> request = NoHttp.createStringRequest(uri);
        request.cancelBySign(cancelObject);
//        request.add("param", param);
//        request.add("page", pageNo);
//        request.add("limit", PAGE_SIZE);
        queue.add(0, request, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelBySign(cancelObject);
    }


    /**
     * @author tanyadong
     * @Title showPresentRecordList
     * @Description 刷新并显示数据
     * @date 2017/9/8 14:44
     */
    public void showPresentRecordList(List<PresentRecord> presentRecordList) {
        if (presentRecordList == null) {
            L.e("presentRecordList == null");
            return;
        }
        if (presentRecordListViewAdapter == null) {
            presentRecordListViewAdapter = new PresentRecordListViewAdapter(this,
                    presentRecordList);
            presentReccordListview.setAdapter(presentRecordListViewAdapter);
//            incomeListViewAdapter.setDelegate(this);
        } else {
            presentRecordListViewAdapter.update(presentRecordList);
            presentRecordListViewAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        index = 0;
        getPresentListData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getPresentListData();
        return true;
    }

    @Override
    public void onStart(int i) {
        circleProgressBarView.showProgressBar();
    }

    @Override
    public void onSucceed(int i, Response<String> response) {
        if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
            String result = (String) response.get();
            list = analyzeListData(result);
            if (list != null) {
                showPresentRecordList(list);
                index = list.size();
            }
        } else {
            T.showShort(this, R.string.get_record_failed);
        }
    }

    /**
     * @author tanyadong
     * @Title: analyzeOrderListData
     * @Description: 解析提现记录
     * @date 2018/1/11 0011 22:33
     */

    private ArrayList<PresentRecord> analyzeListData(String result) {
        if (null == result || "".equals(result)) {
            L.e("result == null");
            T.showShort(this, R.string.get_record_failed);
            return null;
        }
        ArrayList<PresentRecord> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has("ret")) {
                int ret = jsonObject.optInt("ret");
                if (ret == 0) {
                    JSONObject jsonContent = jsonObject.optJSONObject("content");

                    if (jsonContent == null || "".equals(jsonContent)) {
                        T.showShort(this, R.string.get_record_failed);
                        return null;
                    } else {
                        JSONArray jsonPublics = jsonContent.optJSONArray("channels");
                        for (int i = 0; i < jsonPublics.length(); i++) {
                            PresentRecord presentRecord = new PresentRecord();
//                            JSONObject jsPublic = (JSONObject) jsonPublics.get(i);
//                            publics.setHostId(jsPublic.optString("hostId"));
//                            publics.setChannelNum(jsPublic.optInt("channelNum"));
//                            publics.setUserName(jsPublic.optString("username"));
//                            publics.setPassword(AESUtil.decrypt(jsPublic.optString("password")));
//                            publics.setShareName(jsPublic.optString("shareName"));
//                            publics.setImageUrl(jsPublic.optString("imgUrl"));
                            list.add(presentRecord);
                        }
                    }
                } else {
                    T.showShort(this, R.string.get_record_failed);
                }
            } else {
                T.showShort(this, R.string.get_record_failed);
            }
        } catch (Exception e) {
            e.printStackTrace();
            T.showShort(this, R.string.get_record_failed);
        }

        return list;
    }

    @Override
    public void onFailed(int i, Response<String> response) {
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
        T.showShort(this, R.string.get_record_failed);
    }

    @Override
    public void onFinish(int i) {
        refreshLayout.endRefreshing();
        refreshLayout.endLoadingMore();
        circleProgressBarView.showProgressBar();

    }
}
