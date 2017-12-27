package com.mobile.fsaliance.common.common;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


import android.app.Application;
import android.util.Log;

import com.mobile.fsaliance.R;
import com.mobile.fsaliance.common.util.LoginUtils;
import com.mobile.fsaliance.common.util.T;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.URLConnectionNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


/**
 */
public class InitApplication extends Application {
	private static InitApplication instance;
	private RequestQueue queue;
	private Object cancelObject = new Object();
	private List<String> listPlace;
	public static InitApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;
		NoHttp.initialize(this);
		Logger.setDebug(true);
		Logger.setTag("NoHttp");
		initNoHttp();

		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());

		if (!FilePathInit()) {
			Log.e(TAG, "!FilePathInit()");
		}
		queue = NoHttp.newRequestQueue();
		listPlace = new ArrayList<>();
	}


	public boolean FilePathInit() {

		try {
			// 检测是否已经创建
			File dir = new File(AppMacro.APP_PATH);
			// 检测/创建数据库的文件夹
			if (dir.exists()) {
			} else {
				dir.mkdir();
			}

			// 崩溃日志文件夹
			dir = new File(AppMacro.CRASH_MESSAGE_PATH);
			if (dir.exists()) {
			} else {
				dir.mkdir();
			}
		} catch (Exception e) {
			Log.e(TAG, "error " + e.getMessage());
			e.printStackTrace();
			return false;
		}

		return true;
	}


	private void initNoHttp() {
		Logger.setDebug(true); // 开启NoHttp调试模式。
		Logger.setTag("NoHttpSample"); // 设置NoHttp打印Log的TAG。

		NoHttp.initialize(this, new NoHttp.Config()
				.setConnectTimeout(10 * 1000) // 全局连接超时时间，单位毫秒。
				.setReadTimeout(10 * 1000) // 全局服务器响应超时时间，单位毫秒。
				.setCacheStore(
						new DBCacheStore(this) // 配置缓存到数据库。
								.setEnable(false)// true启用缓存，fasle禁用缓存。
				)
				.setCookieStore(
						new DBCookieStore(this)
								.setEnable(false) // true启用自动维护Cookie，fasle禁用自动维护Cookie。
				)
				.setNetworkExecutor(new URLConnectionNetworkExecutor()) // 使用HttpURLConnection做网络层。
		);
	}



}
