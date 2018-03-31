package com.mobile.fsaliance.main;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseFragmentController;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.LoginUtils;
import com.mobile.fsaliance.common.util.StatusBarUtil;
import com.mobile.fsaliance.common.util.T;
import com.mobile.fsaliance.common.vo.User;
import com.mobile.fsaliance.supers.MfrmSuperVouchersController;
import com.mobile.fsaliance.mine.MfrmMineController;
import com.mobile.fsaliance.home.MfrmHomeController;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private List<BaseFragmentController> list_fragment;
    private LinearLayout mineLl, superLl, homeLl;
    private ImageView homeImg, superImg, mineImg;
    private TextView homeTxt, superTxt, mineTxt;

    private int id;
    private MfrmSuperVouchersController mfrmSuperVouchersController = null;
    private MfrmHomeController mfrmHomeController = null;
    private MfrmMineController mfrmMineController = null;
    // 上一次点击物理返回按键的时间
    private long lastCall_ACTION_BACT_Time = 0L;
    // 默认判断为连续点击的最大间隔时间
    private final long DOUBLE_CLICK_TIME_DELAY = 2000;
    int result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        result = StatusBarUtil.StatusBarLightMode(this);
        if (result != 0) {
            StatusBarUtil.initWindows(this, getResources().getColor(R.color.login_btn_color));
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
     * @author yuanxueyuan
     * @Title: gotoLoginView
     * @Description: 跳转到登录界面
     * @date 2018/2/9 11:54
     */
    private void gotoLoginView() {
        Intent intent = new Intent();
        intent.setClass(this, MfrmLoginController.class);
        startActivity(intent);
        finish();
    }
    /**
     * 点击事件
     */
    private void click() {
        this.mineLl.setOnClickListener(this);
        this.superLl.setOnClickListener(this);
        this.homeLl.setOnClickListener(this);
        //设置ViewPager滑动监听
        viewPager.setOnPageChangeListener(this);
    }

    /**
     * 初始化界面
     */
    private void initView() {
        this.mineLl = (LinearLayout) findViewById(R.id.ll_mine_page);
        this.superLl = (LinearLayout) findViewById(R.id.ll_super_page);
        this.homeLl = (LinearLayout) findViewById(R.id.ll_home_page);

        this.homeTxt = (TextView) findViewById(R.id.txt_home);
        this.superTxt = (TextView) findViewById(R.id.txt_super);
        this.mineTxt = (TextView) findViewById(R.id.txt_mine);

        this.homeImg = (ImageView) findViewById(R.id.img_home);
        this.superImg = (ImageView) findViewById(R.id.img_super);
        this.mineImg = (ImageView) findViewById(R.id.img_mine);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        mfrmSuperVouchersController = new MfrmSuperVouchersController();
        mfrmMineController = new MfrmMineController();
        mfrmHomeController = new MfrmHomeController();

        list_fragment = new ArrayList<>();

        list_fragment.add(mfrmHomeController);
        list_fragment.add(mfrmSuperVouchersController);
        list_fragment.add(mfrmMineController);

        MainFragmentAdapter m1 = new MainFragmentAdapter(getSupportFragmentManager(),list_fragment);

        viewPager.setAdapter(m1);
        //关闭预加载，默认一次只加载一个Fragment
        viewPager.setOffscreenPageLimit(1);
        //关闭预加载，默认一次只加载一个Fragment
        showHome();
    }

    /**
     * @author tanyadong
     * @Title: showHome
     * @Description: 首页
     * @date 2017/12/20 0020 23:01
     */

    private void showHome(){
        if (result != 0) {
            StatusBarUtil.initWindows(this, getResources().getColor(R.color.login_btn_color));
        }
        homeTxt.setTextColor(getResources().getColor(R.color.login_btn_color));
        superTxt.setTextColor(getResources().getColor(R.color.optiontext_color_black));
        mineTxt.setTextColor(getResources().getColor(R.color.optiontext_color_black));
        homeImg.setImageResource(R.drawable.img_tab_home_select);
        superImg.setImageResource(R.drawable.img_tab_supers);
        mineImg.setImageResource(R.drawable.img_tab_mine);
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
        if (result != 0) {
            StatusBarUtil.initWindows(this, getResources().getColor(R.color.login_btn_color));
        }
        homeTxt.setTextColor(getResources().getColor(R.color.optiontext_color_black));
        superTxt.setTextColor(getResources().getColor(R.color.optiontext_color_black));
        mineTxt.setTextColor(getResources().getColor(R.color.login_btn_color));
        homeImg.setImageResource(R.drawable.img_tab_home);
        superImg.setImageResource(R.drawable.img_tab_supers);
        mineImg.setImageResource(R.drawable.img_tab_mine_select);
       id = 3;
    }

    /**
      * @author tanyadong
      * @Title showSuperVoucher
      * @Description 点击超级券
      * @date 2017/9/5 21:37
    */
    private void showSuperVoucher(){
        if (result != 0) {
            StatusBarUtil.initWindows(this, getResources().getColor(R.color.white));
        }
        homeTxt.setTextColor(getResources().getColor(R.color.optiontext_color_black));
        superTxt.setTextColor(getResources().getColor(R.color.login_btn_color));
        mineTxt.setTextColor(getResources().getColor(R.color.optiontext_color_black));
        homeImg.setImageResource(R.drawable.img_tab_home);
        superImg.setImageResource(R.drawable.img_tab_supers_select);
        mineImg.setImageResource(R.drawable.img_tab_mine);
        //隐藏menu
        id =2;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home_page:
                showHome();
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_super_page:
                showSuperVoucher();
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_mine_page:
                User user = LoginUtils.getUserInfo(this);
                if (user == null) {
                    gotoLoginView();
                } else {
                    showMine();
                    viewPager.setCurrentItem(2);
                }
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
