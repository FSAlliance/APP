package com.mobile.fsaliance.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseView;
import com.mobile.fsaliance.common.common.CircleProgressBarView;
import com.mobile.fsaliance.common.util.T;
import com.mobile.fsaliance.common.vo.User;



public class MfrmLoginView extends BaseView {

    private TextView loginTxt;
    private EditText usernameEditTxt;
    private EditText passwordEditTxt;
    private LinearLayout registerLl;

    public CircleProgressBarView circleProgressBarView;

    public MfrmLoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void setInflate() {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.activity_login_view, this);
    }

    @Override
    public void initData(Object... data) {
        User user = (User) data[0];
        if (user == null) {
            return;
        }
        usernameEditTxt.setText(user.getJobId());
        passwordEditTxt.setText(user.getPassword());
    }


    @Override
    protected void initViews() {
        circleProgressBarView = (CircleProgressBarView) findViewById(R.id.circleProgressBarView);
        loginTxt= (TextView) findViewById(R.id.txt_login);
        registerLl = (LinearLayout) findViewById(R.id.rl_trgister);
        usernameEditTxt = (EditText) findViewById(R.id.edit_user_name);
        passwordEditTxt = (EditText) findViewById(R.id.edit_password);
    }

    @Override
    protected void addListener() {
        loginTxt.setOnClickListener(this);
        registerLl.setOnClickListener(this);
    }

    @Override
    protected void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.txt_login:
                String jobId = usernameEditTxt.getText().toString().trim();
                String password = passwordEditTxt.getText().toString().trim();

                if (!checkInfo(jobId, password)) {
                    return;
                }
                if (super.delegate instanceof MfrmLoginViewDelegate) {
                    ((MfrmLoginViewDelegate) super.delegate).onClickLogin(jobId, password);
                }
                break;
            case R.id.rl_trgister:
                if (super.delegate instanceof MfrmLoginViewDelegate) {
                    ((MfrmLoginViewDelegate) super.delegate).onClickRegister();
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
    private boolean checkInfo(String jobId, String password) {
        if (null == jobId || "".equals(jobId)) {
            T.showShort(context, R.string.username_is_empty);
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
      * @Description 登录代理页
    */
    public interface MfrmLoginViewDelegate {
        //点击登入
        void onClickLogin(String jobId, String password);

        //点击注册跳转到注册界面
        void onClickRegister();
    }
}
