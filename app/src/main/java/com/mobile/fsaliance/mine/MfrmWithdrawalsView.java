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
import com.mobile.fsaliance.common.common.AppMacro;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.T;
import com.mobile.fsaliance.common.vo.User;


public class MfrmWithdrawalsView extends BaseView {
    private ImageView userInfoBackImg;
    private TextView titleTxt, presentmMonenyTxt, allPresentTxt;
    private LinearLayout titleLiftLl, titleRightLl;
    private EditText presentCountEdit;
    private TextView presentOkTxt; //确认提现
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
            L.e("user == null");
            return;
        }
        if (presentmMonenyTxt == null) {
            L.e("presentmMonenyTxt == null");
            return;
        }
        presentmMonenyTxt.setText(user.getBalanceNum()+"");
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
        presentOkTxt = (TextView) findViewById(R.id.txt_present_ok);
    }

    @Override
    protected void addListener() {
        titleLiftLl.setOnClickListener(this);
        allPresentTxt.setOnClickListener(this);
        presentOkTxt.setOnClickListener(this);
    }

    @Override
    protected void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.ll_title_left:
                if (super.delegate instanceof MfrmWithdrawalsViewDelegate) {
                    ((MfrmWithdrawalsViewDelegate) super.delegate).onClickBack();
                }
                break;
            case R.id.txt_present_all:
                String money = presentmMonenyTxt.getText().toString().trim();
                if (money == null || "".equals(money) || Integer.parseInt(money) == 0) {
                    T.showShort(context, context.getResources().getString(R.string.present_moneny_null));
                    return;
                }
                presentCountEdit.setText(presentmMonenyTxt.getText().toString());
                break;
            case R.id.txt_present_ok:
                if (presentCountEdit.getText().toString().trim() == null || presentCountEdit.getText().toString().equals("")) {
                    T.showShort(context, context.getResources().getString(R.string.please_input_present_moneny));
                    return;
                }
                if (Integer.parseInt(presentCountEdit.getText().toString().trim()) > Integer.parseInt( presentmMonenyTxt.getText().toString().trim())) {
                    T.showShort(context, context.getResources().getString(R.string.present_moneny_overrun));
                    return;
                }
                if (Integer.parseInt(presentmMonenyTxt.getText().toString().trim()) < AppMacro.MIN_MONEY_TO_GET) {
                    T.showShort(context, context.getResources().getString(R.string.present_moneny_min));
                    return;
                }
                if (super.delegate instanceof MfrmWithdrawalsViewDelegate) {
                    ((MfrmWithdrawalsViewDelegate) super.delegate).onClickPresent(presentCountEdit.getText().toString().trim());
                }
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
        void onClickPresent(String presentMoneny); //提现
        void onClickBack();
    }
}
