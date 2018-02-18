package com.mobile.fsaliance.mine;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.WindowManager;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.common.AppMacro;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.LoginUtils;
import com.mobile.fsaliance.common.util.StatusBarUtil;
import com.mobile.fsaliance.common.util.T;
import com.mobile.fsaliance.common.vo.User;
import com.mobile.fsaliance.main.MfrmLoginController;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.UUID;


public class MfrmUserInfoController extends BaseController implements MfrmUserInfoView.MfrmUserInfoViewDelegate, OnResponseListener<String> {

    private MfrmUserInfoView mfrmUserInfoView;
    private Object cancelObject = new Object();
    private RequestQueue queue;
    private User user;
    private static final int GALLERY_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private String phoroPath = AppMacro.PHOTO_PATH + "photo.jpeg";
    @Override
    protected void getBundleData() {
        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("user");
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
//        user = LoginUtils.getUserInfo(this);
        if (user == null) {
            return;
        }
        mfrmUserInfoView.initData(user);
    }

    /**
     * @author tanyadong
     * @Title: updatePicture
     * @Description: 上传头像照片
     * @date 2018/1/15 0015 22:45
     * @param
     */

    private void updatePicture(File file) {
        String uri = AppMacro.REQUEST_IP_PORT + AppMacro.REQUEST_GOODS_PATH+ AppMacro.REQUEST_UPDATE_USER_PHOTO;
        Request<String> request = NoHttp.createStringRequest(uri, RequestMethod.POST);
        request.setCancelSign(cancelObject);
        request.add("file", file);
        request.setMultipartFormEnable(true);
        queue.add(0, request, this);
        L.e("tyd----"+request.url());
    }

    private void updateUserHeadInfo(String phoro1Path, String userId) {
        String uri = AppMacro.REQUEST_IP_PORT + AppMacro.REQUEST_GOODS_PATH+ AppMacro.REQUEST_UPDATE_USER_HEAD;
        Request<String> request = NoHttp.createStringRequest(uri);
        request.setCancelSign(cancelObject);
        request.add("userPic",phoro1Path);
//        request.add("userId","8732ed2f-edfd-40c5-a05d-3ae326e594c6");
        request.add("userId", userId);
        queue.add(1, request, this);
        L.e("tyd----"+request.url());
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
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        startActivityForResult(intent, 4);
    }

    @Override
    public void onClickModifyPassword() {
        Intent intent = new Intent();
        intent.setClass(this, MfrmModifyPasswordController.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        startActivityForResult(intent, 3);
    }

    @Override
    public void onClickBoundAlipay() {
        Intent intent = new Intent();
        intent.setClass(this, MfrmBoundAlipayController.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("user", user);
//        intent.putExtras(bundle);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onClickClickOff() {
        LoginUtils.saveUserInfo(this, null);
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
        phoroPath =AppMacro.PHOTO_PATH +  UUID.randomUUID().toString()+".jpg";
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
    public void onClickSetAttributes(float bgAlpha) {
        WindowManager.LayoutParams lp= this.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        this.getWindow().setAttributes(lp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    File temp = new File(phoroPath);
                    mfrmUserInfoView.setSelectPhoto(Uri.fromFile(temp));
                    updatePicture(temp);
                    break;
                case GALLERY_REQUEST_CODE:
                    mfrmUserInfoView.setSelectPhoto(data.getData());
                    String path = getRealFilePath(this, data.getData());
                    L.e("tyd------path"+path);
                    updatePicture(new File(path));
                    break;
                case 2:
                    mfrmUserInfoView.setAlipayBound(data.getStringExtra("boundAlipay"));
                    break;
                case 3:
                    user = (User) data.getSerializableExtra("user");
                    break;
                case 4:
                    user = (User) data.getSerializableExtra("user");
                    if (user == null) {
                        return;
                    }
                    mfrmUserInfoView.setNickName(user.getNickName());
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart(int i) {

    }
    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    @Override
    public void onSucceed(int i, Response<String> response) {
        if (!response.isSucceed()) {
            T.showShort(this, R.string.video_update_fail);
            return;
        }
        switch (i) {
            case 0:
                    String path = response.get().toString();
                    L.e("tyd--"+path);
                    if (path == null) {
                        T.showShort(this, R.string.video_update_fail);
                    } else {
                        updateUserHeadInfo(path, user.getId());
                    }
                break;
            case 1:
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.get().toString());
                    if (jsonObject.optInt("ret") == 0) {
                    } else {
                        T.showShort(this, getResources().getString(R.string.video_update_fail));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    T.showShort(this, getResources().getString(R.string.video_update_fail));
                }

                break;
        }
    }

    @Override
    public void onFailed(int i, Response<String> response) {
        Exception exception = response.getException();
        if (exception instanceof NetworkError) {
            T.showShort(this, R.string.network_error);
            return;
        }
        if (exception instanceof SocketTimeoutException) {
            T.showShort(this, R.string.network_socket_timeout_error);
            return;
        }
        if (exception instanceof UnKnownHostError) {
            T.showShort(this, R.string.network_unknown_host_error);
            return;
        }
        if (exception instanceof ConnectException) {
            T.showShort(this, R.string.network_error);
            return;
        }
        T.showShort(this, R.string.video_update_fail);
    }

    @Override
    public void onFinish(int i) {

    }
}
