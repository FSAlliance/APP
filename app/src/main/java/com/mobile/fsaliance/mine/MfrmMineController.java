package com.mobile.fsaliance.mine;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseFragmentController;
import com.mobile.fsaliance.common.util.StatusBarUtil;



/**
 * Created by chenziqiang on 17/5/15.
 */

public class MfrmMineController extends BaseFragmentController implements MfrmMineView.AboutViewDelegate {
    private MfrmMineView mfrmMineView;

    @Override
    protected View onCreateViewFunc(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = null;
        view = inflater.inflate(R.layout.activity_mine_controller,
                null);
        mfrmMineView = (MfrmMineView) view
                .findViewById(R.id.mfrm_mine_view);
        mfrmMineView.setDelegate(this);
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(option);
            StatusBarUtil.setStatusBarColor(getActivity(), getResources().getColor(R.color.login_btn_color));
            StatusBarUtil.StatusBarLightMode(getActivity());
        }
        lazyLoad();
        return view;
    }

    @Override
    protected void getBundleData() {

    }

    @Override
    protected void lazyLoad() {

    }



    @Override
    public void onClickBack() {
    }


}
