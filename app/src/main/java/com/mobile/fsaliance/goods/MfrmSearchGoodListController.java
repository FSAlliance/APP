package com.mobile.fsaliance.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.common.AppMacro;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.T;
import com.mobile.fsaliance.common.vo.Asset;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class MfrmSearchGoodListController extends BaseController
        implements MfrmSearchGoodListView.MfrmSearchGoodListDelegate, OnResponseListener {

    private final String TAG = "MfrmSearchGoodListController";
    private MfrmSearchGoodListView mfrmSearchGoodListView;

    private RequestQueue queue;
    private static final int SEARCH_ASSET_LIST = 1;
    private List<Asset> assetList;
    private int pageNo = 0;
    private static final int PAGE_SIZE = 10;//每页数据条数
    private static final int FIRST_PAGE = 0;//第几页
    private boolean refreshList = false;
    private boolean loadMoreList = false;
    private Object cancelObject = new Object();
    private int lastCount = 0;//上次请求数据个数

    private String searchGoods;//搜索的商品

    @Override
    protected void getBundleData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        searchGoods = bundle.getString("search_goods");
    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_good_list_controller);
        mfrmSearchGoodListView = (MfrmSearchGoodListView)findViewById(R.id.mfrm_search_good_list_view);
        mfrmSearchGoodListView.setDelegate(this);
        queue = NoHttp.newRequestQueue();
        assetList = new ArrayList<>();
        refreshList = false;
        loadMoreList = false;
