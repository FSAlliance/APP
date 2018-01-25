package com.mobile.fsaliance.goods;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseView;
import com.mobile.fsaliance.common.common.CircleProgressBarView;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.vo.Good;
import com.mobile.fsaliance.home.AssetListViewAdapter;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


/**
 * @author tanyadong
 * @date 创建时间 2017/9/5
 * @Description 搜索
 */
public class MfrmSearchGoodListView extends BaseView implements BGARefreshLayout.BGARefreshLayoutDelegate, AssetListViewAdapter.AssetListViewAdapterDelegate, AbsListView.OnScrollListener {
    private TextView assetListNoDataTxt;
    private AssetListViewAdapter assetListViewAdapter;
    public CircleProgressBarView circleProgressBarView;
    public boolean isLoadMore;

    private TextView titleText;
    private ListView searchGoodList;
    private BGARefreshLayout mRefreshLayout;
    private LinearLayout backLL;



    public MfrmSearchGoodListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void setInflate() {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.activity_search_goods_list_controller, this);
    }

    @Override
    protected void initViews() {
        titleText = (TextView) findViewById(R.id.txt_title_middle);
        backLL = (LinearLayout) findViewById(R.id.ll_title_left);
        searchGoodList = (ListView) findViewById(R.id.search_goods_list_back_list_view);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.search_goods_list_back_refreshLayout);
        //商品列表
		assetListNoDataTxt = (TextView) findViewById(R.id.txt_search_good_list_no_data);
		circleProgressBarView = (CircleProgressBarView) findViewById(R.id.search_good_list_circleProgressBarView);
        initFresh();
    }

    /**
     * @param title 标题
     * @author yuanxueyuan
     * @Title: setTitle
     * @Description: 设置标题
     * @date 2017/12/26 20:58
     */
    public void setTitle(String title) {
        if (title == null || titleText == null) {
            L.e("itle == null");
            return;
        }
        titleText.setText(title);
    }

    /**
     * @author tanyadong
     * @Title endRefreshLayout
     * @Description 停止刷新
     * @date 2017/9/12 17:28
     */
    public void endRefreshLayout() {
        mRefreshLayout.endRefreshing();
        mRefreshLayout.endLoadingMore();
    }

    /**
     * 初始化上下拉刷新控件
     */
    private void initFresh() {
        mRefreshLayout.setDelegate(this);
        //true代表开启上拉加载更多
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        mRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
    }

    @Override
    protected void addListener() {
        searchGoodList.setOnScrollListener(this);
        backLL.setOnClickListener(this);
    }

    /**
     * @author tanyadong
     * @Title
     * @Description 下拉
     * @date 2017/9/16 14:08
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (super.delegate instanceof MfrmSearchGoodListDelegate) {
            ((MfrmSearchGoodListDelegate) super.delegate).onClickPullDown("");
        }
    }

    /**
     * @author tanyadong
     * @Title onBGARefreshLayoutBeginLoadingMore
     * @Description 上拉
     * @date 2017/9/16 14:08
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (super.delegate instanceof MfrmSearchGoodListDelegate) {
            ((MfrmSearchGoodListDelegate) super.delegate).onClickLoadMore("");
        }
        return isLoadMore;
    }


    @Override
    public void onClickItem(Good asset) {
        if (super.delegate instanceof MfrmSearchGoodListDelegate) {
            ((MfrmSearchGoodListDelegate) super.delegate).onClickToDetail(asset);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //有更多
        if (totalItemCount > visibleItemCount) {
            //不满一屏
            isLoadMore = true;
        } else {
            isLoadMore = false;
        }
    }


    @Override
    protected void onClickListener(View v) {
        switch (v.getId()) {
            //返回键
            case R.id.ll_title_left:
                if (super.delegate instanceof MfrmSearchGoodListDelegate) {
                    ((MfrmSearchGoodListDelegate) super.delegate).onClickBack();
                }
                break;
            default:
                break;
        }
    }

    /**
     * @author tanyadong
     * @Title showSearchAssetList
     * @Description 刷新并显示数据
     * @date 2017/9/8 14:44
     */
    public void showSearchAssetList(List<Good> myAssetList) {
        if (myAssetList == null) {
            L.e("myAssetList == null");
            return;
        }
        if (assetListViewAdapter == null) {
            assetListViewAdapter = new AssetListViewAdapter(context,
                    myAssetList);
            searchGoodList.setAdapter(assetListViewAdapter);
            assetListViewAdapter.setDelegate(this);
        } else {
            assetListViewAdapter.update(myAssetList);
            assetListViewAdapter.notifyDataSetChanged();
        }
    }

    /**
     * @author tanyadong
     * @Title: setNoDataView
     * @Description: 设置没有数据界面显示
     * @date 2017/4/1 11:06
     */
    public void setNoDataView(boolean isShow) {
        if (isShow) {
            assetListNoDataTxt.setVisibility(VISIBLE);
        } else {
            assetListNoDataTxt.setVisibility(GONE);
        }
    }

    @Override
    public void initData(Object... data) {

    }



    public interface MfrmSearchGoodListDelegate {

        void onClickPullDown(String searchTxt); //下拉刷新

        void onClickLoadMore(String searchTxt); //上拉加载

        void onClickToDetail(Good asset); //上啦加载

        void onClickBack();//返回键
    }

}
