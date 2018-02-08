package com.mobile.fsaliance.common.common;

import android.os.Environment;

public class AppMacro {
	// 数据文件路径
	public static final String APP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/FSAliance/";
	//	public static final String APP_PATH = InitApplication.getInstance().getExternalFilesDir(null).getAbsolutePath()+"/";
	// Crash日志文件夹路径
	public static final String CRASH_MESSAGE_PATH = APP_PATH + "CrashMeaasge/";
	public static final String PHOTO_PATH = APP_PATH + "photo/";
	public static final String REQUEST_URL = "http://221.238.227.82:10086/rest";//接口
	public static final String REQUEST_IP_PORT = "http://39.107.106.248:3389";//接口http://39.107.106.248:3389

	public static final String REQUEST_GOODS_PATH = "/FSAlliance/rest";//接口
	public static final int RESPONCESUCCESS = 200; //请求接口能调通

	//具体的接口
	public static final String REQUEST_FAVORITE_LIST = "/Goods/favoriteGroup";//获取选品库列表
	public static final String REQUEST_FAVORITE_ITEMS = "/Goods/favoriteItem";//获取选品库中的数据
	public static final String REQUEST_GET_GOOD_CODE = "/Goods/code";//获取选品库中的数据
	public static final String REQUEST_SEARCH_GOOD = "/Goods/searchGoods";//搜索商品
	public static final String REQUEST_LOGIN = "/user/checkSign";//登录

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

}
