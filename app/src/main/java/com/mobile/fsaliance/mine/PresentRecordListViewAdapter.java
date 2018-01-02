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
import com.mobile.fsaliance.common.vo.PresentRecord;

import java.util.List;

/**
 * @author tanyadong
 * @Title: PresentRecordListViewAdapter
 * @Description: 提现记录
 * @date 2017/12/25 0025 21:33
 */

public class PresentRecordListViewAdapter extends BaseAdapter implements View.OnClickListener {
	private Context context;
	private List<PresentRecord> presentRecords;
	private LayoutInflater layoutInflater;

	public PresentRecordListViewAdapter(Context context, List<PresentRecord> presentRecordList) {
		super();
		this.context = context;
		this.layoutInflater = LayoutInflater.from(context);
		this.presentRecords = presentRecordList;
	}

	public void update(List<PresentRecord> data) {
		if (data == null) {
			L.e("data = null!");
			return;
		}
		this.presentRecords = data;
	}


	@Override
	public int getCount() {
		if (presentRecords == null) {
			L.e("data = null!");
			return 0;
		}
		return presentRecords.size();
	}

	@Override
	public Object getItem(int position) {
		return presentRecords.get(position);
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
			holder.presentRecordImg = (ImageView) view.findViewById(R.id.img_present_record);
			holder.presentRecordMoneyTxt = (TextView) view.findViewById(R.id.txt_present_record_count);
			holder.presentRecordSymbolTxt = (TextView) view.findViewById(R.id.txt_present_record_symbol);
			holder.presentRecordTimeTxt = (TextView) view.findViewById(R.id.txt_present_record_time);
			holder.presentRecordTypeTxt = (TextView) view.findViewById(R.id.txt_present_record_type);
            view.setTag(holder);
		} else {
			holder = (Holder) view.getTag();
		}
		PresentRecord presentRecord = presentRecords.get(position);
		if (presentRecord.getState() == 0) { //提现中
			holder.presentRecordTypeTxt.setText(R.string.mine_money_in_the_present);
			holder.presentRecordImg.setImageResource(R.drawable.register_job_id);
		} else {
			holder.presentRecordTypeTxt.setText(R.string.mine_money_has_present);
			holder.presentRecordImg.setImageResource(R.drawable.register_at_once);
		}
		holder.presentRecordMoneyTxt.setText(presentRecord.getPresentMoneny());
		holder.presentRecordTimeTxt.setText(presentRecord.getPresentTime());
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		}
	}

	private class Holder {
		ImageView presentRecordImg; //图片
		TextView presentRecordTypeTxt;//类型  被动收入  主动收入
		TextView presentRecordTimeTxt; // 日期
		TextView presentRecordSymbolTxt; //加减符号
		TextView presentRecordMoneyTxt; // 钱数
	}

}
