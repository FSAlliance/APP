package com.mobile.fsaliance.mine;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.util.OrderType;
import com.mobile.fsaliance.common.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class MfrmMyOrderActivity extends BaseController implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private TabLayout tab_FindFragment_title; //定义TabLayout
    private ViewPager vp_FindFragment_pager; //定义viewPager
    private FragmentAdapter fAdapter; //定义adapter
    private List<Fragment> list_fragment; //定义要装fragment的列表
    private List<OrderType> list_title; //tab名称列表
    private int position;
    private LinearLayout titleLiftLl, titleRightLl;
    private TextView titleTxt;
    private ImageView titleLiftImg;
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
        addLinister();
    }

    private void addLinister() {
        titleLiftLl.setOnClickListener(this);
    }

    /**
     * 初始化各控件
     */
    private void initControls() {
        titleLiftLl = (LinearLayout) findViewById(R.id.ll_title_left);
        titleRightLl = (LinearLayout) findViewById(R.id.ll_title_right);
        titleRightLl.setVisibility(View.INVISIBLE);
        titleLiftImg = (ImageView) findViewById(R.id.img_back);
        titleLiftImg.setImageResource(R.drawable.goback);
        titleTxt = (TextView) findViewById(R.id.txt_title);
        titleTxt.setText(getResources().getString(R.string.ming_my_order));
        tab_FindFragment_title = (TabLayout)findViewById(R.id.tab_FindFragment_title);

        vp_FindFragment_pager = (ViewPager)findViewById(R.id.vp_FindFragment_pager);
        //关闭预加载，默认一次只加载一个Fragment
        vp_FindFragment_pager.setOffscreenPageLimit(1);
        //将fragment装进列表中
        list_fragment = new ArrayList<>();

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add(new OrderType("全部", "0"));
        list_title.add(new OrderType("待付款", "1"));
        list_title.add(new OrderType("待发货", "2"));
        list_title.add(new OrderType("待发货", "3"));
        //TabLayout加载viewpager
        for(int i = 0; i < list_title.size(); i++){
            list_fragment.add(TabFragment.newInstance(list_title.get(i).getOid()));
        }
        fAdapter = new FragmentAdapter(this, getSupportFragmentManager(),list_fragment , list_title);
        vp_FindFragment_pager.setAdapter(fAdapter);//给ViewPager设置适配器
        tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);//将TabLayout和ViewPager关联起来
        //设置TabLayout的模式
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
            default:
                break;
        }
    }
}
