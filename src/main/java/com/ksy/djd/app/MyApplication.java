package com.ksy.djd.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.view.WindowManager;

import com.ksy.djd.model.system.User;

//MyQQApplication
public class MyApplication extends Application {
	
//	public Login login;// 登陆的用户对象
//	public UserData userData;//用户所有数据
	
	private static MyApplication app;
	public static int screenWidth;
	public static int screenHeight;
//	public static BmobUser currentUser;
	public static User currentUser;
	public Resources res;
	public static boolean isOnLine = true;// 是否保存状态在线
	public static Activity curActivity;// 当前运行的activity
	public static Context mContext;

	@Override
	public void onCreate(){
		app = this;
		mContext = getApplicationContext();
		res = getResources();
		getScreenSize();
		super.onCreate();
		//app.currentUser=new BmobUser();
	}

	//---------------------------------------------------
	public static MyApplication getInstance(){
		return app;
	}
	
	private void getScreenSize(){
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
		screenHeight = wm.getDefaultDisplay().getHeight();
	}

	public static int getScreenWidth() {
		return screenWidth;
	}
	
	public static int getScreenHeight() {
		return screenHeight;
	}
}
