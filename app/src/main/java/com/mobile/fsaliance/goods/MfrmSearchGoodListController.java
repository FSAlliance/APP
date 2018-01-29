package com.mobile.fsaliance.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.common.AppMacro;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.T;
import com.mobile.fsaliance.common.vo.Favorite;
import com.mobile.fsaliance.common.vo.Good;
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
    private static final int FAVORITE_GOODS = 0;//选品库中的数据
    private static final int SEARCH_ASSET_LIST = 1;//搜索设备

    private int pageNo = 0;
    private boolean refreshList = false;
    private boolean loadMoreList = false;
    private Object cancelObject = new Object();
    private int lastCount = 0;//上次请求数据个数

    private String searchGoods;//搜索的商品
    private int fromWhichView;//从那个界面跳转过来的
    private Favorite favorite;//选品库

    @Override
    protected void getBundleData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        fromWhichView = bundle.getInt("from");
        if (fromWhichView == AppMacro.FROM_HOME) {
            favorite = (Favorite) bundle.getSerializable("favorite");
        } else if (fromWhichView == AppMacro.FROM_SEARCH) {
            searchGoods = bundle.getString("search_goods");
        }
    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_good_list_controller);
        mfrmSearchGoodListView = (MfrmSearchGoodListView)findViewById(R.id.mfrm_search_good_list_view);
        mfrmSearchGoodListView.setDelegate(this);
        queue = NoHttp.newRequestQueue();
        refreshList = false;
        loadMoreList = false;

        String title = "";
        if (fromWhichView == AppMacro.FROM_HOME) {
            if (favorite != null) {
                title = favorite.getFavoriteName();
            }
            //设置标题
            mfrmSearchGoodListView.setTitle(title);
            //查询数据
            getFavoriteGoods(favorite, AppMacro.FIRST_PAGE);
        } else if (fromWhichView == AppMacro.FROM_SEARCH) {
            //设置标题
            mfrmSearchGoodListView.setTitle(searchGoods);
            //查询数据
            getSearchAssetData(searchGoods, AppMacro.FIRST_PAGE);
        }
    }

    /**
     * @param favorite 选品库
     * @author yuanxueyuan
     * @Title: getFavoriteGoods
     * @Description: 获取选品库中的商品信息
     * @date 2018/1/25 20:56
     */
    private void getFavoriteGoods(Favorite favorite, int pageNo) {
        if (favorite == null) {
            //TODO 没有设备
            return;
        }
        String uri = AppMacro.REQUEST_IP_PORT + AppMacro.REQUEST_GOODS_PATH + AppMacro.REQUEST_FAVORITE_ITEMS;
        Request<String> request = NoHttp.createStringRequest(uri);
        request.cancelBySign(cancelObject);
        request.add("favoritesId", favorite.getFavoriteID());
        request.add("platForm", AppMacro.PLATFORM);
        request.add("adzoneId", AppMacro.ADZONEID);
        request.add("uuid", "1231231231");
        request.add("pageSize", AppMacro.PAGE_SIZE);
        request.add("pageNo", pageNo);
        L.i("QQQQQQQQQQ","url: "+request.url());
        queue.add(FAVORITE_GOODS, request, this);
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
        request.add("limit", AppMacro.PAGE_SIZE);
        queue.add(SEARCH_ASSET_LIST, request, this);
    }


    @Override
    public void onClickPullDown(String searchTxt) {
        refreshList = true;
        getSearchAssetData(searchTxt, AppMacro.FIRST_PAGE);
    }

    @Override
    public void onClickLoadMore(String searchTxt) {
        loadMoreList = true;
        getSearchAssetData(searchTxt, pageNo);
    }

    @Override
    public void onClickToGoodDetail(Good good) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.setClass(this, MfrmGoodsInfoController.class);
        bundle.putSerializable("good", good);
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
            L.i("QQQQQQQ","result: "+result);
            switch (i) {
                //获取选品库中的信息
                case FAVORITE_GOODS:
                    List<Good> goods = analyzeFavoriteData(result);
                    if (goods == null || goods.size() <= 0) {
                        //TODO 展示没有数据
                        return;
                    }
                    mfrmSearchGoodListView.showGoodList(goods);


                    break;
                //搜索商品信息
                case SEARCH_ASSET_LIST:
//                    assetList = analyzeAssetsData(result);
//                    mfrmSearchGoodListView.showSearchAssetList(assetList);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * @param result 返回的数据
     * @author yuanxueyuan
     * @Title: analyzeFavoriteData
     * @Description: 解析喜爱组的数据
     * @date 2018/1/28 21:29
     */
    private List<Good> analyzeFavoriteData(String result) {
        if (null == result || "".equals(result)) {
//            reloadNoDataList();
            return null;
        }
        List<Good> goods = new ArrayList<>();
        try {
            JSONObject resultJson = new JSONObject(result);
            if (resultJson.has("error_response")){
                //错误码
                JSONObject error = resultJson.getJSONObject("error_response");
                int errorCode = error.getInt("code");
                if (errorCode == 15) {
                    //TODO 没有数据
                    return null;
                }
            } else if (resultJson.has("tbk_uatm_favorites_item_get_response")) {
                //有数据
                JSONObject responseJson = resultJson.getJSONObject("tbk_uatm_favorites_item_get_response");
                if (responseJson == null) {
                    L.e("responseJson == null");
                    return null;
                }
                JSONObject results = responseJson.optJSONObject("results");
                if (results == null) {
                    L.e("results == null");
                    return null;
                }
                JSONArray favoriteItemList = results.optJSONArray("uatm_tbk_item");
                if (favoriteItemList == null || favoriteItemList.length() <= 0) {
                    //TODO 没有数据
                    return null;
                }
                for (int i = 0; i < favoriteItemList.length(); i++) {
                    JSONObject goodJson = (JSONObject) favoriteItemList.get(i);
                    if (goodJson == null) {
                        continue;
                    }
                    Good good = new Good();
                    String eventEndTime = goodJson.optString("event_end_time");//招行活动的结束时间
                    String evnetStartTime = goodJson.optString("event_start_time");//招商活动开始时间
                    String goodUrl = goodJson.optString("item_url");//商品地址
                    String nick = goodJson.optString("nick");//卖家昵称
                    String goodId = goodJson.optString("num_iid");//商品ID
                    String goodPicUrl = goodJson.optString("pict_url");//商品主图
                    String goodProvcity = goodJson.optString("provcity");//宝贝所在地
                    String reservePrice = goodJson.optString("reserve_price");//商品一口价格
                    String sellerId = goodJson.optString("seller_id");//卖家id
                    String shopTitle = goodJson.optString("shop_title");//商店名称
                    //组合
                    String goodSmallImages = goodJson.optString("small_images");

                    String goodStatus = goodJson.optString("status");//宝贝状态，0失效，1有效；
                    String goodTitle = goodJson.optString("title");//商品标题
                    String goodTkRate = goodJson.optString("tk_rate");//收入比例
                    int goodType = goodJson.optInt("type");//宝贝类型：1 普通商品； 2 鹊桥高佣金商品；3 定向招商商品；4 营销计划商品;
                    int userType = goodJson.optInt("user_type");//卖家类型
                    int goodVolume = goodJson.optInt("volume");//30天销量
                    String goodFinalPrice = goodJson.optString("zk_final_price");//商品折扣价格
                    String goodFinalPriceWap = goodJson.optString("zk_final_price_wap");//无线折扣价
                    String goodCouponInfo = goodJson.optString("coupon_info");//优惠券面额
                    String goodCouponClickUrl= goodJson.optString("coupon_click_url");//商品优惠券推广链接
                    int goodCouponTotalCount= goodJson.optInt("coupon_total_count");//优惠券剩余量
                    int goodCouponRemainCount= goodJson.optInt("coupon_remain_count");//优惠券剩余量

                    good.setItemUrl(goodUrl);
                    good.setNick(nick);
                    good.setGoodsId(goodId);
                    good.setGoodsImg(goodPicUrl);
                    good.setGoodProvcity(goodProvcity);
                    good.setReservePrice(reservePrice);
                    good.setSellerId(sellerId);
                    good.setShopTitle(shopTitle);

                    good.setGoodStatus(goodStatus);
                    good.setGoodsTitle(goodTitle);
                    good.setCommissionRate(goodTkRate);
                    good.setGoodType(goodType);
                    good.setUserType(userType);
                    good.setVolume(goodVolume);
                    good.setGoodsFinalPrice(goodFinalPrice);
                    good.setGoodsFinalPriceWap(goodFinalPriceWap);
                    good.setGoodsCode(goodCouponInfo);
                    good.setCouponClickUrl(goodCouponClickUrl);
                    good.setCouponRemainCount(goodCouponRemainCount);
                    good.setCouponTotalCount(goodCouponTotalCount);
                    goods.add(good);
                }
            }
            return goods;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return goods;
    }

    /**
     * @author tanyadong
     * @Title reloadNoDataList
     * @Description 无数据刷新列表
     * @date 2017/9/9 20:59
     */
    /*private void reloadNoDataList() {
        if (assetList == null || assetList.size() <= 0) {
            mfrmSearchGoodListView.setNoDataView(true);
            mfrmSearchGoodListView.showSearchAssetList(assetList);
        }
    }*/

    @Override
    public void onFailed(int i, Response response) {
//        if (refreshList == true) {
//            if (assetList != null) {
//                assetList.clear();
//            }
//        }
        Exception exception = response.getException();
//        reloadNoDataList();
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
