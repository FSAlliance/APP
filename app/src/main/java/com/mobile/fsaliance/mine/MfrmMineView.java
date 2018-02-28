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
import com.mobile.fsaliance.common.util.CropCircleTransformation;
import com.mobile.fsaliance.common.vo.User;

/**
 * @author tanyadong
 * @Description: 我的
 * @date 2017/12/20 0020 23:10
 * ${tags}
 * &*/

public class MfrmMineView extends BaseView {
    private LinearLayout userNameLl, userBalanceLl, inPresentLl, hasBalanceLl;
    private TextView userAccountIdTxt, userNameTxt, userBalanceTxt, inPresentTxt, hasBalanceTxt;
    private ImageView userHeadImg; //头像
    private RelativeLayout mineOrderRl, findMyOrderRl, mineRecordIncomeRl, presentRecordRl, immediateCashRl, mineShareRl,
    boundAlipayRl;
    public MfrmMineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void setInflate() {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.activity_mine_view, this);
    }

    @Override
    public void initData(Object... data) {
        if (data == null) {
            return;
        }
        User user = (User) data[0];
        if (user == null) {
            return;
        }
        Glide.with(context).load(user.getUserHead()).placeholder(R.drawable.img_user_head).bitmapTransform(new CropCircleTransformation(context)).crossFade(1000).into(userHeadImg);
        userNameTxt.setText(user.getNickName());
        userAccountIdTxt.setText(user.getPhoneNum());
        userBalanceTxt.setText(String.valueOf(user.getBalanceNum()));
        inPresentTxt.setText(String.valueOf(user.getCashing()));
        hasBalanceTxt.setText(String.valueOf(user.getCashed()));
    }



    @Override
    protected void initViews() {
        userNameLl = (LinearLayout) findViewById(R.id.ll_username);
        userAccountIdTxt = (TextView) findViewById(R.id.txt_account_id);
        userNameTxt = (TextView) findViewById(R.id.txt_username);
        userHeadImg = (ImageView) findViewById(R.id.img_user);
        userBalanceLl = (LinearLayout) findViewById(R.id.ll_balance);
        inPresentLl = (LinearLayout) findViewById(R.id.ll_in_the_present);
        hasBalanceLl = (LinearLayout) findViewById(R.id.ll_has_balance);

        userBalanceTxt = (TextView) findViewById(R.id.txt_balance);
        inPresentTxt = (TextView) findViewById(R.id.txt_in_the_present);
        hasBalanceTxt = (TextView) findViewById(R.id.txt_has_balance);

        mineOrderRl = (RelativeLayout) findViewById(R.id.rl_mine_order);
        findMyOrderRl = (RelativeLayout) findViewById(R.id.rl_mine_order_find);
        mineRecordIncomeRl = (RelativeLayout) findViewById(R.id.rl_record_of_income);
        mineShareRl = (RelativeLayout) findViewById(R.id.rl_share);
        presentRecordRl = (RelativeLayout) findViewById(R.id.rl_present_record);
        boundAlipayRl = (RelativeLayout) findViewById(R.id.rl_bound_alipay);
        immediateCashRl = (RelativeLayout) findViewById(R.id.rl_immediate_cash);
    }

    @Override
    protected void addListener() {
        userNameLl.setOnClickListener(this);
        userBalanceLl.setOnClickListener(this);
        inPresentLl.setOnClickListener(this);
        hasBalanceLl.setOnClickListener(this);

        mineOrderRl.setOnClickListener(this);
        findMyOrderRl.setOnClickListener(this);
        mineRecordIncomeRl.setOnClickListener(this);
        mineShareRl.setOnClickListener(this);
        presentRecordRl.setOnClickListener(this);
        boundAlipayRl.setOnClickListener(this);
        immediateCashRl.setOnClickListener(this);
    }

    @Override
    protected void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.ll_username:
                if (super.delegate instanceof MfrmMineViewDelegate) {
                    ((MfrmMineViewDelegate) super.delegate).onClickToUserInfo();
                }
                break;
            case R.id.rl_bound_alipay:
                if (super.delegate instanceof MfrmMineViewDelegate) {
                    ((MfrmMineViewDelegate) super.delegate).onClickBoundAlipay();
                }
                break;
            case R.id.rl_immediate_cash:
                //立即提现
                if (super.delegate instanceof MfrmMineViewDelegate) {
                    ((MfrmMineViewDelegate) super.delegate).onClickImmediateCash();
                }
                break;
            case R.id.rl_present_record:
                //提现记录
                if (super.delegate instanceof MfrmMineViewDelegate) {
                    ((MfrmMineViewDelegate) super.delegate).onClickPresentRecord();
                }
                break;
            case R.id.rl_record_of_income:
                //收入记录
                if (super.delegate instanceof MfrmMineViewDelegate) {
                    ((MfrmMineViewDelegate) super.delegate).onClickRecordOfIncome();
                }
                break;

            case R.id.rl_share:
                if (super.delegate instanceof MfrmMineViewDelegate) {
                    ((MfrmMineViewDelegate) super.delegate).onClickShare();
                }
                break;
            case R.id.rl_mine_order:
                if (super.delegate instanceof MfrmMineViewDelegate) {
                    ((MfrmMineViewDelegate) super.delegate).onClickMyOrder();
                }
                break;
            case R.id.rl_mine_order_find:
                //找回订单
                if (super.delegate instanceof MfrmMineViewDelegate) {
                    ((MfrmMineViewDelegate) super.delegate).onClickFindMyOrder();
                }
                break;
            case R.id.ll_balance:
                //我的余额
                if (super.delegate instanceof MfrmMineViewDelegate) {
                    ((MfrmMineViewDelegate) super.delegate).onClickMyBalance();
                }
                break;
            case R.id.ll_in_the_present:
                //提现中
                if (super.delegate instanceof MfrmMineViewDelegate) {
                    ((MfrmMineViewDelegate) super.delegate).onClickInPresent();
                }
                break;
            case R.id.ll_has_balance:
                //已提现
                if (super.delegate instanceof MfrmMineViewDelegate) {
                    ((MfrmMineViewDelegate) super.delegate).onClickHasBalance();
                }
                break;
            default:
                break;
        }

    }

    public interface MfrmMineViewDelegate {
        void onClickToUserInfo();

        void onClickMyBalance();//我的余额

        void onClickInPresent();//提现中

        void onClickHasBalance();//已提现

        void onClickMyOrder(); //我的订单

        void onClickFindMyOrder(); //找回订单

        void onClickBoundAlipay();  //绑定支付宝

        void onClickShare(); //分享

        void onClickPresentRecord();//提现记录

        void onClickImmediateCash();//立即提现

        void onClickRecordOfIncome();//收入记录
    }
}
