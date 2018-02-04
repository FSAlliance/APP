package com.mobile.fsaliance.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.vo.Good;

import java.util.List;

/**
 * @author yuanxueyuan
 * @Description: 商品适配器
 * @date 2018/1/28  20:24
 */
public class GoodListViewAdapter extends BaseAdapter {
    private Context context;
    private GoodListViewAdapterDelegate delegate;
    private List<Good> goods;
    private LayoutInflater layoutInflater;

    public GoodListViewAdapter(Context context, List<Good> goodList) {
        super();
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.goods = goodList;
    }

    public void update(List<Good> data) {
        if (data == null) {
            L.e("goods = null!");
            return;
        }
        this.goods = data;
    }

    public void setDelegate(GoodListViewAdapterDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public int getCount() {
        if (goods == null) {
            L.e("goods = null!");
            return 0;
        }
        return goods.size();
    }

    @Override
    public Object getItem(int position) {
        return goods.get(position);
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
            holder.goodsPriceDiscountRl = (RelativeLayout) view.findViewById(R.id.rl_goods_price_discount);
            holder.goodsPriceText = (TextView) view.findViewById(R.id.home_goods_price);
            holder.goodsSaleNumText = (TextView) view.findViewById(R.id.home_goods_sale_num);
            holder.goodsImg = (ImageView) view.findViewById(R.id.home_goods_img);
            holder.goodsLL = (LinearLayout) view.findViewById(R.id.home_goods);
            holder.lineView = (ImageView) view.findViewById(R.id.view_item_line);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        //第一个不显示
        if (position == 0) {
            holder.lineView.setVisibility(View.GONE);
        }

        if (goods != null && goods.get(position) != null) {
            final Good good = goods.get(position);
            if (good != null) {
                holder.goodsDescribeText.setText(good.getGoodsTitle()); //描述
                if (good.getCouponInfo() == null || "".equals(good.getCouponInfo())) {
                    holder.goodsPriceDiscountRl.setVisibility(View.INVISIBLE);
                } else {
                    holder.goodsPriceDiscountText.setText(good.getCouponInfo()); //商品优惠卷价格
                }
                holder.goodsPriceText.setText(good.getGoodsFinalPrice()); //商品价格
                holder.goodsSaleNumText.setText(good.getVolume()+"");//商品销量
                Glide.with(context).load(good.getGoodsImg()).into(holder.goodsImg);
                holder.goodsLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (delegate != null) {
                            delegate.onClickItem(good);
                        }
                    }
                });
            }
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
        private RelativeLayout goodsPriceDiscountRl;
        private ImageView lineView;
    }


    public interface GoodListViewAdapterDelegate {

        void onClickItem(Good good);// 点击每一个item
    }
}
