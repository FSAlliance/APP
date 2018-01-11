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

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.base.BaseController;
import com.mobile.fsaliance.common.util.L;
import com.mobile.fsaliance.common.util.T;

public class MfrmGoodsInfoController extends BaseController implements View.OnClickListener {

    private TextView oneKeyCopyText, goodsInfoCodeText, toTaoBaoText;
    private ImageView goodsInfoImg;
    private TextView titleTxt;
    private LinearLayout backLL;


    @Override
    protected void getBundleData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //TODO 具体参数
    }

    @Override
    protected void onCreateFunc(Bundle savedInstanceState) {
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_info_controller);

        //初始化界面
        initView();

        //添加监听事件
        addListener();

        //初始化数据
        initValues();

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
    }

    /**
     * @author yuanxueyuan
     * @Title: initValues
     * @Description: 初始化数据
     * @date 2017/12/25 21:07
     */
    private void initValues() {
        goodsInfoImg.setImageResource(R.drawable.goods_price_discount);
        goodsInfoCodeText.setText("￥1Jri0PY0hT6￥");
    }

     /**
     * @author  yuanxueyuan
     * @Title:  getGoodCode
     * @Description: 获取商品优惠卷的
     * @date 2017/12/25 22:15
     */
    private String getGoodCode(){
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


    public static boolean checkPackage(Context context ,String packageName){
        if (packageName == null || "".equals(packageName))
            return false;
        try{
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        }catch (PackageManager.NameNotFoundException e){
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
                    String url = "taobao://shop.m.taobao.com/shop/shop_index.htm?shop_id=131259851&spm=a230r.7195193.1997079397.8.Pp3ZMM&point";
                    Uri uri = Uri.parse(url);
                    intent.setData(uri);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
}
