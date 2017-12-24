package com.mobile.fsaliance.mine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseView;
import com.mobile.fsaliance.common.vo.User;


public class MfrmBoundAlipayView extends BaseView {
    private ImageView userInfoBackImg;
    private TextView titleTxt, boundOkTxt;
    private LinearLayout titleLiftLl, titleRightLl;
    private EditText alipayAcountEdit;
    public MfrmBoundAlipayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void setInflate() {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.activity_boundalipay_view, this);
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
        titleTxt.setText(getResources().getString(R.string.mine_bound_alipay));
        boundOkTxt = (TextView) findViewById(R.id.txt_bound_ok);
        alipayAcountEdit = (EditText) findViewById(R.id.edit_alipay_acount);
    }

    @Override
    protected void addListener() {
        titleLiftLl.setOnClickListener(this);
        boundOkTxt.setOnClickListener(this);
    }

    @Override
    protected void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.txt_bound_ok:
                if (super.delegate instanceof MfrmBoundAlipayViewDelegate) {
                    ((MfrmBoundAlipayViewDelegate) super.delegate).onClickBoundAlipay();
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
    public interface MfrmBoundAlipayViewDelegate {

        void onClickBoundAlipay();//绑定支付宝

    }
}
