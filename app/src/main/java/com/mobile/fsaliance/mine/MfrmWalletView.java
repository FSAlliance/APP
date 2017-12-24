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
import com.mobile.fsaliance.common.vo.User;


public class MfrmWalletView extends BaseView {
    private ImageView userInfoBackImg;
    private TextView titleTxt, walletPresentTxt, recordIncomeTxt, presentRecordTxt;
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
            return;
        }
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
    }

    @Override
    protected void addListener() {
        titleLiftLl.setOnClickListener(this);
        walletPresentTxt.setOnClickListener(this);
        presentRecordTxt.setOnClickListener(this);
        recordIncomeTxt.setOnClickListener(this);
    }

    @Override
    protected void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.txt_wallet_present:
                break;
            case R.id.txt_record_of_income:
                break;
            case R.id.txt_present_record:
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


    }
}
