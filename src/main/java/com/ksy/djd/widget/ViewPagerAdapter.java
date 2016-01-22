package com.ksy.djd.widget;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import android.os.Parcelable;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.ksy.djd.app.MyApplication;

public class ViewPagerAdapter {

	public ArrayList<View> listViews;
	public PagerAdapter pagerAdapter;
	public int cureentRadioButtontIndex = 0;
	public int previousRadioButtontIndex = 1;
	public ViewPager viewPager;
	public boolean isCycle = true;	// 是否左右循环

	public ViewPagerAdapter(final ArrayList<View> listViews, ViewPager viewPager) {
		this.listViews = listViews;
		this.viewPager = viewPager;
		initAdaper();
		this.viewPager.setAdapter(pagerAdapter);
	}
	
	public ViewPagerAdapter(final ArrayList<View> listViews) {
		this.listViews = listViews;
		initAdaper();
	}
	
	public void setCureentRadioButtontIndex(int index){
		previousRadioButtontIndex = cureentRadioButtontIndex;
		cureentRadioButtontIndex = index;
	}
	
	/**
	 * 2013-03-17修改，改为通过，支持4个以上
	 * @param index
	 */
	public void setViewPagesetCurrentItem(int index){
		previousRadioButtontIndex = cureentRadioButtontIndex;
		cureentRadioButtontIndex = index;
		int tempIndex = cureentRadioButtontIndex - previousRadioButtontIndex;
		if (listViews.size() > 4) {// 临时加上对4个以上的处理
			int space = 1;
			if (tempIndex < 0) {
				space = -space;
				tempIndex = -tempIndex;
			}
			while (tempIndex > 0) {
				tempIndex--;
				viewPager.setCurrentItem(viewPager.getCurrentItem() + space);
			}
		} else {
			if (listViews.size() == 4 && tempIndex == 2) {
				viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
				viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
			} else if (listViews.size() == 4 && tempIndex == -2) {
				viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
				viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
			} else if (tempIndex > 1 || tempIndex == -1)
				viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
			else if (tempIndex < -1 || tempIndex == 1)
				viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
		}
	
	}
	
	public void  setFirstViewPage(boolean isTurnLeft){
		if(!isCycle || listViews.size() == 2)
			viewPager.setCurrentItem(0);
		else if(isTurnLeft){
			viewPager.setCurrentItem(listViews.size() * 20);
		}else{
			viewPager.setCurrentItem(0);
		}
			
	}
	
	private void initAdaper(){
		pagerAdapter = new PagerAdapter() {

			@Override
			public int getCount() {
				if(!isCycle || listViews.size() < 3)
					return listViews.size();
				else
					return Integer.MAX_VALUE;
			}

			@Override
			public void startUpdate(View view) {
			}

			@Override
			public Object instantiateItem(View collection, int position) {
				try {
					((ViewPager) collection).addView(listViews.get(position % listViews.size()), 0);
				} catch (Exception e) {
				}
				return listViews.get(position % listViews.size());

			}

			@Override
			public void destroyItem(View collection, int position, Object arg2) {
				
			}

			@Override
			public void finishUpdate(View view) {
			}

			@Override
			public boolean isViewFromObject(View view, Object o) {
				return view.equals(o);
			}

			@Override
			public Parcelable saveState() {
				return null;
			}

			@Override
			public void restoreState(Parcelable prclbl, ClassLoader cl) {
			}
		};
	}

	/**
     *  设置ViewPager的滑动速度，不滑动，直接切换
     * 
     * */
	public void setViewPagerScrollSpeed() {
		try {
			Field mScroller = null;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext());
			mScroller.set(viewPager, scroller);
		}catch(Exception e){
//        	Tools.printStackTrace("ViewPagerAdapter", e);
        }
    }
	
	private class FixedSpeedScroller extends Scroller {
		private int mDuration = 0;
		private int mScreenWidth = MyApplication.getScreenWidth();

	    public FixedSpeedScroller(Context context) {
	        super(context);
	    }

	    public FixedSpeedScroller(Context context, Interpolator interpolator) {
	        super(context, interpolator);
	    }

	    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
	        super(context, interpolator);
	        //super(context, interpolator, flywheel);
	    }

	    @Override
		public void startScroll(int startX, int startY, int dx, int dy, int duration) {
			if (Math.abs(dx) >= mScreenWidth) {
				super.startScroll(startX, startY, dx, dy, mDuration);
			} else {
				super.startScroll(startX, startY, dx, dy, duration);
			}
		}

	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy) {
	    	if (Math.abs(dx) >= mScreenWidth) {
				super.startScroll(startX, startY, dx, dy, mDuration);
			} else {
				super.startScroll(startX, startY, dx, dy);
			}
	    }
	}
}