//        getSearchAssetData(searchGoods, FIRST_PAGE);
        //设置标题
        mfrmSearchGoodListView.setTitle(searchGoods);
        //查询数据
        getSearchAssetData("", FIRST_PAGE);
    }

    /**
     * @author tanyadong
     * @Title getSearchAssetData
     * @Description 获取搜索数据
     * @date 2017/9/9 10:42
     */
    private void getSearchAssetData(String param, int pageNo) {
        String uri = AppMacro.REQUEST_URL + "/asset/query";
        Request<String> request = NoHttp.createStringRequest(uri);
        request.cancelBySign(cancelObject);
        request.add("param", param);
        request.add("page", pageNo);
        request.add("limit", PAGE_SIZE);
        queue.add(SEARCH_ASSET_LIST, request, this);
    }



    @Override
    public void onClickPullDown(String searchTxt) {
        refreshList = true;
        getSearchAssetData(searchTxt, FIRST_PAGE);
    }

    @Override
    public void onClickLoadMore(String searchTxt) {
        loadMoreList = true;
        getSearchAssetData(searchTxt, pageNo);
    }

    @Override
    public void onClickToDetail(Asset asset) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.setClass(this, MfrmGoodsInfoController.class);
        //TODO 填写具体的参数
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClickBack() {
        finish();
    }



    @Override
    public void onStart(int i) {
        if (refreshList == true || loadMoreList == true) {
            return;
        }
        mfrmSearchGoodListView.circleProgressBarView.showProgressBar();
    }

    @Override
    public void onSucceed(int i, Response response) {
        if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
            String result = (String) response.get();
            assetList = analyzeAssetsData(result);
            mfrmSearchGoodListView.showSearchAssetList(assetList);
        }
    }


    /**
     * @author tanyadong
     * @Title analyzeAssetsData
     * @Description 解析查询到的资产
     * @date 2017/9/9 20:57
     */
    private List<Asset> analyzeAssetsData(String result) {
        if (!loadMoreList) {
            if (assetList != null) {
                assetList.clear();
            }
        }
        if (null == result || "".equals(result)) {
            T.showShort(this, R.string.get_myasset_failed);
            reloadNoDataList();
            L.e("result == null");
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has("code") && jsonObject.optInt("code") == 0) {
                JSONArray jsonArray = jsonObject.optJSONArray("content");
                mfrmSearchGoodListView.isLoadMore = true;
                if (jsonArray.length() <= 0) {
                    if (loadMoreList) {
                        mfrmSearchGoodListView.isLoadMore = false;
                        T.showShort(this, R.string.check_asset_no_more);
                    } else {
                        reloadNoDataList();
                    }
                    return null;
                } else {
                    mfrmSearchGoodListView.setNoDataView(false);
                }
                if (assetList == null){
                    assetList = new ArrayList<>();
                }
                int arrCount = 0;
                if (assetList != null) {
                    arrCount = assetList.size();
                }
                if (jsonArray.length() >= PAGE_SIZE) {
                    pageNo++;
                } else {
                    if (lastCount < PAGE_SIZE && arrCount > 0) {
                        int index = (pageNo - 1) * PAGE_SIZE;//开始从某一位移除
                        for (int i = index; i < arrCount; i++) {
                            if (i >= index && i < index + lastCount) {
                                if (index < assetList.size()){
                                    assetList.remove(index);
                                }
                            }
                        }
                    }
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    Asset asset = new Asset();
                    JSONObject jsonObjectContent = jsonArray.getJSONObject(i);
                    asset.setState(jsonObjectContent.getInt("state"));
                    asset.setType(jsonObjectContent.getString("type"));
                    asset.setCodeId(jsonObjectContent.getString("codeId"));
                    asset.setJobId(jsonObjectContent.getString("jobId"));
                    asset.setUserName(jsonObjectContent.optString("user"));
                    asset.setName(jsonObjectContent.getString("name"));
                    asset.setBoard(jsonObjectContent.getString("board"));
                    asset.setBox(jsonObjectContent.getString("box"));
                    asset.setBuild(jsonObjectContent.getString("build"));
                    asset.setCenter(jsonObjectContent.getString("center"));
                    asset.setCost(jsonObjectContent.getString("cost"));
                    asset.setCostIt(jsonObjectContent.getString("costIt"));
                    asset.setCount(jsonObjectContent.getString("count"));
                    asset.setCpu(jsonObjectContent.getString("cpu"));
                    asset.setDisk(jsonObjectContent.getString("disk"));
                    asset.setFloor(jsonObjectContent.getString("floor"));
                    asset.setHardDriver(jsonObjectContent.getString("hardDriver"));
                    asset.setLeavePlace(jsonObjectContent.getString("leavePlace"));
                    asset.setMemory(jsonObjectContent.getString("memory"));
                    asset.setModel(jsonObjectContent.getString("model"));
                    asset.setMoney(jsonObjectContent.getString("money"));
                    asset.setOther(jsonObjectContent.getString("other"));
                    asset.setPart(jsonObjectContent.getString("part"));
                    asset.setPlace(jsonObjectContent.getString("place"));
                    asset.setRealPlace(jsonObjectContent.getString("realPlace"));
                    asset.setSaver(jsonObjectContent.getString("saver"));
                    asset.setRealSaver(jsonObjectContent.getString("realSaver"));
                    asset.setPrice(jsonObjectContent.getString("price"));
                    asset.setSoftDriver(jsonObjectContent.getString("softDriver"));
                    asset.setTime(jsonObjectContent.getString("time"));
                    asset.setVideoCard(jsonObjectContent.getString("videoCard"));
                    assetList.add(asset);
                }
                lastCount = jsonArray.length();
            } else {
                T.showShort(this, R.string.get_myasset_failed);
                reloadNoDataList();
                return null;
            }
        } catch (JSONException e) {
            T.showShort(this, R.string.get_myasset_failed);
            reloadNoDataList();
            e.printStackTrace();
        }
        return  assetList;
    }


    /**
     * @author tanyadong
     * @Title reloadNoDataList
     * @Description 无数据刷新列表
     * @date 2017/9/9 20:59
     */
    private void reloadNoDataList() {
        if (assetList == null || assetList.size() <= 0) {
            mfrmSearchGoodListView.setNoDataView(true);
            mfrmSearchGoodListView.showSearchAssetList(assetList);
        }
    }

    @Override
    public void onFailed(int i, Response response) {
        if (refreshList == true) {
            if (assetList != null) {
                assetList.clear();
            }
        }
        Exception exception = response.getException();
        reloadNoDataList();
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
        if (exception instanceof ConnectException) {
            T.showShort(this, R.string.network_error);
            return;
        }
        T.showShort(this, R.string.login_failed);
    }

    @Override
    public void onFinish(int i) {
        mfrmSearchGoodListView.circleProgressBarView.hideProgressBar();
        mfrmSearchGoodListView.endRefreshLayout();
        refreshList = false;
        loadMoreList = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
