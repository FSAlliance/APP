package com.mobile.fsaliance.mine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseView;

/**
 * @author tanyadong
 * @Description: 我的
 * @date 2017/12/20 0020 23:10
 * ${tags}
 * &*/

public class MfrmMineView extends BaseView {
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


    }



    @Override
    protected void initViews() {
    }

    @Override
    protected void addListener() {
    }

    @Override
    protected void onClickListener(View v) {
        switch (v.getId()) {
            default:
                break;
        }

    }

    public interface AboutViewDelegate {

        void onClickBack();//点击返回

    }
}
