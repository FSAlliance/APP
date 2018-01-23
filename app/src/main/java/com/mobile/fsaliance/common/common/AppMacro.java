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
	public static final String REQUEST_IP_PORT = "http://192.168.0.103:8080";//接口
	public static final String REQUEST_GOODS_FAVORITE = "/FSAlliance/rest/Goods/favoriteGroup";//接口
	public static final int RESPONCESUCCESS = 200; //请求接口能调通
}
