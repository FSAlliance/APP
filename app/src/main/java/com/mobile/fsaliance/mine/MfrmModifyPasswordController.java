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
import com.mobile.fsaliance.common.common.CircleProgressBarView;
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


public class MfrmModifyPasswordController extends BaseController implements OnResponseListener<String>, View.OnClickListener {

    private Object cancelObject = new Object();
    private RequestQueue queue;
    private static final int MODIFY_PWD = 0;
    private User user;
    private EditText originalPwdEdit, newPwdEdit, confirmPwdEdit;
    private TextView confirmModifyTxt;
    private LinearLayout titleLeftLl;
    private ImageView titleLeftImg;
    private CircleProgressBarView circleProgressBarView;
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
        setContentView(R.layout.activity_modify_password_controller);
        initView();
        addLinster();
        queue = NoHttp.newRequestQueue();
        initData(user);
    }

    private void initData(User user) {

    }

    private void addLinster() {
        confirmModifyTxt.setOnClickListener(this);
        titleLeftLl.setOnClickListener(this);
    }

    private void initView() {
        originalPwdEdit = (EditText) findViewById(R.id.edit_user_original_pwd);
        newPwdEdit = (EditText) findViewById(R.id.edit_user_new_pwd);
        confirmPwdEdit = (EditText) findViewById(R.id.edit_user_confirm_pwd);
        confirmModifyTxt = (TextView) findViewById(R.id.txt_confirm_modify);
        circleProgressBarView = (CircleProgressBarView) findViewById(R.id.circleProgressBarView);
        titleLeftImg = (ImageView) findViewById(R.id.img_back);
        titleLeftImg.setImageResource(R.drawable.goback);
        titleLeftLl = (LinearLayout) findViewById(R.id.ll_title_left);
    }


    @Override
    public void onFailed(int i, Response response) {
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
        T.showShort(this, R.string.modify_pwd_fail);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelBySign(cancelObject);
    }

    @Override
    public void onStart(int i) {
        circleProgressBarView.showProgressBar();
    }

    @Override
    public void onSucceed(int i, Response<String> response) {
        if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
            String result = (String) response.get();
            if (result == null || "".equals(result)) {
                T.showShort(this, R.string.modify_pwd_fail);
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.has("code") && jsonObject.getInt("code") == 0) {
                    JSONObject jsonUser = jsonObject.optJSONObject("content");
                    if (user == null) {
                        user = new User();
                    }
                    user.setUserName(jsonUser.optString("jobId"));
                    user.setPassword(jsonUser.optString("password"));
                    LoginUtils.saveUserInfo(this, user);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    T.showShort(this, R.string.modify_pwd_fail);
                }
            } catch (JSONException e) {
                T.showShort(this, R.string.modify_pwd_fail);
                e.printStackTrace();
            }
        } else {
            T.showShort(this, R.string.modify_pwd_fail);
        }
    }


    @Override
    public void onFinish(int i) {
        circleProgressBarView.hideProgressBar();
        user.setPassword("aaaaaaaaaaaaaaaaaaaaa");
        LoginUtils.saveUserInfo(this, user);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
            case R.id.txt_confirm_modify:
                String originalPwd = originalPwdEdit.getText().toString().trim();
                String newPwd = newPwdEdit.getText().toString().trim();
                String confirmPwd = confirmPwdEdit.getText().toString().trim();
                if (checkPwd(originalPwd, newPwd, confirmPwd)) {
                    String uri = AppMacro.REQUEST_URL + "/user/login";
                    Request<String> request = NoHttp.createStringRequest(uri);
                    request.setCancelSign(cancelObject);
                    queue.add(MODIFY_PWD, request, this);
                }
                break;
                default:
                    break;
        }

    }

    /**
     * @author tanyadong
     * @Title: checkPwd
     * @Description: 校验参数
     * @date 2018/1/2 0002 22:27
     */

    private boolean checkPwd(String originalPwd, String newPwd, String confirmPwd) {
        if (originalPwd.equals("") || newPwd.equals("") || confirmPwd.equals("")) {
            T.showShort(this,R.string.password_is_empty);
            return false;
        }
        user.setPassword("aa");
        if (user.getPassword() == null) {
            return false;
        }
        if (!user.getPassword().equals(originalPwd)) {
            T.showShort(this,R.string.original_pwd_is_error);
            return false;
        }

        if (!newPwd.equals(confirmPwd)) {
            T.showShort(this, R.string.new_pwd_is_error);
            return false;
        }
        return true;
    }
}
