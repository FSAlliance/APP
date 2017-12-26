package com.mobile.fsaliance.mine;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.vo.Asset;

import java.util.List;

/**
 * @author tanyadong
 * @Title: PresentRecordListViewAdapter
 * @Description: 提现记录
 * @date 2017/12/25 0025 21:33
 */

public class PresentRecordListViewAdapter extends BaseAdapter implements View.OnClickListener {
	private Context context;
	private List<Asset> assets;
	private LayoutInflater layoutInflater;

	public PresentRecordListViewAdapter(Context context, List<Asset> assetList) {
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
		final Holder holder;
		if (view == null) {
			view = layoutInflater.inflate(
					R.layout.present_record_list, null);
			holder = new Holder();

            view.setTag(holder);
		} else {
			holder = (Holder) view.getTag();
		}

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		}
	}

	private class Holder {
//		TextView assetId;
//		TextView assetName;
//		ImageView assetProcessState;
//
//		TextView assetNumTxt;
//		TextView assetNameTxt;
//		TextView assetStateTxt;
//		TextView assetPlaceTxt;
//		RelativeLayout parentItemRl;
//		LinearLayout childItemLl;
//		ImageView parentItemLineView;
//		View bottomLineView;
//		ImageView qrcodeImg;
//		LinearLayout qrcodeLl;
	}

}
