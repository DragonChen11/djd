package com.ksy.djd.util;

import com.ksy.djd.app.MyApplication;
import com.ksy.djd.util.NLProgressDialog;
import com.ksy.djd.util.Utils;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class TabActivityBase extends TabActivity implements OnClickListener,OnTabChangeListener{
	protected Context mContext = null;
	protected MyApplication app = null;
//	protected ActivityTaskManager activityTaskManager;
	protected LayoutInflater inflater=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext=this;
		app=MyApplication.getInstance();
		app= (MyApplication) this.getApplication();
//		activityTaskManager=ActivityTaskManager.getInstance();
//		activityTaskManager.putActivity(((Object)this).getClass().getSimpleName(), this);
		inflater=getLayoutInflater();
		
	}

	@Override
	public void onTabChanged(String tabId){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v){
		// TODO Auto-generated method stub
		
	}
	public void openProgressDialog() {
		Utils.showProgressDialog((Activity) mContext);
	}
	
	/** 关闭http对话框 */
	protected void closeProgressDialog() {
		Utils.dismissProgressDialog();
	}
}
