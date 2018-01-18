package com.mobile.fsaliance.home;

import android.support.v7.widget.RecyclerView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.vo.Asset;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:
 */
public class NormalRecyclerViewAdapter extends BGARecyclerViewAdapter<Asset> {
    public NormalRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_search_listview_adapter);
    }

    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper, int viewType) {
        viewHolderHelper.setItemChildClickListener(R.id.home_goods);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, Asset model) {
        helper.setText(R.id.home_goods_describe, position+"");
    }

}