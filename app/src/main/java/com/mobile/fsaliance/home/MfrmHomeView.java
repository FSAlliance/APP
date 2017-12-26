package com.mobile.fsaliance.home;

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


import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


/**
  * @date 创建时间 2017/9/5
  * @author tanyadong
  * @Description 搜索
*/
public class MfrmHomeView extends BaseView implements BGARefreshLayout.BGARefreshLayoutDelegate,AssetListViewAdapter.AssetListViewAdapterDelegate, AbsListView.OnScrollListener {
	private ListView searchListView;
	private TextView assetListNoDataTxt;
	private AssetListViewAdapter assetListViewAdapter;
	public CircleProgressBarView circleProgressBarView;
	private BGARefreshLayout mRefreshLayout;

	private LinearLayout searchTopLL, oneMiddleLL, twoMiddleLL, threeMiddleLL, fourMiddleLL,
			fiveMiddleLL, sixMiddleLL, sevenMiddleLL, eightMiddleLL;

	public boolean isLoadMore;
	public MfrmHomeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void setInflate() {
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.fragment_home_view, this);
	}

	@Override
	protected void initViews() {
		//搜索部分
		searchTopLL = (LinearLayout) findViewById(R.id.home_top_search);

		//中间部分
		oneMiddleLL = (LinearLayout) findViewById(R.id.home_middle_one);
		twoMiddleLL = (LinearLayout) findViewById(R.id.home_middle_two);
		threeMiddleLL = (LinearLayout) findViewById(R.id.home_middle_three);
		fourMiddleLL = (LinearLayout) findViewById(R.id.home_middle_four);
		fiveMiddleLL = (LinearLayout) findViewById(R.id.home_middle_five);
		sixMiddleLL = (LinearLayout) findViewById(R.id.home_middle_six);
		sevenMiddleLL = (LinearLayout) findViewById(R.id.home_middle_seven);
		eightMiddleLL = (LinearLayout) findViewById(R.id.home_middle_eight);

		//商品列表
		searchListView = (ListView) findViewById(R.id.search_asset_listview);
		assetListNoDataTxt = (TextView) findViewById(R.id.txt_asset_list_no_data);
		circleProgressBarView = (CircleProgressBarView) findViewById(R.id.circleProgressBarView);
		mRefreshLayout = (BGARefreshLayout) findViewById(R.id.mRefreshLayout);
		initFresh();
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
		searchTopLL.setOnClickListener(this);

		oneMiddleLL.setOnClickListener(this);
		twoMiddleLL.setOnClickListener(this);
		threeMiddleLL.setOnClickListener(this);
		fourMiddleLL.setOnClickListener(this);
		fiveMiddleLL.setOnClickListener(this);
		sixMiddleLL.setOnClickListener(this);
		sevenMiddleLL.setOnClickListener(this);
		eightMiddleLL.setOnClickListener(this);

		searchListView.setOnScrollListener(this);
	}
	/**
	  * @author tanyadong
	  * @Title
	  * @Description  下拉
	  * @date 2017/9/16 14:08
	*/
	@Override
	public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
		if (super.delegate instanceof  MfrmSearchDelegate) {
			((MfrmSearchDelegate) super.delegate).onClickPullDown("");
		}
	}
	/**
	 * @author tanyadong
	 * @Title onBGARefreshLayoutBeginLoadingMore
	 * @Description  上拉
	 * @date 2017/9/16 14:08
	 */
	@Override
	public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
		if (super.delegate instanceof  MfrmSearchDelegate) {
			 ((MfrmSearchDelegate) super.delegate).onClickLoadMore("");
		}
		return isLoadMore;
	}



	@Override
	public void onClickItem(Asset asset) {
		if (super.delegate instanceof MfrmSearchDelegate) {
			((MfrmSearchDelegate) super.delegate).onClickToDetail(asset);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		//有更多
		if(totalItemCount > visibleItemCount){
			//不满一屏
			isLoadMore = true;
		}else{
			isLoadMore = false;
		}
	}


	@Override
	protected void onClickListener(View v) {
		switch (v.getId()) {
			//搜索
			case R.id.home_top_search:
				if (super.delegate instanceof MfrmSearchDelegate) {
					((MfrmSearchDelegate) super.delegate).onClickSearch();
				}
				break;
			case R.id.home_middle_one:
				if (super.delegate instanceof MfrmSearchDelegate) {
					((MfrmSearchDelegate) super.delegate).onClickOne();
				}
				break;
			case R.id.home_middle_two:
				if (super.delegate instanceof MfrmSearchDelegate) {
					((MfrmSearchDelegate) super.delegate).onClickTwo();
				}
				break;
			case R.id.home_middle_three:
				if (super.delegate instanceof MfrmSearchDelegate) {
					((MfrmSearchDelegate) super.delegate).onClickThree();
				}
				break;
			case R.id.home_middle_four:
				if (super.delegate instanceof MfrmSearchDelegate) {
					((MfrmSearchDelegate) super.delegate).onClickFour();
				}
				break;
			case R.id.home_middle_five:
				if (super.delegate instanceof MfrmSearchDelegate) {
					((MfrmSearchDelegate) super.delegate).onClickFive();
				}
				break;
			case R.id.home_middle_six:
				if (super.delegate instanceof MfrmSearchDelegate) {
					((MfrmSearchDelegate) super.delegate).onClickSix();
				}
				break;
			case R.id.home_middle_seven:
				if (super.delegate instanceof MfrmSearchDelegate) {
					((MfrmSearchDelegate) super.delegate).onClickSeven();
				}
				break;
			case R.id.home_middle_eight:
				if (super.delegate instanceof MfrmSearchDelegate) {
					((MfrmSearchDelegate) super.delegate).onClickEight();
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
	public void showSearchAssetList(List<Asset> myAssetList) {
		if (myAssetList == null) {
			L.e("myAssetList == null");
			return;
		}
		if (assetListViewAdapter == null) {
			assetListViewAdapter = new AssetListViewAdapter(context,
					myAssetList);
			searchListView.setAdapter(assetListViewAdapter);
			assetListViewAdapter.setDelegate(this);
		} else {
			assetListViewAdapter.update(myAssetList);
			assetListViewAdapter.notifyDataSetChanged();
		}
	}
	/**
	 * @author  tanyadong
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
	/**
	 * @author liuchenghe
	 * @Title: initData
	 * @Description: 初始化数据（并没有使用该方法，使用update方法）
	 * @date 2016-9-19 下午7:57:20
	 */
	@Override
	public void initData(Object... data) {

	}


	/**
	  * @date 创建时间 2017/9/5
	  * @author tanyadong
	  * @Description 查找
	*/
	public interface MfrmSearchDelegate {

		void  onClickSearch();//搜索

		void  onClickOne();//第1个
		void  onClickTwo();//第2个
		void  onClickThree();//第3个
		void  onClickFour();//第4个
		void  onClickFive();//第5个
		void  onClickSix();//第6个
		void  onClickSeven();//第7个
		void  onClickEight();//第8个

		void onClickPullDown(String searchTxt); //下拉刷新

		void onClickLoadMore(String searchTxt); //上拉加载

		void onClickToDetail(Asset asset); //上啦加载
 	}

}
