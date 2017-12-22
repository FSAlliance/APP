package com.mobile.fsaliance.main;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mobile.fsaliance.common.base.BaseFragmentController;
import com.mobile.fsaliance.common.util.StatusBarUtil;
import com.mobile.fsaliance.common.util.T;
import com.mobile.fsaliance.home.MfrmHomeController;
import com.mobile.fsaliance.mine.MfrmMineController;
import com.mobile.fsaliance.supervoucher.MfrmSuperVoucherController;
import com.mobile.tiandy.asset.R;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private List<BaseFragmentController> list_fragment;
    private LinearLayout mineAssetLl, searchAssetLl, moreLl;
    private ImageView mineImg, searchImg, moreImg;
    private TextView mineTxt, searchTxt, moreTxt;

    private int id;
    private MfrmHomeController mfrmHomeController = null;
    private MfrmSuperVoucherController mfrmSuperVoucherController = null;
    private MfrmMineController mfrmMineController = null;
    // 上一次点击物理返回按键的时间
    private long lastCall_ACTION_BACT_Time = 0L;
    // 默认判断为连续点击的最大间隔时间
    private final long DOUBLE_CLICK_TIME_DELAY = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int result = StatusBarUtil.StatusBarLightMode(this);
        if (result != 0) {
            StatusBarUtil.initWindows(this, getResources().getColor(R.color.white));
        }
        setContentView(R.layout.activity_main);
        //初始化界面
        initView();
        //点击事件
        click();
    }


    @Override
    protected void onPostResume() {
        if(id==1){
            showHome();
            viewPager.setCurrentItem(0);
        } else if(id==2){
            showSuperVoucher();
            viewPager.setCurrentItem(1);
        }else{
            showMine();
            viewPager.setCurrentItem(2);
        }
        //初始化数据
//        initValues();
        super.onPostResume();
    }

    /**
     * 点击事件
     */
    private void click() {
        this.mineAssetLl.setOnClickListener(this);
        this.searchAssetLl.setOnClickListener(this);
        this.moreLl.setOnClickListener(this);
        //设置ViewPager滑动监听
        viewPager.setOnPageChangeListener(this);
    }

    /**
     * 初始化界面
     */
    private void initView() {
        this.mineAssetLl = (LinearLayout) findViewById(R.id.ll_mine_page);
        this.searchAssetLl = (LinearLayout) findViewById(R.id.ll_search_page);
        this.moreLl = (LinearLayout) findViewById(R.id.ll_more_page);

        this.mineTxt = (TextView) findViewById(R.id.txt_mine);
        this.searchTxt = (TextView) findViewById(R.id.txt_search);
        this.moreTxt = (TextView) findViewById(R.id.txt_more);

        this.mineImg = (ImageView) findViewById(R.id.img_mine);
        this.searchImg = (ImageView) findViewById(R.id.img_search);
        this.moreImg = (ImageView) findViewById(R.id.img_more);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        mfrmHomeController = new MfrmHomeController();
        mfrmMineController = new MfrmMineController();
        mfrmSuperVoucherController = new MfrmSuperVoucherController();

        list_fragment = new ArrayList<>();

        list_fragment.add(mfrmHomeController);
        list_fragment.add(mfrmSuperVoucherController);
        list_fragment.add(mfrmMineController);

        MainFragmentAdapter m1 = new MainFragmentAdapter(getSupportFragmentManager(),list_fragment);

        viewPager.setAdapter(m1);
        //关闭预加载，默认一次只加载一个Fragment
        viewPager.setOffscreenPageLimit(1);
        //关闭预加载，默认一次只加载一个Fragment
        showHome();
    }
    /**
     * 初始化数据
     */
    private void initValues() {

    }

    /**
     * @author tanyadong
     * @Title: showHome
     * @Description: 首页
     * @date 2017/12/20 0020 23:01
     */

    private void showHome(){
        mineTxt.setTextColor(getResources().getColor(R.color.green_light));
        searchTxt.setTextColor(getResources().getColor(R.color.optiontext_color_black));
        moreTxt.setTextColor(getResources().getColor(R.color.optiontext_color_black));
        mineImg.setImageResource(R.drawable.bottom_asset_select);
        searchImg.setImageResource(R.drawable.bottom_search_normol);
        moreImg.setImageResource(R.drawable.bottom_more_normol);
        //隐藏menu
        id = 1;
    }

    /**
      * @author tanyadong
      * @Title showMine
      * @Description 我的界面
      * @date 2017/9/5 21:38
    */
    private void showMine() {
        mineTxt.setTextColor(getResources().getColor(R.color.optiontext_color_black));
        searchTxt.setTextColor(getResources().getColor(R.color.optiontext_color_black));
        moreTxt.setTextColor(getResources().getColor(R.color.green_light));
        mineImg.setImageResource(R.drawable.bottom_asset_normol);
        searchImg.setImageResource(R.drawable.bottom_search_normol);
        moreImg.setImageResource(R.drawable.bottom_more_select);
       id = 3;
    }

    /**
      * @author tanyadong
      * @Title showSuperVoucher
      * @Description 点击超级券
      * @date 2017/9/5 21:37
    */
    private void showSuperVoucher(){
        mineTxt.setTextColor(getResources().getColor(R.color.optiontext_color_black));
        searchTxt.setTextColor(getResources().getColor(R.color.green_light));
        moreTxt.setTextColor(getResources().getColor(R.color.optiontext_color_black));
        mineImg.setImageResource(R.drawable.bottom_asset_normol);
        searchImg.setImageResource(R.drawable.bottom_search_select);
        moreImg.setImageResource(R.drawable.bottom_more_normol);
        //隐藏menu
        id =2;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_mine_page:
                showHome();
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_search_page:
                showSuperVoucher();
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_more_page:
                showMine();
                viewPager.setCurrentItem(2);
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                showHome();
                break;
            case 1:
                showSuperVoucher();
                break;
            case 2:
                showMine();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (event.getRepeatCount() == 0) {
                // 当用户点击一下的时候触发事件
                if (System.currentTimeMillis() - lastCall_ACTION_BACT_Time <= DOUBLE_CLICK_TIME_DELAY) {
                    finish();
                    System.exit(0);
                    android.os.Process.killProcess(android.os.Process.myPid());
                } else {
                    T.showShort(this,  R.string.mainframe_whethertoquit);
                    lastCall_ACTION_BACT_Time = System.currentTimeMillis();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
