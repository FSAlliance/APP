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


public class MfrmWithdrawalsView extends BaseView {
    private ImageView userInfoBackImg;
    private TextView titleTxt, presentmMonenyTxt, allPresentTxt;
    private LinearLayout titleLiftLl, titleRightLl;
    private EditText presentCountEdit;
    public MfrmWithdrawalsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void setInflate() {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.activity_withdrawals_view, this);
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
        titleTxt.setText(getResources().getString(R.string.mine_wallet_present));
        presentmMonenyTxt = (TextView) findViewById(R.id.txt_money_count);
        allPresentTxt = (TextView) findViewById(R.id.txt_present_all);
        presentCountEdit = (EditText) findViewById(R.id.edit_input_money);
    }

    @Override
    protected void addListener() {
        titleLiftLl.setOnClickListener(this);
        allPresentTxt.setOnClickListener(this);
    }

    @Override
    protected void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.txt_present_all:
                break;
            default:
                break;
        }
    }
    /**
      * @date 创建时间 2017/9/6
      * @author tanyadong
      * @Description 提现
    */
    public interface MfrmWithdrawalsViewDelegate {


    }
}
