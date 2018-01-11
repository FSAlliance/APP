package com.mobile.fsaliance.mine;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.vo.Order;


import java.util.ArrayList;

/**
 * Created by 谭亚东 on 2016/8/3.
 */
public class MyOrder_all_Adapter extends BaseAdapter{

    private Context context;
    protected LayoutInflater inflater;
    protected int resource;
    private ArrayList<Order> list;
    private int position_tag;
    private LinearLayout linearLayout;
    private ListView listView;
    public MyOrder_all_Adapter(Context context){
        this.context=context;
    }
    public MyOrder_all_Adapter(Context context, ArrayList<Order> list){
        inflater = LayoutInflater.from(context);
        this.context =context;
        this.list = list;
    }

    Handler mMandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public void updateList(ArrayList<Order> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list!=null){
            return list.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyOrderViewHolder vh = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_order,null);
            vh = new MyOrderViewHolder();
            vh.myOrderShopNameTxt= (TextView) convertView.findViewById(R.id.txt_myorder_shop_name);
            vh.myOrderStateTxt = (TextView) convertView.findViewById(R.id.txt_myorder_state);
            vh.myOrderMoney = (TextView) convertView.findViewById(R.id.txt_myorder_money);
            vh.myOrderID = (TextView) convertView.findViewById(R.id.txt_myorder_id);
            vh.myOrderTime = (TextView) convertView.findViewById(R.id.txt_myorder_time);
            vh.myOrderIntroduceTxt = (TextView) convertView.findViewById(R.id.txt_myorder_introduce);
            convertView.setTag(vh);
        }else {
            vh = (MyOrderViewHolder) convertView.getTag();
        }
        //赋值

        final Order order=list.get(position);

        if (order.getType() == 0) { //订单成功
            vh.myOrderMoney.setText(R.string.my_order_success);
        } else if (order.getType() == 1) { //已结算
            vh.myOrderMoney.setText(R.string.my_order_already_settled);
        } else if (order.getType() == 2) { //已付款
            vh.myOrderMoney.setText(R.string.my_order_already_paid);
        } else if (order.getType() == 3) { //失效
            vh.myOrderMoney.setText(R.string.my_order_fail);
        }
        vh.myOrderMoney.setText(String.valueOf(list.get(position).getMoney()));
        vh.myOrderIntroduceTxt.setText(order.getOrderItemTitle());
        vh.myOrderTime.setText(order.getOrderTime());
        vh.myOrderShopNameTxt.setText(order.getOrderShopTitle());
        vh.myOrderID.setText(order.getOrderNumber());
        return convertView;
    }









    public static class MyOrderViewHolder{
        TextView myOrderShopNameTxt;
        TextView myOrderStateTxt;
        TextView myOrderIntroduceTxt;
        TextView myOrderMoney;
        TextView myOrderID;
        TextView myOrderTime;

    }

}
