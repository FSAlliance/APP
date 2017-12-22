package com.mobile.fsaliance.mine;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.fsaliance.common.base.BaseFragmentController;
import com.mobile.fsaliance.common.util.AppUtils;
import com.mobile.tiandy.asset.R;


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
