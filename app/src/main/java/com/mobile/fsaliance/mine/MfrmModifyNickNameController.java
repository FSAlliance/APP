package com.mobile.fsaliance.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.common.AppMacro;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.LoginUtils;
import com.mobile.fsaliance.common.util.StatusBarUtil;
import com.mobile.fsaliance.common.util.T;
import com.mobile.fsaliance.common.vo.User;
import com.mobile.fsaliance.main.MainActivity;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;


public class MfrmModifyNickNameController extends BaseController implements OnResponseListener<String>, View.OnClickListener {

    private Object cancelObject = new Object();
    private RequestQueue queue;
    private User user;
    private ImageView userInfoBackImg;
    private TextView titleTxt, modifyNickNameOkTxt;
    private LinearLayout titleLiftLl, titleRightLl;
    private EditText modifyNickNameEdit;
    private final int UPDATE_USER_NAME = 0;
    private String userName;//用户名
    @Override
    protected void getBundleData() {
        user = LoginUtils.getUserInfo(this);
    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        int result = StatusBarUtil.StatusBarLightMode(this);
        if (result != 0) {
            StatusBarUtil.initWindows(this, getResources().getColor(R.color.white));
        }
        setContentView(R.layout.activity_modify_nickname_controller);
        initView();
        addLinister();
        queue = NoHttp.newRequestQueue();
        initData();
    }

    private void initData() {
        if (user.getNickName() != null) {
            modifyNickNameEdit.setText(user.getNickName());
        }
    }

    private void addLinister() {
        modifyNickNameOkTxt.setOnClickListener(this);
        titleLiftLl.setOnClickListener(this);
    }

    private void initView() {
        titleLiftLl = (LinearLayout) findViewById(R.id.ll_title_left);
        titleRightLl = (LinearLayout) findViewById(R.id.ll_title_right);
        titleRightLl.setVisibility(View.VISIBLE);
        userInfoBackImg = (ImageView) findViewById(R.id.img_back);
        userInfoBackImg.setImageResource(R.drawable.goback);
        titleTxt = (TextView) findViewById(R.id.txt_title);
        titleTxt.setText(getResources().getString(R.string.modify_nickname));
        modifyNickNameOkTxt = (TextView) findViewById(R.id.txt_confirm_modify);
        modifyNickNameEdit = (EditText) findViewById(R.id.edit_user_nickname);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelBySign(cancelObject);
    }

    @Override
    public void onStart(int i) {
    }

    @Override
    public void onSucceed(int i, Response<String> response) {
        if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
            String result = (String) response.get();
            if (result == null || "".equals(result)) {
                T.showShort(this, R.string.please_input_user_error);
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.has("ret") && jsonObject.getInt("ret") == 0) {
                    user.setNickName(userName);
                    LoginUtils.saveUserInfo(this, user);
                    Intent intent = new Intent();
                    intent.putExtra("user", user);
                    setResult(0,intent);
                    finish();
                } else {
                    T.showShort(this, R.string.please_input_user_error);
                }
            } catch (JSONException e) {
                T.showShort(this, R.string.please_input_user_error);
                e.printStackTrace();
            }
        } else {
            T.showShort(this, R.string.please_input_user_error);
        }
    }

    @Override
    public void onFailed(int i, Response<String> response) {
        Exception exception = response.getException();
        if (exception instanceof NetworkError) {
            T.showShort(this, R.string.network_error);
            return;
        }
        if (exception instanceof UnKnownHostError) {
            T.showShort(this, R.string.network_unknown_host_error);
            return;
        }
        if (exception instanceof SocketTimeoutException) {
            T.showShort(this, R.string.network_socket_timeout_error);
            return;
        }
        T.showShort(this, R.string.please_input_user_error);
    }

    @Override
    public void onFinish(int i) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_confirm_modify:
                if (modifyNickNameEdit == null) {
                    L.e("modifyNickNameEdit == null");
                    T.showShort(this, R.string.please_input_user_error);
                    return;
                }
                String userName = modifyNickNameEdit.getText().toString().trim();
                updateUserName(userName);
                break;
            case R.id.ll_title_left:
                finish();
                break;
            default:
                break;
        }

    }

    /**
     * @param userName 用户名
     * @author yuanxueyuan
     * @Title: updateUserName
     * @Description: 更新用户名称
     * @date 2018/2/10 15:15
     */
    private void updateUserName(String userName) {
        if (userName == null || "".equals(userName)) {
            T.showShort(this, R.string.please_input_user_name);
            return;
        }
        this.userName = userName;
        String uri = AppMacro.REQUEST_IP_PORT + AppMacro.REQUEST_GOODS_PATH + AppMacro.REQUEST_UPDATE_USER_NAME;
        Request<String> request = NoHttp.createStringRequest(uri);
        request.setCancelSign(cancelObject);
        request.add("userId", user.getId());
        request.add("userName", userName);
        queue.add(UPDATE_USER_NAME, request, this);
    }
}
