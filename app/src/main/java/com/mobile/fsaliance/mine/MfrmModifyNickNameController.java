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
    @Override
    protected void getBundleData() {
        Bundle bundle= getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        user = (User) bundle.getSerializable("user");
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
                T.showShort(this, R.string.bound_fail);
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.has("code") && jsonObject.getInt("code") == 0) {
                    JSONObject jsonUser = jsonObject.optJSONObject("content");
                    if (user == null) {
                        user = new User();
                    }
                    user.setNickName(modifyNickNameEdit.getText().toString().trim());
                    LoginUtils.saveUserInfo(this, user);
                    Intent intent = new Intent();
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                } else {
                    T.showShort(this, R.string.bound_fail);
                }
            } catch (JSONException e) {
                T.showShort(this, R.string.bound_fail);
                e.printStackTrace();
            }
        } else {
            T.showShort(this, R.string.bound_fail);
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
        T.showShort(this, R.string.bound_fail);
    }

    @Override
    public void onFinish(int i) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_confirm_modify:
                user = LoginUtils.getUserInfo(this);
                if (user == null) {
                    user = new User();
                }
                LoginUtils.saveUserInfo(this, user);

                String uri = AppMacro.REQUEST_URL + "/user/login";
                Request<String> request = NoHttp.createStringRequest(uri);
                request.setCancelSign(cancelObject);
                request.add("user", user.getId());
                request.add("alipayAcount", "");
                queue.add(0, request, this);
                break;
            case R.id.ll_title_left:
                finish();
                break;
            default:
                break;
        }

    }
}
