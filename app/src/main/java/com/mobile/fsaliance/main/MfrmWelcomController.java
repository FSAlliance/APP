package com.mobile.fsaliance.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.util.LoginUtils;
import com.mobile.fsaliance.common.vo.User;

import java.util.Timer;
import java.util.TimerTask;

public class MfrmWelcomController extends BaseController {

    private Timer timer;
    private MyTimerTask task;
    private MyHandler myHandler;
    private int WHAT = 1;
    private User user;

    @Override
    protected void getBundleData() {
    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welome_controller);
        //延时1.5秒跳转
        myHandler = new MyHandler();
        timer = new Timer();
        task = new MyTimerTask();
        timer.schedule(task, 1500);
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            myHandler.sendEmptyMessage(WHAT);
        }
    }

    /**
     * @author zengCheng
     * @Description: 延迟1.5秒跳转
     * @date 2017.03.22
     */
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT) {
                gotoMainView();
            }
        }
    }

    /**
     * @author tanyadong
     * @Title: gotoMainView
     * @Description: 跳转到主界面
     * @date 2017/12/20 0020 23:33
     */

    private void gotoMainView() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }







    /**
     * @author zengCheng
     * @Description: 保存登录信息
     * @date 2017.03.22
     */
    private void saveUserInfo(){
        LoginUtils.saveUserInfo(this, user);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
