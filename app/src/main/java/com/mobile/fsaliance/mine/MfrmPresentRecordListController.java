package com.mobile.fsaliance.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.StatusBarUtil;
import com.mobile.fsaliance.common.vo.Asset;
import com.yanzhenjie.nohttp.rest.RequestQueue;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;


public class MfrmPresentRecordListController extends BaseController implements View.OnClickListener {

    private Object cancelObject = new Object();
    private RequestQueue queue;
    private ListView presentReccordListview;
    private ImageView incomeListBackImg;
    private TextView titleTxt, boundOkTxt;
    private LinearLayout titleLiftLl, titleRightLl;
    private PresentRecordListViewAdapter presentRecordListViewAdapter;
    @Override
    protected void getBundleData() {

    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        int result = StatusBarUtil.StatusBarLightMode(this);
        if (result != 0) {
            StatusBarUtil.initWindows(this, getResources().getColor(R.color.white));
        }
        setContentView(R.layout.activity_incomelist_controller);
        initView();
        addListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Asset> list = new ArrayList<>();
        showPresentRecordList(list);
    }

    private void initView() {
        presentReccordListview = (ListView) findViewById(R.id.presentrecord_list_view);

        titleLiftLl = (LinearLayout) findViewById(R.id.ll_title_left);
        titleRightLl = (LinearLayout) findViewById(R.id.ll_title_right);
        titleRightLl.setVisibility(INVISIBLE);
        incomeListBackImg = (ImageView) findViewById(R.id.img_back);
        incomeListBackImg.setImageResource(R.drawable.goback);
        titleTxt = (TextView) findViewById(R.id.txt_title);
        titleTxt.setText(getResources().getString(R.string.ming_present_record));
    }

    private void addListener() {
        titleLiftLl.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelBySign(cancelObject);
    }


    /**
     * @author tanyadong
     * @Title showPresentRecordList
     * @Description 刷新并显示数据
     * @date 2017/9/8 14:44
     */
    public void showPresentRecordList(List<Asset> myAssetList) {
        if (myAssetList == null) {
            L.e("myAssetList == null");
            return;
        }
        if (presentRecordListViewAdapter == null) {
            presentRecordListViewAdapter = new PresentRecordListViewAdapter(this,
                    myAssetList);
            presentReccordListview.setAdapter(presentRecordListViewAdapter);
//            incomeListViewAdapter.setDelegate(this);
        } else {
            presentRecordListViewAdapter.update(myAssetList);
            presentRecordListViewAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
        }
    }
}
