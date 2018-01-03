package com.mobile.fsaliance.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.common.AppMacro;
import com.mobile.fsaliance.common.util.LoginUtils;
import com.mobile.fsaliance.common.util.StatusBarUtil;
import com.mobile.fsaliance.common.vo.User;
import com.mobile.fsaliance.main.MfrmLoginController;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.RequestQueue;

import java.io.File;


public class MfrmUserInfoController extends BaseController implements MfrmUserInfoView.MfrmUserInfoViewDelegate {

    private MfrmUserInfoView mfrmUserInfoView;
    private Object cancelObject = new Object();
    private RequestQueue queue;
    private User user;
    private static final int GALLERY_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private String phoroPath = AppMacro.PHOTO_PATH +  File.separator + "photo.jpeg";
    @Override
    protected void getBundleData() {

    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        int result = StatusBarUtil.StatusBarLightMode(this);
        if (result != 0) {
            StatusBarUtil.initWindows(this, getResources().getColor(R.color.white));
        }
        setContentView(R.layout.activity_user_info_controller);
        mfrmUserInfoView = (MfrmUserInfoView) findViewById(R.id.activity_userinfo_view);
        mfrmUserInfoView.setDelegate(this);
        queue = NoHttp.newRequestQueue();
        user = LoginUtils.getUserInfo(this);
        if (user == null) {
            return;
        }
        mfrmUserInfoView.initData(user);
    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelBySign(cancelObject);
    }


    @Override
    public void onClickBack() {
        finish();
    }

    @Override
    public void onClickModifyNickName() {
        Intent intent = new Intent();
        intent.setClass(this, MfrmModifyNickNameController.class);
        //todo 参数
        startActivity(intent);
    }

    @Override
    public void onClickModifyPassword() {
        Intent intent = new Intent();
        intent.setClass(this, MfrmModifyPasswordController.class);
        //todo 参数
        startActivity(intent);
    }

    @Override
    public void onClickBoundAlipay() {
        Intent intent = new Intent();
        intent.setClass(this, MfrmBoundAlipayController.class);
        //todo 参数
        startActivity(intent);
    }

    @Override
    public void onClickClickOff() {
        Intent intent = new Intent();
        intent.setClass(this, MfrmLoginController.class);
        startActivity(intent);
        finish();
    }

    /**
     * @author tanyadong
     * @Title: onClickTackPhoto
     * @Description: 拍照
     * @date 2018/1/3 0003 20:13
     */

    @Override
    public void onClickTackPhoto() {
        // "拍照"按钮被点击了
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(phoroPath)));
        startActivityForResult(takeIntent, CAMERA_REQUEST_CODE);
    }
    /**
     * @author tanyadong
     * @Title: onClickPickPicture
     * @Description: 选择照片
     * @date 2018/1/3 0003 20:13
     */

    @Override
    public void onClickPickPicture() {
        // "从相册选择"按钮被点击了
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == this.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    File temp = new File(phoroPath);
                    mfrmUserInfoView.setSelectPhoto(Uri.fromFile(temp));
                    break;
                case GALLERY_REQUEST_CODE:
                    mfrmUserInfoView.setSelectPhoto(data.getData());
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
