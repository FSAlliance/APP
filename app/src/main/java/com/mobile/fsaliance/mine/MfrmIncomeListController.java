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
import com.mobile.fsaliance.common.vo.IncomeRecord;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;


public class MfrmIncomeListController extends BaseController implements View.OnClickListener {

    private ListView incomeListView;
    private ImageView incomeListBackImg;
    private TextView titleTxt;
    private LinearLayout titleLiftLl, titleRightLl;
    private IncomeListViewAdapter incomeListViewAdapter;
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

    private void initView() {
        incomeListView = (ListView) findViewById(R.id.income_list_view);

        titleLiftLl = (LinearLayout) findViewById(R.id.ll_title_left);
        titleRightLl = (LinearLayout) findViewById(R.id.ll_title_right);
        titleRightLl.setVisibility(INVISIBLE);
        incomeListBackImg = (ImageView) findViewById(R.id.img_back);
        incomeListBackImg.setImageResource(R.drawable.goback);
        titleTxt = (TextView) findViewById(R.id.txt_title);
        titleTxt.setText(getResources().getString(R.string.ming_record_of_income));
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<IncomeRecord> list = new ArrayList<>();
        showIncomeList(list);
    }

    private void addListener() {
        titleLiftLl.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * @author tanyadong
     * @Title showIncomeList
     * @Description 刷新并显示数据
     * @date 2017/9/8 14:44
     */
    public void showIncomeList(List<IncomeRecord> incomeRecordList) {
        if (incomeRecordList == null) {
            L.e("myAssetList == null");
            return;
        }
        if (incomeListViewAdapter == null) {
            incomeListViewAdapter = new IncomeListViewAdapter(this,
                    incomeRecordList);
            incomeListView.setAdapter(incomeListViewAdapter);
//            incomeListViewAdapter.setDelegate(this);
        } else {
            incomeListViewAdapter.update(incomeRecordList);
            incomeListViewAdapter.notifyDataSetChanged();
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
