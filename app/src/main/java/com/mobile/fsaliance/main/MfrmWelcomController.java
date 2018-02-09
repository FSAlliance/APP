package com.mobile.fsaliance.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.common.AppMacro;
import com.mobile.fsaliance.common.common.InitApplication;
import com.mobile.fsaliance.common.util.LoginUtils;
import com.mobile.fsaliance.common.vo.User;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.Timer;
import java.util.TimerTask;

public class MfrmWelcomController extends BaseController implements OnResponseListener{

    private Timer timer;
    private MyTimerTask task;
    private MyHandler myHandler;
    private int WHAT = 1;
    private Object cancelObject = new Object();
    private RequestQueue queue;
    private final int UPDATE_LOGIN_TIME = 0;

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

    @Override
    public void onStart(int i) {

    }

    @Override
    public void onSucceed(int i, Response response) {

    }

    @Override
    public void onFailed(int i, Response response) {

    }

    @Override
    public void onFinish(int i) {
        //跳转主页面
        gotoMainView();
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
                User user = LoginUtils.getUserInfo(InitApplication.getInstance());
                if (user == null) {
                    gotoLoginView();
                } else {
                    queue = NoHttp.newRequestQueue();
                    updateLoginTime(user);
                }
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
     * @author yuanxueyuan
     * @Title: updateLoginTime
     * @Description: 更新登录的时间
     * @date 2018/2/9 12:07
     */
    private void updateLoginTime(User user) {
        String uri = AppMacro.REQUEST_IP_PORT + AppMacro.REQUEST_GOODS_PATH +AppMacro.REQUEST_UPDATE_LOGIN_TIME;
        Request<String> request = NoHttp.createStringRequest(uri);
        request.setCancelSign(cancelObject);
        request.add("userId", user.getId());
        queue.add(UPDATE_LOGIN_TIME, request, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (queue != null) {
            queue.cancelBySign(cancelObject);
        }
    }
}
