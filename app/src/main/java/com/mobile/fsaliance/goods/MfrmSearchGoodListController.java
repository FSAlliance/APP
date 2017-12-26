package com.mobile.fsaliance.goods;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.util.L;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class MfrmSearchGoodListController extends BaseController implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate{

    private final String TAG = "MfrmSearchGoodListController";
    private TextView titleText;
    private ImageView backImg;
    private ListView searchGoodList;
    private BGARefreshLayout mRefreshLayout;

    @Override
    protected void getBundleData() {
    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_goods_list_controller);

        initView();

        initFresh();

        addListener();

        //初始化数据
        initValues();
    }


    /**
     * @author yuanxueyuan
     * @Title: initView
     * @Description: 初始化界面
     * @date 2017/12/24 22:41
     */
    private void initView() {
        titleText = (TextView) findViewById(R.id.search_goods_list_title);
        backImg = (ImageView) findViewById(R.id.search_goods_list_back_img);
        searchGoodList = (ListView) findViewById(R.id.search_goods_list_back_list_view);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.search_goods_list_back_refreshLayout);
    }


    /**
     * 初始化上下拉刷新控件
     */
    private void initFresh() {
        //true代表开启上拉加载更多
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        mRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
    }

     /**
     * @author  yuanxueyuan
     * @Title:  endRefreshLayout
     * @Description: 停止刷新
     * @date 2017/12/25 22:56
     */
    private void endRefreshLayout() {
        mRefreshLayout.endRefreshing();
        mRefreshLayout.endLoadingMore();

    }


    /**
     * @author yuanxueyuan
     * @Title: initValues
     * @Description: 初始化数据
     * @date 2017/12/25 21:12
     */
    private void initValues() {
        //TODO 本地获取搜索记录
        titleText.setText("12312321");
    }

    /**
     * @author yuanxueyuan
     * @Title: addListener
     * @Description: 添加监听方法
     * @date 2017/12/24 22:41
     */
    private void addListener() {
        backImg.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //返回键
            case R.id.search_goods_list_back_img:
                finish();
                break;
            default:
                break;
        }
    }

     /**
     * @author  yuanxueyuan
     * @Title:  onBGARefreshLayoutBeginRefreshing
     * @Description: 下拉刷新
     * @date 2017/12/25 22:57
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

     /**
     * @author  yuanxueyuan
     * @Title:  onBGARefreshLayoutBeginLoadingMore
     * @Description: 上拉加载更多
     * @date 2017/12/25 22:57
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
