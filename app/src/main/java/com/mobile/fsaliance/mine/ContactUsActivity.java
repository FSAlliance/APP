package com.mobile.fsaliance.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;

import static android.view.View.INVISIBLE;

/**
 * @author yuanxueyuan
 * @Description: 联系我们
 * @date 2018/2/27  20:19
 */
public class ContactUsActivity extends BaseController implements View.OnClickListener{

    private ImageView contactUsBackImg;
    private LinearLayout titleLiftLl, titleRightLl;
    private TextView titleTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        //初始化界面
        initView();

        addListener();
    }

    @Override
    protected void getBundleData() {

    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {

    }

    /**
     * @author yuanxueyuan
     * @Title: initView
     * @Description: 初始化界面布局
     * @date 2018/2/27 21:31
     */
    private void initView() {
        titleLiftLl = (LinearLayout) findViewById(R.id.ll_title_left);
        titleRightLl = (LinearLayout) findViewById(R.id.ll_title_right);
        titleRightLl.setVisibility(INVISIBLE);
        contactUsBackImg = (ImageView) findViewById(R.id.img_back);
        contactUsBackImg.setImageResource(R.drawable.goback);
        titleTxt = (TextView) findViewById(R.id.txt_title);
        titleTxt.setText(getResources().getString(R.string.contact_us_title));
    }

    /**
     * @author yuanxueyuan
     * @Title: addListener
     * @Description: 添加监听事件
     * @date 2018/2/27 21:31
     */
    private void addListener() {
        titleLiftLl.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
        }
    }
}
