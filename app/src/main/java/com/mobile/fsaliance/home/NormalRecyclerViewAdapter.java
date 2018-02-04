package com.mobile.fsaliance.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.vo.Good;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;


/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:
 */
public class NormalRecyclerViewAdapter extends BGARecyclerViewAdapter<Good> {
    public NormalRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_search_listview_adapter);
    }


    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper, int viewType) {
        viewHolderHelper.setItemChildClickListener(R.id.home_goods);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, Good good) {
        helper.getView(R.id.view_item_line).setVisibility(View.GONE);
        helper.setText(R.id.home_goods_describe, good.getGoodsTitle());
        ImageView imageView = helper.getImageView(R.id.home_goods_img);
        Glide.with(mContext).load(good.getGoodsImg()).into(imageView);
        helper.setText(R.id.home_goods_price_discount, good.getCouponInfo()); //优惠券价值
        helper.setText(R.id.home_goods_price, good.getGoodsFinalPrice()); //折扣价
        helper.setText(R.id.home_goods_sale_num, String.valueOf(good.getVolume())); //月销量
    }
}