package com.mobile.fsaliance.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.util.L;

/**
 * @author yuanxueyuan
 * @Description: 分享界面
 * @date 2017/12/27  21:13
 */
public class ShareActivity extends Activity implements View.OnClickListener {

    private RelativeLayout shareAppRL, shareToInviteRL;
    private LinearLayout backLL;
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        //初始化界面
        initView();

        //添加监听器
        addListener();

        //初始化数据
        initValues();
    }


    /**
     * @author yuanxueyuan
     * @Title: initView
     * @Description: 初始化界面
     * @date 2017/12/27 21:03
     */
    private void initView() {
        backLL = (LinearLayout) findViewById(R.id.ll_title_left);
        titleText = (TextView) findViewById(R.id.txt_title_middle);
        shareAppRL = (RelativeLayout) findViewById(R.id.rl_share_app);
        shareToInviteRL = (RelativeLayout) findViewById(R.id.rl_share_to_invite);
    }

    /**
     * @author yuanxueyuan
     * @Title: initValues
     * @Description: 初始化数据
     * @date 2017/12/27 21:22
     */
    private void initValues() {
        if (titleText == null) {
            L.e("titleText == null");
            return;
        }

        titleText.setText(R.string.share_title);
    }

    /**
     * @author yuanxueyuan
     * @Title: addListener
     * @Description: 添加监听器
     * @date 2017/12/27 21:06
     */
    private void addListener() {
        shareAppRL.setOnClickListener(this);
        shareToInviteRL.setOnClickListener(this);
        backLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            //分享APP
            case R.id.rl_share_app:
                intent.setClass(this,ShareAppActivity.class);
                break;
            //分享赚钱
            case R.id.rl_share_to_invite:
                intent.setClass(this,ShareToInviteActivity.class);
                break;
            //返回键
            case R.id.ll_title_left:
                finish();
                return;
            default:
                break;
        }
        startActivity(intent);
    }
}
