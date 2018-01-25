package com.mobile.fsaliance.mine;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.vo.IncomeRecord;

import java.util.List;

/**
 * @author tanyadong
 * @Title: IncomeListViewAdapter
 * @Description: 收入记录
 * @date 2017/12/25 0025 21:33
 */

public class IncomeListViewAdapter extends BaseAdapter implements View.OnClickListener {
	private Context context;
	private List<IncomeRecord> incomeRecords;
	private LayoutInflater layoutInflater;





	public IncomeListViewAdapter(Context context, List<IncomeRecord> incomeRecordList) {
		super();
		this.context = context;
		this.layoutInflater = LayoutInflater.from(context);
		this.incomeRecords = incomeRecordList;
	}

	public void update(List<IncomeRecord> data) {
		if (data == null) {
			L.e("data = null!");
			return;
		}
		this.incomeRecords = data;
	}


	@Override
	public int getCount() {
		if (incomeRecords == null) {
			L.e("data = null!");
			return 0;
		}
		return incomeRecords.size();
	}

	@Override
	public Object getItem(int position) {
		return incomeRecords.get(position);
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

		IncomeRecord incomeRecord = incomeRecords.get(position);
		if (incomeRecord.getType() == 0) { //主动收入
			holder.incomeRecordTypeTxt.setText(R.string.active_income);
			holder.incomeRecordImg.setImageResource(R.drawable.img_active_income);
		} else {  // 被动收入
			holder.incomeRecordTypeTxt.setText(R.string.passive_income);
			holder.incomeRecordImg.setImageResource(R.drawable.img_passive_income);
		}
		holder.incomeRecordMoneyTxt.setText(incomeRecord.getIncomeMoneny());
		holder.incomeRecordTimeTxt.setText(incomeRecord.getIncomeTime());
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
