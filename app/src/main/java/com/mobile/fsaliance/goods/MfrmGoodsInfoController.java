package com.mobile.fsaliance.goods;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.common.AppMacro;
import com.mobile.fsaliance.common.common.CircleProgressBarView;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.T;
import com.mobile.fsaliance.common.vo.Good;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class MfrmGoodsInfoController extends BaseController implements View.OnClickListener, OnResponseListener {

    private TextView oneKeyCopyText, goodsInfoCodeText, toTaoBaoText;
    private ImageView goodsInfoImg;
    private TextView titleTxt;
    private LinearLayout backLL;
    private CircleProgressBarView circleProgressBarView;

    private static final int GET_GOOD_CODE = 0;//获取淘口令
    private Object cancelObject = new Object();
    private RequestQueue queue;
    private Good good;//商品信息


    @Override
    protected void getBundleData() {
        Intent intent = getIntent();
        if (intent == null) {
            L.e("intent == null");
            return;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            L.e("bundle == null");
            return;
        }
        good = (Good) bundle.getSerializable("good");
        L.e("QQQQQQQQQQQ", "good: " + good.toString());
    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_info_controller);
        queue = NoHttp.newRequestQueue();

        //初始化界面
        initView();

        //添加监听事件
        addListener();

        //初始化数据
        initValues(good);

    }

    /**
     * @author yuanxueyuan
     * @Title: initView
     * @Description: 初始化界面布局
     * @date 2017/12/24 23:00
     */
    private void initView() {
        titleTxt = (TextView) findViewById(R.id.txt_title_middle);
        titleTxt.setText(R.string.goods_info_title);
        backLL = (LinearLayout) findViewById(R.id.ll_title_left);
        oneKeyCopyText = (TextView) findViewById(R.id.goods_info_copy_one_key);
        goodsInfoImg = (ImageView) findViewById(R.id.goods_info_img);
        goodsInfoCodeText = (TextView) findViewById(R.id.goods_info_code);
        toTaoBaoText = (TextView) findViewById(R.id.goods_info_to_taobao);
        circleProgressBarView = (CircleProgressBarView) findViewById(R.id.good_info_circleProgressBarView);
    }

    /**
     * @author yuanxueyuan
     * @Title: initValues
     * @Description: 初始化数据
     * @date 2017/12/25 21:07
     */
    private void initValues(Good good) {
        if (good == null) {
            L.e("good == null");
            finish();
            return;
        }
        Glide.with(this).load(good.getGoodsImg()).into(goodsInfoImg);
        getGoodInfos(good);
    }

    /**
     * @param good 商品信息
     * @author yuanxueyuan
     * @Title: getGoodInfos
     * @Description: 获取淘口令
     * @date 2018/1/29 20:12
     */
    private void getGoodInfos(Good good) {
        if (good == null) {
            L.e("good == null");
            return;
        }
        String uri = AppMacro.REQUEST_IP_PORT + AppMacro.REQUEST_GOODS_PATH + AppMacro.REQUEST_GET_GOOD_CODE;
        Request<String> request = NoHttp.createStringRequest(uri);
        request.cancelBySign(cancelObject);
        request.add("userId", "128556731");
        request.add("text", good.getGoodsTitle());
        request.add("url", good.getCouponClickUrl());//编码
        request.add("logo", good.getGoodsImg());
        request.add("ext", "");
        L.i("QQQQQQQQQQ", "url: " + request.url());
        queue.add(GET_GOOD_CODE, request, this);
    }

    /**
     * @author yuanxueyuan
     * @Title: getGoodCode
     * @Description: 获取商品优惠卷的
     * @date 2017/12/25 22:15
     */
    private String getGoodCode() {
        if (goodsInfoCodeText == null) {
            return "";
        }
        return goodsInfoCodeText.getText().toString().trim();
    }

    /**
     * @author yuanxueyuan
     * @Title: addListener
     * @Description: 添加监听事件
     * @date 2017/12/24 23:02
     */
    private void addListener() {
        backLL.setOnClickListener(this);
        oneKeyCopyText.setOnClickListener(this);
        toTaoBaoText.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public static boolean checkPackage(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //返回键
            case R.id.ll_title_left:
                finish();
                break;
            //一键复制
            case R.id.goods_info_copy_one_key:
                String goodCode = getGoodCode();
                if ("".equals(goodCode)) {
                    T.showShort(this, "出现错误啦");
                    return;
                }
                // 复制到剪切板
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(goodCode);
                T.showShort(this, "复制成功");
                break;
            //直接领券
            case R.id.goods_info_to_taobao:
                //TODO 跳转淘宝 根据地址
                String goodCode1 = getGoodCode();
                if (checkPackage(this, "com.taobao.taobao")) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
//                    String url = "taobao://shop.m.taobao.com/shop/shop_index.htm?shop_id=131259851&spm=a230r.7195193.1997079397.8.Pp3ZMM&point";
//                    Uri uri = Uri.parse(url);
//                    intent.setData(uri);
                    Uri uri = Uri.parse(good.getItemUrl()); // 商品地址
                    intent.setData(uri);
                    intent.setClassName("com.taobao.taobao", "com.taobao.tao.detail.activity.DetailActivity");
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart(int i) {
        circleProgressBarView.showProgressBar();
    }

    @Override
    public void onSucceed(int i, Response response) {
        if (response.responseCode() == AppMacro.RESPONCESUCCESS) {
            String result = (String) response.get();
            L.i("QQQQQQQ", "result: " + result);
            switch (i) {
                //获取选品库中的信息
                case GET_GOOD_CODE:
                    if (result == null || "".equals(result)) {
                        L.e("result == null");
                        return;
                    }
                    analyzeData(result);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * @param result 获取到的结果
     * @author yuanxueyuan
     * @Title: analyzeData
     * @Description: 解析数据
     * @date 2018/1/29 20:27
     */
    private void analyzeData(String result) {
        if (result == null || "".equals(result)) {
            //获取失败
            //暂无优惠卷
            return;
        }
        try {
            JSONObject resultJson = new JSONObject(result);
            if (resultJson == null) {
                //暂无优惠卷
                L.e("resultJson == null");
                return;
            }
            JSONObject response = resultJson.optJSONObject("tbk_tpwd_create_response");
            if (response == null) {
                L.e("response == null");
                //暂无优惠卷
                return;
            }
            JSONObject data = response.optJSONObject("data");
            if (data == null) {
                L.e("data == null");
                //暂无优惠卷
                return;
            }
            String model = data.optString("model");
            if (model == null || "".equals(model)) {
                return;
            }
            goodsInfoCodeText.setText(model);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailed(int i, Response response) {

    }

    @Override
    public void onFinish(int i) {
        circleProgressBarView.hideProgressBar();
    }
}
