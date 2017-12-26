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
 * @Title: IncomeListViewAdapter
 * @Description: 收入记录
 * @date 2017/12/25 0025 21:33
 */

public class IncomeListViewAdapter extends BaseAdapter implements View.OnClickListener {
	private Context context;
	private List<Asset> assets;
	private LayoutInflater layoutInflater;





	public IncomeListViewAdapter(Context context, List<Asset> assetList) {
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
					R.layout.income_record_list, null);
			holder = new Holder();
			holder.incomeRecordImg = (ImageView) view.findViewById(R.id.img_income_record);
			holder.incomeRecordMoneyTxt = (TextView) view.findViewById(R.id.txt_income_record_count);
			holder.incomeRecordSymbolTxt = (TextView) view.findViewById(R.id.txt_income_record_symbol);
			holder.incomeRecordTimeTxt = (TextView) view.findViewById(R.id.txt_income_record_time);
			holder.incomeRecordTypeTxt = (TextView) view.findViewById(R.id.txt_income_record_type);
			view.setTag(holder);
		} else {
			holder = (Holder) view.getTag();
		}
//		holder.assetId.setText("资产"+(position + 1) + ":");
//		String codeId = assets.get(position).getCodeId();
//		String coid = codeId.substring(codeId.lastIndexOf(".") + 1, codeId.length());
//		holder.assetName.setText(assets.get(position).getName() + "(" + coid + ")");
//		if (assets.get(position).getState() == 1) {// 选中勾选状态
//			holder.assetProcessState
//					.setImageResource(R.drawable.myasset_checked);
//		} else {// 未选中状态
//			holder.assetProcessState
//					.setImageResource(R.drawable.myasset_unchecked);
//		}
//		String assetName = assets.get(position).getName();
//		holder.assetNameTxt.setText(assetName);
//		holder.assetNumTxt.setText(codeId);
//        holder.assetPlaceTxt.setText(assets.get(position).getRealPlace());
//		if (assets.get(position).getState() == 1) {// 已认证
//			holder.assetStateTxt
//					.setText(R.string.authenticated);
//			holder.assetStateTxt.setTextColor(context.getResources().getColor(R.color.green_light));
//			holder.qrcodeLl.setVisibility(View.GONE);
//		} else {// 未选中状态
//			holder.assetStateTxt
//					.setText(context.getResources().getString(R.string.unauthorized) + context.getResources().getString(R.string.qrcode_authenticate));
//			holder.assetStateTxt.setTextColor(context.getResources().getColor(R.color.read));
//			holder.qrcodeLl.setVisibility(View.VISIBLE);
//		}
//		holder.parentItemRl.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (assets.get(position).isExpand()) {
//					holder.parentItemLineView.setVisibility(View.VISIBLE);
//					holder.childItemLl.setVisibility(View.GONE);
//					assets.get(position).setExpand(false);
//				} else {
//					holder.parentItemLineView.setVisibility(View.GONE);
//					holder.childItemLl.setVisibility(View.VISIBLE);
//					assets.get(position).setExpand(true);
//				}
//			}
//		});
//		holder.qrcodeLl.setOnClickListener(this);
//		holder.qrcodeImg.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		}
	}

	private class Holder {
		ImageView incomeRecordImg; //图片
		TextView incomeRecordTypeTxt;//类型  被动收入  主动收入
		TextView incomeRecordTimeTxt; // 日期
		TextView incomeRecordSymbolTxt; //加减符号
		TextView incomeRecordMoneyTxt; // 钱数
	}

}
