package com.mobile.fsaliance.share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.util.FileUtils;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.T;

import java.io.File;
import java.util.Collections;
import java.util.List;

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
    private ShareItem wechatShareItem, qqShareItem;
    private String intentOrder = "android.intent.action.SEND";
    // 要分享的应用在手机中的包名
    private String strWeChatPackageName = "com.tencent.mm";
    private String strQQPackageName = "com.tencent.mobileqq";
    private String strWeChatFriendPackageName = "com.tencent.mm";

    // 要分享的应用在手机中的类名
    private String strWeChatActivityName = "com.tencent.mm.ui.tools.ShareImgUI";
    private String strQQActivityName = "com.tencent.mobileqq.activity.JumpActivity";
    private String strWeChatFriendActivityName = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
    // 图片格式
    private String imgFormat = "image/png";
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
        wechatShareItem = new ShareItem(getResources().getString(
                R.string.share_wechat), strWeChatActivityName, strWeChatPackageName);
        qqShareItem = new ShareItem(getResources().getString(
                R.string.share_qq), strQQActivityName, strQQPackageName);

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

    // 分享图片
    private void shareMsg(Context context, String msgTitle, String imgPath,
                          ShareItem share) {
        if (context == null || msgTitle == null || share == null) {
            L.e("context == null || msgTitle == null || share == null");
            return;
        }

        if (!share.packageName.isEmpty()
                && !isAvilible(this, share.packageName)) {
            T.showShort(context,
                    context.getResources().getString(R.string.share_please_install)
                            + share.title);
            return;
        }

        openApp(share);
        Intent intent = new Intent(intentOrder);
        if ((imgPath == null) || (imgPath.equals(""))) {
            L.e("imgPath == null || (imgPath.equals(''))");
            return;
        } else {

            if (FileUtils.isFileExists(imgPath)) {
                File file = new File(imgPath);
                intent.setType(imgFormat);
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            }
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (share.packageName.isEmpty()) {
            context.startActivity(Intent.createChooser(intent, msgTitle));
            return;
        }
        intent.setComponent(new ComponentName(share.packageName,
                share.activityName));
        context.startActivity(intent);
    }


    // 便利包名查看应用是否存在
    public boolean isAvilible(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();

        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (((PackageInfo) pinfo.get(i)).packageName
                    .equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }
    /**
     * @author 姜冬阳
     * @Description: 检查系统应用程序，并打开
     * @date 2017/2/27
     * ${tags}
     */
    private void openApp(ShareItem share){
        PackageManager mPackageManager = this.getPackageManager();
        //应用过滤条件
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);//PackageManager来获取所有APP:
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> mAllApps = mPackageManager.queryIntentActivities(mainIntent, 0);//哪些APP是否注册了一个明确的Intent
        //按包名排序
        Collections.sort(mAllApps, new ResolveInfo.DisplayNameComparator(mPackageManager));

        for(ResolveInfo res : mAllApps){
            //该应用的包名和主Activity
            String pkg = res.activityInfo.packageName;
            String cls = res.activityInfo.name;
            // 打开当前选中应用
            if(pkg.contains(share.packageName)){
                ComponentName componet = new ComponentName(pkg, cls);
                Intent intent = new Intent();
                intent.setComponent(componet);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
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
                shareMsg(this, getResources().getString(R.string.device_share_msgTitle), "", qqShareItem);
                break;
            //分享到微信
            case R.id.img_share_to_invite_wechat:
                shareMsg(this, getResources().getString(R.string.device_share_msgTitle), "", wechatShareItem);
                break;
            default:
                break;
        }
    }

    // 内部实体类
    private class ShareItem {
        String title;
        String activityName;
        String packageName;

        public ShareItem(String title, String activityName,
                         String packageName) {
            this.title = title;
            this.activityName = activityName;
            this.packageName = packageName;
        }
    }
}
