package com.mobile.fsaliance.main;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.fsaliance.common.base.BaseView;
import com.mobile.fsaliance.common.common.CircleProgressBarView;
import com.mobile.fsaliance.common.util.T;
import com.mobile.tiandy.asset.R;



public class MfrmRegisterView extends BaseView {

    private TextView registerTxt;
    private EditText passwordEditTxt, userNameEdit, refereeAcountEdit;
    private ImageView title_backImg;
    private TextView titleTxt;
    private ImageView titltRightImg;
    private LinearLayout showPassWordLl;
    private ImageButton showPassWordImg;
    private boolean isShowPassword;
    private LinearLayout titleLiftLl;
    public CircleProgressBarView circleProgressBarView;
    private TextView directLoginTxt;

    public MfrmRegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void setInflate() {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.activity_register_view, this);
    }

    @Override
    public void initData(Object... data) {

    }


    @Override
    protected void initViews() {
        circleProgressBarView = (CircleProgressBarView) findViewById(R.id.circleProgressBarView);
        registerTxt = (TextView) findViewById(R.id.txt_register);
        passwordEditTxt = (EditText) findViewById(R.id.edit_password);
        userNameEdit = (EditText) findViewById(R.id.edit_user_name);
        refereeAcountEdit = (EditText) findViewById(R.id.edit_referee_acount);
        titleLiftLl = (LinearLayout) findViewById(R.id.ll_title_left);
        title_backImg = (ImageView) findViewById(R.id.img_back);
        title_backImg.setImageResource(R.drawable.goback);
        titleTxt = (TextView) findViewById(R.id.txt_title);
        directLoginTxt = (TextView) findViewById(R.id.txt_direct_login);
    }

    @Override
    protected void addListener() {
        registerTxt.setOnClickListener(this);
        title_backImg.setOnClickListener(this);
        titleLiftLl.setOnClickListener(this);
        directLoginTxt.setOnClickListener(this);
    }

    @Override
    protected void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.txt_register:
                String refereeAcount = refereeAcountEdit.getText().toString().trim();
                String username = userNameEdit.getText().toString().trim();
                String password = passwordEditTxt.getText().toString().trim();
                if (!checkInfo(username, password, refereeAcount)) {
                    return;
                }
                if (super.delegate instanceof MfrmRegisterDelegate) {
                    ((MfrmRegisterDelegate) super.delegate).onClickRegister(refereeAcount, username, password);
                }
                break;
            case R.id.ll_title_left:
            case R.id.img_back:
                if (super.delegate instanceof MfrmRegisterDelegate) {
                    ((MfrmRegisterDelegate) super.delegate).onClickBack();
                }
                break;
            case R.id.txt_direct_login:
                if (super.delegate instanceof MfrmRegisterDelegate) {
                    ((MfrmRegisterDelegate) super.delegate).onClickToLogin();
                }
                break;
            default:
                break;
        }
    }

    /**
     * @author tanyadong
     * @Description: 检验输入字段
     * @date 2017.0.23
     */
    private boolean checkInfo(String username, String password, String jobId) {
        if (null == jobId || "".equals(jobId)) {
            T.showShort(context, R.string.username_is_empty);
            return false;
        }
        if (null == username || "".equals(username)) {
            T.showShort(context, R.string.password_is_empty);
            return false;
        }
        if (null == password || "".equals(password)) {
            T.showShort(context, R.string.password_is_empty);
            return false;
        }
        return true;
    }

   /**
     * @date 创建时间 2017/9/6
     * @author tanyadong
     * @Description
   */
    public interface MfrmRegisterDelegate {
        //点击注册
        void onClickRegister(String jobID, String userName, String passwor);
        //点击返回
        void onClickBack();
        //点击登录
        void onClickToLogin();
    }
}
