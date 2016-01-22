package com.ksy.djd.util.ui;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TabHost;

public class MyOnPageChangeListener implements OnPageChangeListener {
	private TabHost mTabHost;

	public MyOnPageChangeListener(TabHost mTabHost){
		super();
		this.mTabHost = mTabHost;
	}

	@Override
	public void onPageSelected(int arg0){
		mTabHost.setCurrentTab(arg0);
		switch(arg0){
			case 0:
				mTabHost.setCurrentTab(0);
				break;
			case 1:
				mTabHost.setCurrentTab(1);
				break;
			case 2:
				mTabHost.setCurrentTab(2);
				break;
			case 3:
				mTabHost.setCurrentTab(3);
				break;
		}
	}

	@Override
	public void onPageScrolled(int arg0,float arg1,int arg2){
	}

	@Override
	public void onPageScrollStateChanged(int arg0){
	}

}
