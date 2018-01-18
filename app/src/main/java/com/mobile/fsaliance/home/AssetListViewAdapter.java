package com.mobile.fsaliance.home;

import com.bumptech.glide.Glide;
import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.vo.Asset;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class AssetListViewAdapter extends BaseAdapter {
	private Context context;
	private AssetListViewAdapterDelegate delegate;
	private List<Asset> assets;
	private LayoutInflater layoutInflater;

	public AssetListViewAdapter(Context context, List<Asset> assetList) {
		super();
		this.context = context;
		this.layoutInflater = LayoutInflater.from(context);
		this.assets = assetList;
	}

	public void update(List<Asset> data) {
		if (data == null) {
			L.e("data = null!");
			return;
		}
		this.assets = data;
	}

	public void setDelegate(AssetListViewAdapterDelegate delegate) {
		this.delegate = delegate;
	}

	@Override
	public int getCount() {
		if (assets == null) {
			L.e("data = null!");
			return 0;
		}
		return assets.size();
	}

	@Override
	public Object getItem(int position) {
		return assets.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		Holder holder;
		if (view == null) {
			view = layoutInflater.inflate(
					R.layout.item_search_listview_adapter, null);
			holder = new Holder();
			holder.goodsDescribeText = (TextView) view.findViewById(R.id.home_goods_describe);
			holder.goodsPriceDiscountText = (TextView) view.findViewById(R.id.home_goods_price_discount);
			holder.goodsPriceText = (TextView) view.findViewById(R.id.home_goods_price);
			holder.goodsSaleNumText = (TextView) view.findViewById(R.id.home_goods_sale_num);
			holder.goodsImg = (ImageView) view.findViewById(R.id.home_goods_img);
			holder.goodsLL = (LinearLayout) view.findViewById(R.id.home_goods);
			view.setTag(holder);
		} else {
			holder = (Holder) view.getTag();
		}
		if (assets != null) {
			holder.goodsDescribeText.setText(assets.get(position).getModel()); //描述
			holder.goodsPriceDiscountText.setText("10"); //商品优惠卷价格
			holder.goodsPriceText.setText("100"); //商品价格
			holder.goodsSaleNumText.setText("12");//商品销量
			Glide.with(context).load(R.drawable.register_username).into(holder.goodsImg);
			holder.goodsLL.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (delegate != null) {
						delegate.onClickItem(assets.get(position));
					}
				}
			});
		}
		return view;
	}

	private class Holder {
		private LinearLayout goodsLL;
		private ImageView goodsImg;
		private TextView goodsDescribeText;
		private TextView goodsPriceDiscountText;
		private TextView goodsPriceText;
		private TextView goodsSaleNumText;
	}



	public interface AssetListViewAdapterDelegate {

		void onClickItem(Asset asset);// 点击每一个item
	}
}
