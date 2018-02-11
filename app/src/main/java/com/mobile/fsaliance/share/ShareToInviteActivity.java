package com.mobile.fsaliance.share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.util.FileUtils;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.LoginUtils;
import com.mobile.fsaliance.common.util.StatusBarUtil;
import com.mobile.fsaliance.common.util.T;
import com.mobile.fsaliance.common.vo.User;

import java.io.File;
import java.util.Collections;
import java.util.List;

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
    // 要分享的应用在手机中的包名
    private String strWeChatPackageName = "com.tencent.mm";
    private String strQQPackageName = "com.tencent.mobileqq";
    private String strWeChatFriendPackageName = "com.tencent.mm";

    // 要分享的应用在手机中的类名
    private String strWeChatActivityName = "com.tencent.mm.ui.tools.ShareImgUI";
    private String strQQActivityName = "com.tencent.mobileqq.activity.JumpActivity";
    private String strWeChatFriendActivityName = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
    private ShareItem wechatShareItem, qqShareItem;
    private String intentOrder = "android.intent.action.SEND";

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_to_invite);
        int result = StatusBarUtil.StatusBarLightMode(this);
        if (result != 0) {
            StatusBarUtil.initWindows(this, getResources().getColor(R.color.white));
        }
        user = LoginUtils.getUserInfo(this);

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

        wechatShareItem = new ShareItem(getResources().getString(
                R.string.share_wechat), strWeChatActivityName, strWeChatPackageName);
        qqShareItem = new ShareItem(getResources().getString(
                R.string.share_qq), strQQActivityName, strQQPackageName);
    }

    /**
     * @author yuanxueyuan
     * @Title: initValues
     * @Description: 初始化数据
     * @date 2017/12/27 21:22
     */
    private void initValues() {
        if (titleText == null || user == null) {
            L.e("titleText == null");
            return;
        }
        titleText.setText(R.string.share_to_invite_title);
        String inviteNum = user.getShareCode();
        codeText.setText(inviteNum);
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

    /**
     * @author tanyadong
     * @Title: openApp
     * @Description: 打开app
     * @date 2018/1/11 0011 19:35
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
     * @author tanyadong
     * @Title: shareMsg
     * @Description: 分享文字
    @date 2018/1/11 0011 19:35
     */

    private void shareMsg(Context context, String msgTitle, String codeTxt,
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
        intent.putExtra(Intent.EXTRA_TEXT, codeTxt);
        intent.setType("text/plain");

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //返回键
            case R.id.ll_title_left:
                finish();
                break;
            //复制邀请码
            case R.id.text_share_to_invite_copy:
                if (codeText == null) {
                    T.showShort(this, R.string.share_copy_error);
                    return;
                }
                String myShareNum = codeText.getText().toString().trim();
                if ("".equals(myShareNum)) {
                    T.showShort(this, R.string.share_copy_error);
                    return;
                }
                // 复制到剪切板
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if (cm == null) {
                    T.showShort(this, R.string.share_copy_error);
                    return;
                }
                // 将文本内容放到系统剪贴板里。
                cm.setText(myShareNum);
                T.showShort(this, R.string.share_copy_success);
                break;
            //分享到QQ
            case R.id.img_share_to_invite_qq:
                shareMsg(this, getResources().getString(R.string.device_share_msgTitle), codeText.getText().toString().trim(), qqShareItem);
                break;
            //分享到微信
            case R.id.img_share_to_invite_wechat:
                shareMsg(this, getResources().getString(R.string.device_share_msgTitle), codeText.getText().toString().trim(), wechatShareItem);
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
