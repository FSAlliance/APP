package com.mobile.fsaliance.share;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.util.L;

/**
 * @author yuanxueyuan
 * @Description: 分享赚钱界面，即分享推荐码
 * @date 2017/12/27  21:14
 * ${tags}
 */
public class ShareToInviteActivity extends Activity implements View.OnClickListener {

    private LinearLayout backLL;
    private TextView titleText;
    private TextView copyCodeText;
    private TextView codeText;
    private ImageView shareToQQImg, shareToWechatImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_to_invite);

        initView();

        initValues();

        addListener();
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
        copyCodeText = (TextView) findViewById(R.id.text_share_to_invite_copy);
        codeText = (TextView) findViewById(R.id.text_share_to_invite_code);
        shareToQQImg = (ImageView) findViewById(R.id.img_share_to_invite_qq);
        shareToWechatImg = (ImageView) findViewById(R.id.img_share_to_invite_wechat);
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

        titleText.setText(R.string.share_to_invite_title);

        codeText.setText("WWWWWWWWW");
    }

    /**
     * @author yuanxueyuan
     * @Title: addListener
     * @Description: 添加监听器
     * @date 2017/12/27 21:06
     */
    private void addListener() {
        copyCodeText.setOnClickListener(this);
        backLL.setOnClickListener(this);
        shareToQQImg.setOnClickListener(this);
        shareToWechatImg.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //返回键
            case R.id.ll_title_left:
                finish();
                break;
            //复制邀请码
            case R.id.text_share_to_invite_copy:
                break;
            //分享到QQ
            case R.id.img_share_to_invite_qq:
                break;
            //分享到微信
            case R.id.img_share_to_invite_wechat:
                break;
            default:
                break;
        }
    }
}
