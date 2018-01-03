package com.mobile.fsaliance.mine;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;


import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class MfrmMyOrderActivity extends BaseController implements ViewPager.OnPageChangeListener{

    private TabLayout tab_FindFragment_title; //定义TabLayout
    private ViewPager vp_FindFragment_pager; //定义viewPager
    private FragmentAdapter fAdapter; //定义adapter
    private List<Fragment> list_fragment; //定义要装fragment的列表
    private List<String> list_title; //tab名称列表
    private int position;

    @Override
    protected void getBundleData() {

    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        int result = StatusBarUtil.StatusBarLightMode(this);
        if (result != 0) {
            StatusBarUtil.initWindows(this, getResources().getColor(R.color.white));
        }
        setContentView(R.layout.activity_my_order);
        initControls();
    }

    /**
     * 初始化各控件
     */
    private void initControls() {
        tab_FindFragment_title = (TabLayout)findViewById(R.id.tab_FindFragment_title);

        vp_FindFragment_pager = (ViewPager)findViewById(R.id.vp_FindFragment_pager);
        //关闭预加载，默认一次只加载一个Fragment
//        vp_FindFragment_pager.setOffscreenPageLimit(3);
        //初始化各fragment
//        myOrderAllFragment = new MyOrderAllFragment();
//        myOrderUnPaymenFragment = new MyOrderUnPaymenFragment();
//        myOrderUnSendFragment=new MyOrderUnSendFragment();
//        myOrderUnDeliveryFragment=new MyOrderUnDeliveryFragment();
        //将fragment装进列表中
        list_fragment = new ArrayList<>();
//        list_fragment.add(myOrderAllFragment);
//        list_fragment.add(myOrderUnPaymenFragment);
//        list_fragment.add(myOrderUnSendFragment);
//        list_fragment.add(myOrderUnDeliveryFragment);
        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("全部");
        list_title.add("待付款");
        list_title.add("待发货");
        list_title.add("待收货");
        //设置TabLayout的模式
        //为TabLayout添加tab名称
//        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(0)));
//        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)));
//        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(2)));
//        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(3)));
//        fAdapter =new MyOrder_tab_Adapter(getSupportFragmentManager(),list_fragment,list_title);
        //viewpager加载adapter
        //TabLayout加载viewpager
        for(int i = 0; i < list_title.size(); i++){
            list_fragment.add(TabFragment.newInstance(list_title.get(i)));
        }
        fAdapter = new FragmentAdapter(this, getSupportFragmentManager(),list_fragment , list_title);
        vp_FindFragment_pager.setAdapter(fAdapter);//给ViewPager设置适配器
        tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);//将TabLayout和ViewPager关联起来
        tab_FindFragment_title.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.position = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
