package com.ksy.djd.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


public class ApplicationUtils {
	private static Application app;
	private static ApplicationUtils mApplicationUtils;
	public static SharedPreferences readSP;
	public static List<Activity> activities = new ArrayList<Activity>();

	private Vibrator vibrator = null;
	private Toast toast = null;
	private Toast toastLONG = null;
	private Toast NLtoast = null;

	public final static int SHOW_TOAST = 1;
	public final static int SHOW_TOAST_STRING = 2;
	public final static int RETURN_LOGIN = 3;

	public static boolean isSichuan = false; // 是否为四川应用

	public static void init(Application app){
		ApplicationUtils.app = app;
		mApplicationUtils = new ApplicationUtils();
		mApplicationUtils.toast = Toast.makeText(app, "", Toast.LENGTH_SHORT);
		mApplicationUtils.toastLONG = Toast.makeText(app, "", Toast.LENGTH_LONG);
		readSP = app.getSharedPreferences("readSP", Application.MODE_PRIVATE);
	}


	public static Application getAppInstance(){
		return app;
	}

	public static boolean isEshoreYxt2(){
		try{
			if("china.telecom.yxt".equals(app.getPackageName()))
				return true;
		}catch(Exception e){
			//e.printStackTrace("ApplicationUtils", e);
		}

		return false;
	}


	/**
	 * 震动 50 毫秒
	 */
	public static void vibrate(){
		vibrate(50);
	}

	/**
	 * 震动
	 * 
	 * @param milliseconds
	 */
	public static void vibrate(long milliseconds){
		if(mApplicationUtils.vibrator == null)
			mApplicationUtils.vibrator = (Vibrator) app.getSystemService(Service.VIBRATOR_SERVICE);
		mApplicationUtils.vibrator.vibrate(milliseconds);
	}

	/**
	 * toast信息提示 (short)
	 * 
	 * @param resId
	 * @return
	 */
	public static void toastMakeText(int resId){
		mApplicationUtils.handlerToast.sendMessage(mApplicationUtils.handlerToast.obtainMessage(1, resId));
	}

	/**
	 * toast信息提示 (short)
	 * 
	 * @param msg
	 */
	public static void toastMakeText(String msg){
		mApplicationUtils.handlerToast.sendMessage(mApplicationUtils.handlerToast.obtainMessage(1, msg));
	}

	/**
	 * toast信息提示(long)
	 * 
	 * @param resId
	 * @return
	 */
	public static void toastMakeTextLong(int resId){
		mApplicationUtils.handlerToast.sendMessage(mApplicationUtils.handlerToast.obtainMessage(2, resId));
	}

	/**
	 * 自定义 toast信息提示
	 * 
	 * @param msg
	 * @return
	 */
	public static void showNLToast(String msg){
		mApplicationUtils.handlerToast.sendMessage(mApplicationUtils.handlerToast.obtainMessage(3, msg));
	}

	/**
	 * toast信息提示(long)
	 * 
	 * @param msg
	 */
	public static void toastMakeTextLong(String msg){
		mApplicationUtils.handlerToast.sendMessage(mApplicationUtils.handlerToast.obtainMessage(2, msg));
	}

	public static void handlerMessage(int what){
		mApplicationUtils.handler.sendEmptyMessage(what);
	}

	public static void handlerMessage(int what,long delayedTime){
		mApplicationUtils.handler.sendEmptyMessageDelayed(what, delayedTime);
	}

	public static void handlerMessage(android.os.Message msg){
		mApplicationUtils.handler.sendMessage(msg);
	}



	/**
	 * 保存最后的activity，为崩溃返回作准备
	 */
	public static void saveLastActivity(Class<?> clazz){
		app.getSharedPreferences("lastActivity", Application.MODE_PRIVATE).edit().putString("lastActivity", clazz.getName()).commit();
	}

	/**
	 * 是否为4.0以上
	 */
	public static boolean isSDK4_0_More(){
		return android.os.Build.VERSION.SDK_INT > 13;
	}

	/**
	 * 解决出现的异常:Only the original thread that created a view hierarchy can touch
	 * its views<br>
	 * 所有的toast都交给UI线程显示
	 */
	public final Handler handlerToast = new Handler() {
		public void handleMessage(Message msg){
			try{
				switch(msg.what){
					case 1:
						if(msg.obj instanceof String)
							toast.setText(String.valueOf(msg.obj));
						else
							toast.setText(Integer.valueOf("" + msg.obj));
						toast.show();
						break;
					case 2:
						if(msg.obj instanceof String)
							toastLONG.setText(String.valueOf(msg.obj));
						else
							toastLONG.setText(Integer.valueOf("" + msg.obj));
						toastLONG.show();
						break;
					case 3:
//						if(msg.obj instanceof String){
//							if(NLtoast == null){
//								View toastRoot = LayoutInflater.from(app).inflate(R.layout.toast, null);
//								NLtoast = new Toast(app);
//								NLtoast.setGravity(Gravity.BOTTOM, 0, 10);
//								NLtoast.setDuration(Toast.LENGTH_LONG);
//								NLtoast.setView(toastRoot);
//							}
//							TextView message = (TextView) NLtoast.getView().findViewById(R.id.message);
//							message.setText(String.valueOf(msg.obj));
//							NLtoast.show();
//						}
						break;
					default:
						if(msg.obj instanceof String)
							toast.setText(String.valueOf(msg.obj));
						else
							toast.setText(Integer.valueOf("" + msg.obj));
						toast.show();
						break;
				}
			}catch(NumberFormatException e){
				Log.e("ApplicationUtils", e.getMessage(), e);
			}
		};
	};

	public final Handler handler = new Handler() {
		public void handleMessage(Message msg){
			switch(msg.what){
				case SHOW_TOAST:
					toastMakeTextLong(msg.arg1);
					break;
				case SHOW_TOAST_STRING:
					toastMakeTextLong(String.valueOf(msg.obj));
					break;
				case RETURN_LOGIN:
//					ApplicationUtils.returnLogin();
					break;
				case 4:
//					Tools.showSetNetworkDialog(getCurrentActivity());
					vibrate();
					break;
				default:
					break;
			}
		};
	};
}
