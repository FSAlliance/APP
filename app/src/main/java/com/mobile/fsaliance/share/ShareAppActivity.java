package com.mobile.fsaliance.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.util.L;

/**
 * @author yuanxueyuan
 * @Description: 分享APP
 * @date 2017/12/29  20:47
 * ${tags}
 */
public class ShareAppActivity extends Activity implements View.OnClickListener {

    private TextView titleTxt;
    private LinearLayout backLL;
    private ImageView QRCodeImg, shareToQQImg, shareToWeChatImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_app);

        initView();

        addListener();

        initValues();
    }

    /**
     * @author yuanxueyuan
     * @Title: initView
     * @Description: 初始化界面布局
     * @date 2017/12/29 20:47
     */
    private void initView() {
        titleTxt = (TextView) findViewById(R.id.txt_title_middle);
        backLL = (LinearLayout) findViewById(R.id.ll_title_left);
        shareToQQImg = (ImageView) findViewById(R.id.img_share_to_invite_qq);
        shareToWeChatImg = (ImageView) findViewById(R.id.img_share_to_invite_wechat);
        QRCodeImg = (ImageView) findViewById(R.id.img_share_app_qr);
    }

     /**
     * @author  yuanxueyuan
     * @Title:  initValues
     * @Description: 初始化数据
     * @date 2017/12/29 20:50
     */
    private void initValues(){
        if (titleTxt == null) {
            L.e("titleTxt == null");
            return;
        }
        titleTxt.setText(R.string.share_app);
    }

    /**
     * @param bitmap 二维码
     * @author yuanxueyuan
     * @Title: setQRCodeImg
     * @Description: 设置二维码
     * @date 2018/1/4 20:46
     */
    private void setQRCodeImg(Bitmap bitmap) {
        if (QRCodeImg == null || bitmap == null) {
            L.e("QRCodeImg == null");
            return;
        }
        QRCodeImg.setImageBitmap(bitmap);
    }

    /**
     * @author yuanxueyuan
     * @Title: addListener
     * @Description: 添加监听事件
     * @date 2017/12/29 20:49
     */
    private void addListener() {
        backLL.setOnClickListener(this);
        shareToQQImg.setOnClickListener(this);
        shareToWeChatImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //返回键
            case R.id.ll_title_left:
                finish();
                break;
            //分享到QQ
            case R.id.img_share_to_invite_qq:
                finish();
                break;
            //分享到微信
            case R.id.img_share_to_invite_wechat:
                finish();
                break;
            default:
                break;
        }
    }
}
