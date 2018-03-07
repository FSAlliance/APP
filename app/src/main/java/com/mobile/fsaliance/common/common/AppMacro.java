package com.mobile.fsaliance.common.common;

import android.os.Environment;

public class AppMacro {
	// 数据文件路径
	public static final String APP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/FSAliance/";
	//	public static final String APP_PATH = InitApplication.getInstance().getExternalFilesDir(null).getAbsolutePath()+"/";
	// Crash日志文件夹路径
	public static final String CRASH_MESSAGE_PATH = APP_PATH + "CrashMeaasge/";
	public static final String PHOTO_PATH = APP_PATH + "photo/";
	public static final String REQUEST_IP_PORT = "http://39.107.106.248:3389";//接口http://39.107.106.248:3389

	public static final String REQUEST_GOODS_PATH = "/FSAlliance/rest";//接口
	public static final int RESPONCESUCCESS = 200; //请求接口能调通

	//具体的接口
	public static final String REQUEST_FAVORITE_LIST = "/Goods/favoriteGroup";//获取选品库列表
	public static final String REQUEST_FAVORITE_ITEMS = "/Goods/favoriteItem";//获取选品库中的数据
	public static final String REQUEST_GET_GOOD_CODE = "/Goods/code";//获取选品库中的数据
	public static final String REQUEST_SEARCH_GOOD = "/Goods/searchGoods";//搜索商品
	public static final String REQUEST_REGISTER = "/user/register";//注册
	public static final String REQUEST_LOGIN = "/user/checkSign";//登录
	public static final String REQUEST_UPDATE_LOGIN_TIME = "/user/updateLoginTime";//更新登录时间
	public static final String REQUEST_UPDATE_USER_ALIPAY = "/user/updateAlipayNum";//更新支付宝帐号
	public static final String REQUEST_UPDATE_USER_NAME = "/user/updateUserName";//更新用户名
	public static final String REQUEST_UPDATE_USER_PHOTO = "/user/updateUserPhoto";//更新头像
	public static final String REQUEST_UPDATE_USER_HEAD = "/user/updateUserHeadInfo";//更新头像
	public static final String REQUEST_UPDATE_PASSWORD = "/user/updatePassword";//更改密码
	public static final String REQUEST_GET_USER_INFO = "/user/getuserinfo";//获取用户信息
	public static final String REQUEST_FIND_MY_ORDER = "/userOrder/addOrderTaobao";//找回订单
	public static final String REQUEST_GET_ORDER = "/userOrder/getOrderByType"; //获取订单信息
	public static final String REQUEST_GET_PRESENT_RECORD = "/user/getPresentRecordList";//获取提现记录
	public static final String REQUEST_GET_INCOME_RECORD = "/user/getIncomeRecordList";//获取收入记录
	public static final String REQUEST_PRESENT = "/user/persent"; //提现
	//淘宝联盟所需常量
	public static final int PLATFORM = 2;//链接形式：1：PC，2：无线，默认：１
	public static final long ADZONEID = 210792050;//推广位id，
	//用常量来定义每个界面
	public static final int FROM_HOME = 0;//主界面
	public static final int FROM_SEARCH = 1;//搜索界面


	//其他常量
	public static final int PAGE_SIZE = 10;//每页数据条数
	public static final int FIRST_PAGE = 0;//第几页

	public static final int GET_DATA_RET_SUCCESS = 0;//获取成功
	public static final int GET_DATA_RET_NO_USERNAME = -5;//没有此用户
	public static final int GET_DATA_RET_NO_PASSWORD = -6;//没有此用户
	public static final int FIND_MY_ORDER_HAVE = -17;//该订单已被认领
	public static final int FIND_MY_ORDER_NO_HAVE = -18;//没有该订单

	//订单记录
	public static final int ORDER_ALL = 1;//所有订单
	public static final int ORDER_HAVE_PAY = 2;//订单付款
	public static final int ORDER_HAVE_SETTLEMENT = 3;//订单结算
	public static final int ORDER_HAVE_INVALID = 4;//订单失效


}
