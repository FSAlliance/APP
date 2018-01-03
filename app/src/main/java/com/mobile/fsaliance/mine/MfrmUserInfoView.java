package com.mobile.fsaliance.mine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseView;
import com.mobile.fsaliance.common.vo.User;



public class MfrmUserInfoView extends BaseView {
    private ImageView userInfoBackImg, userHeadPortraitImg;
    private TextView titleTxt, logOffTxt, alipayAccountTxt, userNickNameTxt;
    private LinearLayout titleLiftLl, titleRightLl;
    private RelativeLayout userInfoHeadPortraitRl, userNickNameRl, userPasswordRl, userAlipayRl;

    public MfrmUserInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void setInflate() {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.activity_user_info_view, this);
    }

    @Override
    public void initData(Object... data) {
        User user = (User) data[0];
        if (user == null) {
            return;
        }
        if (!user.getAliPayAccount().equals("")) {
            alipayAccountTxt.setText(user.getAliPayAccount());
        }
        userNickNameTxt.setText(user.getUserName());
        Glide.with(context)
                .load(user.getUserHead())
                .placeholder(R.drawable.register_job_id)
                .into(userHeadPortraitImg);
    }


    @Override
    protected void initViews() {
        titleLiftLl = (LinearLayout) findViewById(R.id.ll_title_left);
        titleRightLl = (LinearLayout) findViewById(R.id.ll_title_right);
        titleRightLl.setVisibility(INVISIBLE);
        userInfoBackImg = (ImageView) findViewById(R.id.img_back);
        userInfoBackImg.setImageResource(R.drawable.goback);
        titleTxt = (TextView) findViewById(R.id.txt_title);
        titleTxt.setText(getResources().getString(R.string.mine_user_info));

        userInfoHeadPortraitRl = (RelativeLayout) findViewById(R.id.rl_user_info_head_portrait);
        userHeadPortraitImg = (ImageView) findViewById(R.id.img_user_info_head_portrait);
        userNickNameRl = (RelativeLayout) findViewById(R.id.rl_user_nickname);
        userNickNameTxt = (TextView) findViewById(R.id.txt_user_nickname);
        userAlipayRl = (RelativeLayout) findViewById(R.id.rl_user_alipay);
        userPasswordRl = (RelativeLayout) findViewById(R.id.rl_user_password);
        logOffTxt = (TextView) findViewById(R.id.txt_login_off);
        alipayAccountTxt = (TextView) findViewById(R.id.txt_user_alipay);
    }

    @Override
    protected void addListener() {
        titleLiftLl.setOnClickListener(this);
        userInfoHeadPortraitRl.setOnClickListener(this);
        userPasswordRl.setOnClickListener(this);
        userAlipayRl.setOnClickListener(this);
        userNickNameRl.setOnClickListener(this);
        logOffTxt.setOnClickListener(this);
    }

    @Override
    protected void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.rl_user_info_head_portrait:
                if (super.delegate instanceof MfrmUserInfoViewDelegate) {
                    ((MfrmUserInfoViewDelegate) super.delegate).onClickModifyHeadImg();
                }
                break;
            case R.id.rl_user_alipay:
                if (super.delegate instanceof MfrmUserInfoViewDelegate) {
                    ((MfrmUserInfoViewDelegate) super.delegate).onClickBoundAlipay();
                }
                break;
            case R.id.rl_user_password:
                if (super.delegate instanceof MfrmUserInfoViewDelegate) {
                    ((MfrmUserInfoViewDelegate) super.delegate).onClickModifyPassword();
                }
                break;
            case R.id.rl_user_nickname:
                if (super.delegate instanceof MfrmUserInfoViewDelegate) {
                    ((MfrmUserInfoViewDelegate) super.delegate).onClickModifyNickName();
                }
                break;
            case R.id.txt_login_off:
                if (super.delegate instanceof MfrmUserInfoViewDelegate) {
                    ((MfrmUserInfoViewDelegate) super.delegate).onClickClickOff();
                }
                break;
            default:
                break;
        }
    }
    /**
      * @date 创建时间 2017/9/6
      * @author tanyadong
      * @Description 登录代理页
    */
    public interface MfrmUserInfoViewDelegate {
        void onClickModifyHeadImg(); //修改头像

        void onClickModifyNickName(); //修改昵称

        void onClickModifyPassword();//修改密码

        void onClickBoundAlipay();//绑定支付宝

        void onClickClickOff();//推出登录
    }
}
