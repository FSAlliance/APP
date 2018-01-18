package com.mobile.fsaliance.mine;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseView;
import com.mobile.fsaliance.common.util.CropCircleTransformation;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.vo.User;

import java.util.ArrayList;


public class MfrmUserInfoView extends BaseView {
    private ImageView userInfoBackImg, userHeadPortraitImg;
    private TextView titleTxt, logOffTxt, alipayAccountTxt, userNickNameTxt;
    private LinearLayout titleLiftLl, titleRightLl;
    private RelativeLayout userInfoHeadPortraitRl, userNickNameRl, userPasswordRl, userAlipayRl;
    private RelativeLayout selectPhotoRl;
    private PopupWindow popupWindow;
    private TextView tackPhoneBtn, pickPictureBtn, cancelBtn;
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
        if (user.getUserHead() == null || user.getUserName() == null) {
            return;
        }

        if (user.getAliPayAccount() != null && !user.getAliPayAccount().equals("")) {
            alipayAccountTxt.setText(user.getAliPayAccount());
        }

        userNickNameTxt.setText(user.getUserName());
        setSelectPhoto(Uri.parse(user.getUserHead()));
    }

    public void setSelectPhoto(Uri uri) {
        Glide.with(context)
                .load(uri)
                .placeholder(R.drawable.img_user_head)
                .bitmapTransform(new CropCircleTransformation(context)).crossFade(1000).into(userHeadPortraitImg);
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
        initPopupWindow();
    }
    /**
     * @author tanyadong
     * @Title: initPopupWindow
     * @Description: 初始化popupwindow
     * @date 2017/12/2 0002 10:47
     */

    private void initPopupWindow() {
        selectPhotoRl = (RelativeLayout) LayoutInflater.from(context).inflate(
                R.layout.select_photo, null);
        tackPhoneBtn = (TextView) selectPhotoRl.findViewById(R.id.picture_selector_take_photo_btn);
        pickPictureBtn = (TextView) selectPhotoRl.findViewById(R.id.picture_selector_pick_picture_btn);
        cancelBtn = (TextView) selectPhotoRl.findViewById(R.id.picture_selector_cancel_btn);
        popupWindow = new PopupWindow(selectPhotoRl, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置弹出窗体可点击
        popupWindow.setAnimationStyle(R.style.take_photo_anim);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setWidth(LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (delegate instanceof MfrmUserInfoViewDelegate) {
                    ((MfrmUserInfoViewDelegate)delegate).onClickSetAttributes((float) 1);
                }
            }
        });
    }
    @Override
    protected void addListener() {
        titleLiftLl.setOnClickListener(this);
        userInfoHeadPortraitRl.setOnClickListener(this);
        userPasswordRl.setOnClickListener(this);
        userAlipayRl.setOnClickListener(this);
        userNickNameRl.setOnClickListener(this);
        logOffTxt.setOnClickListener(this);

        tackPhoneBtn.setOnClickListener(this);
        pickPictureBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    protected void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.rl_user_info_head_portrait:
                showPopupWindow();
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
            case R.id.picture_selector_cancel_btn:
                dismissPopupWindow();
                break;
            case R.id.picture_selector_pick_picture_btn:
                dismissPopupWindow();
                if (super.delegate instanceof MfrmUserInfoViewDelegate) {
                    ((MfrmUserInfoViewDelegate) super.delegate).onClickPickPicture();
                }
                break;
            case R.id.picture_selector_take_photo_btn:
                dismissPopupWindow();
                if (super.delegate instanceof MfrmUserInfoViewDelegate) {
                    ((MfrmUserInfoViewDelegate) super.delegate).onClickTackPhoto();
                }
                break;
            case R.id.ll_title_left:
                if (super.delegate instanceof MfrmUserInfoViewDelegate) {
                    ((MfrmUserInfoViewDelegate) super.delegate).onClickBack();
                }
                break;
            default:
                break;
        }
    }
    /**
     * 移除PopupWindow
     */
    public void dismissPopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
    /**
     * @author tanyadong
     * @Title: showPopupWindow
     * @Description: 显示窗体
     * @date 2017/12/2 0002 11:45
     */

    public void  showPopupWindow() {
        // 设置弹出窗体显示时的动画，从底部向上弹出
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(this, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        if (super.delegate instanceof MfrmUserInfoViewDelegate) {
            ((MfrmUserInfoViewDelegate) super.delegate).onClickSetAttributes((float) 0.5);
        }
    }

    /**
     * @author tanyadong
     * @Title: setAlipayBound
     * @Description: 设置支付宝账号
     * @date 2018/1/18 0018 21:38
     */

    public void setAlipayBound(String alipayBound) {
        alipayAccountTxt.setText(alipayBound);
    }

    /**
      * @date 创建时间 2017/9/6
      * @author tanyadong
      * @Description 登录代理页
    */
    public interface MfrmUserInfoViewDelegate {
        void onClickBack(); //返回

        void onClickModifyNickName(); //修改昵称

        void onClickModifyPassword();//修改密码

        void onClickBoundAlipay();//绑定支付宝

        void onClickClickOff();//退出登录

        void onClickTackPhoto(); //拍照

        void onClickPickPicture(); //选择

        void onClickSetAttributes(float bgAlpha);//设置activity背景透明度
    }
}
