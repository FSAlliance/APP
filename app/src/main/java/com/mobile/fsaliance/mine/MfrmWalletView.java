package com.mobile.fsaliance.mine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseView;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.vo.User;


public class MfrmWalletView extends BaseView {

    private ImageView userInfoBackImg;
    private TextView titleTxt, walletPresentTxt, recordIncomeTxt, presentRecordTxt, myBalanceTxt;
    private LinearLayout titleLiftLl, titleRightLl;
    public MfrmWalletView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void setInflate() {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.activity_wallet_view, this);
    }

    @Override
    public void initData(Object... data) {
        User user = (User) data[0];
        if (user == null) {
            L.e("user == null");
            return;
        }
        if (myBalanceTxt == null) {
            L.e("myBalanceTxt == null");
            return;
        }
        myBalanceTxt.setText(user.getBalanceNum()+"");
    }


    @Override
    protected void initViews() {
        titleLiftLl = (LinearLayout) findViewById(R.id.ll_title_left);
        titleRightLl = (LinearLayout) findViewById(R.id.ll_title_right);
        titleRightLl.setVisibility(INVISIBLE);
        userInfoBackImg = (ImageView) findViewById(R.id.img_back);
        userInfoBackImg.setImageResource(R.drawable.goback);
        titleTxt = (TextView) findViewById(R.id.txt_title);
        titleTxt.setText(getResources().getString(R.string.mine_wallet));
        walletPresentTxt = (TextView) findViewById(R.id.txt_wallet_present);
        presentRecordTxt = (TextView) findViewById(R.id.txt_present_record);
        recordIncomeTxt = (TextView) findViewById(R.id.txt_record_of_income);
        myBalanceTxt = (TextView) findViewById(R.id.txt_money_count);

    }

    @Override
    protected void addListener() {
        titleLiftLl.setOnClickListener(this);
        walletPresentTxt.setOnClickListener(this);
        presentRecordTxt.setOnClickListener(this);
        recordIncomeTxt.setOnClickListener(this);
    }

    /**
     * @param balance 金额
     * @author yuanxueyuan
     * @Title: setMyBalanceTxt
     * @Description: 设置余额
     * @date 2018/1/3 21:48
     */
    public void setMyBalanceTxt(String balance) {
        if (myBalanceTxt == null || balance == null) {
            L.e("myBalanceTxt == null");
            return;
        }
        myBalanceTxt.setText(balance);
    }

    @Override
    protected void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.ll_title_left:
                //点击返回键
                if (super.delegate instanceof MfrmWalletViewDelegate) {
                    ((MfrmWalletViewDelegate) super.delegate).onClickBack();
                }
                break;
            case R.id.txt_wallet_present:
                //点击提现
                if (super.delegate instanceof MfrmWalletViewDelegate) {
                    ((MfrmWalletViewDelegate) super.delegate).onClickWalletPresent();
                }
                break;
            case R.id.txt_record_of_income:
                //点击收入记录
                if (super.delegate instanceof MfrmWalletViewDelegate) {
                    ((MfrmWalletViewDelegate) super.delegate).onClickRecordOfIncome();
                }
                break;
            case R.id.txt_present_record:
                //点击提现记录
                if (super.delegate instanceof MfrmWalletViewDelegate) {
                    ((MfrmWalletViewDelegate) super.delegate).onClickPresentRecord();
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
    public interface MfrmWalletViewDelegate {

        void onClickBack();//点击返回

        void onClickWalletPresent();//点击提现

        void onClickRecordOfIncome();//点击收入记录

        void onClickPresentRecord();//点击提现记录



    }
}
