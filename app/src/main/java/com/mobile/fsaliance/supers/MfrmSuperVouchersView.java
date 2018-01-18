package com.mobile.fsaliance.supers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseView;
import com.mobile.fsaliance.common.common.CircleProgressBarView;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.vo.Asset;
import com.mobile.fsaliance.home.AssetListViewAdapter;


import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class MfrmSuperVouchersView extends BaseView implements BGARefreshLayout.BGARefreshLayoutDelegate, AssetListViewAdapter.AssetListViewAdapterDelegate,AbsListView.OnScrollListener {
	private ListView superGoodListView;
	private TextView superListNoDataTxt;
	public CircleProgressBarView circleProgressBarView;
	private BGARefreshLayout bgaRefreshLayout;
	private TextView titleTxt;
	private LinearLayout backLL;

	private AssetListViewAdapter superListViewAdapter;

	public boolean isLoadMore;


	public MfrmSuperVouchersView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void setInflate() {
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.fragment_super_vouchers_view, this);
	}

	@Override
	protected void initViews() {
		superListNoDataTxt = (TextView) findViewById(R.id.txt_super_good_list_no_data);
		circleProgressBarView = (CircleProgressBarView) findViewById(R.id.super_good_list_circleProgressBarView);
		bgaRefreshLayout = (BGARefreshLayout) findViewById(R.id.super_goods_list_back_refreshLayout);
		superGoodListView = (ListView) findViewById(R.id.super_goods_list_back_list_view);
		titleTxt = (TextView) findViewById(R.id.txt_title_middle);
		backLL = (LinearLayout) findViewById(R.id.ll_title_left);
		backLL.setVisibility(GONE);
		titleTxt.setText(R.string.tabbar_bottom_super_voucher);
		initFresh();
	}



	@Override
	protected void addListener() {
		superGoodListView.setOnScrollListener(this);
	}

	@Override
	protected void onClickListener(View v) {
	}


	/**
	 * @author  tanyadong
	 * @Title: setNoDataView
	 * @Description: 设置没有数据界面显示
	 * @date 2017/4/1 11:06
	 */
	public void setNoDataView(boolean isShow) {
		if (isShow) {
			superListNoDataTxt.setVisibility(VISIBLE);
		} else {
			superListNoDataTxt.setVisibility(GONE);
		}
	}

	/**
	 * 初始化上下拉刷新控件
	 */
	private void initFresh() {
		bgaRefreshLayout.setDelegate(this);
		//true代表开启上拉加载更多
		BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(getContext(), false);
		bgaRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
	}

	/**
	 * @param myAssetList 数据
	 * @author yuanxueyuan
	 * @Title: showMyAssetList
	 * @Description: 刷新并显示数据
	 * @date 2017/12/26 21:59
	 */
	public void showMyAssetList(List<Asset> myAssetList) {
		if (myAssetList == null) {
			L.e("myAssetList == null");
			return;
		}
		if (superListViewAdapter == null) {
			superListViewAdapter = new AssetListViewAdapter(context, myAssetList);
			superGoodListView.setAdapter(superListViewAdapter);
			superListViewAdapter.setDelegate(this);
		} else {
			superListViewAdapter.update(myAssetList);
			superListViewAdapter.notifyDataSetChanged();
		}
	}


	@Override
	public void initData(Object... data) {

	}

	@Override
	public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
		if (super.delegate instanceof MfrmMineAssetDelegate) {
			((MfrmMineAssetDelegate) super.delegate).pullDownRefresh();
		}
	}


	/**
	 * @author yuanxueyuan
	 * @Title: endRefreshLayout
	 * @Description: 停止刷新
	 * @date 2017/12/26 22:00
	 */
	public void endRefreshLayout() {
		bgaRefreshLayout.endRefreshing();
		bgaRefreshLayout.endLoadingMore();
	}


	@Override
	public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
		if (super.delegate instanceof MfrmMineAssetDelegate) {
			((MfrmMineAssetDelegate) super.delegate).onClickLoadMore();
		}
		return isLoadMore;
	}

	@Override
	public void onScrollStateChanged(AbsListView absListView, int i) {

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
	public void onClickItem(Asset asset) {
		if (super.delegate instanceof MfrmMineAssetDelegate) {
			((MfrmMineAssetDelegate) super.delegate).onClickToDetail(asset);
		}
	}


	public interface MfrmMineAssetDelegate {

		void onClickToDetail(Asset asset);//点击详情

		void pullDownRefresh(); //下拉刷新

		void onClickLoadMore();//上拉加载更多
	}

}
