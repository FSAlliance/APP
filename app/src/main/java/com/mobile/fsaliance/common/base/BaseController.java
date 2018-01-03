package com.mobile.fsaliance.common.base;




import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public abstract class BaseController extends AppCompatActivity {
	
	public int bussFd = -1;
	private FrameLayout frameLayout;
	private static boolean isActive = false; 

	/**
	 * 初始化界面xml 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getBundleData();

		onCreateFunc(savedInstanceState);
	}
	
	/**
	 * 获取参数
	 */
	protected abstract void getBundleData();
	
	/**
	 * 获取参数
	 */
	protected abstract void onCreateFunc(Bundle savedInstanceState);
	
	/**
	 * 界面销毁,需要调用stopTask
	 */
	protected void onDestroy() {
		super.onDestroy();

	}
	

    

	@Override
    protected void onStop() {
        super.onStop();
    }
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	  
	

    @Override
    protected void onResume() {
        super.onResume();
    }



}
