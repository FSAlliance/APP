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
    public MyOrder_all_Adapter(Context context, int resource, ArrayList<Order> list, ListView listView,LinearLayout nointent){
        inflater = LayoutInflater.from(context);
        this.context =context;
        this.list = list;
        this.resource = resource;
        this.linearLayout=nointent;
        this.listView=listView;
    }

    Handler mMandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    public void addItems(ArrayList<Order> list){
        this.list.addAll(list);
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

        vh.myOrderMoney.setText(String.valueOf(list.get(position).getMoney()));



//        //未付款未取消订单
//        if(list.get(position).tag==1 && list.get(position).cancleOrNot==0){
//            //未取消订单
//            vh.myOrderStateTxt.setText("等待付款");
//        }
//        if(list.get(position).completeOrNot==1){ //已经完成
//            vh.myOrderStateTxt.setText("实付款");
//        }



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
