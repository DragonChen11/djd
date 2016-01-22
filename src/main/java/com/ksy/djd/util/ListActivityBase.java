package com.ksy.djd.util;



import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.BaseAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ksy.djd.app.MyApplication;
import com.ksy.djd.config.AppConfig;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sky.djd.R;

public class ListActivityBase extends ListActivity{

	// 公用变量
	protected Context mContext;
	protected BaseAdapter adapter;
	protected LayoutInflater inflater = null;
	protected int pageNum = 1;
	protected int pageSize=20;
	protected PullToRefreshListView mPullRefreshListView;
	protected final int INIT_DATA_ACTION = 1;
	protected final int SUMMIT_DATA_ACTION = 2;
	protected MyApplication app=null;
    
//	protected ActivityTaskManager activityTaskManager;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext=this;
//		activityTaskManager=ActivityTaskManager.getInstance();
//		activityTaskManager.putActivity(this.getClass().getSimpleName(), this);
		app=MyApplication.getInstance();
		app=(MyApplication) this.getApplication();
	}
	
	/**
	 * 加载数据 Intent传递过来的 Extras数据
	 */
	protected void loadExtrasData(){

	}
	
	protected void initAdapter(){
	};

	protected void initRefreshView(){
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView){
				refreshData();		
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView){
				pageNum += 1;
				loadData();
			}
		});
	}

	protected void onlyDownRefresh(){
		mPullRefreshListView.setMode(Mode.PULL_FROM_START);
	}

	protected void onlyUpRefresh(){
		mPullRefreshListView.setMode(Mode.PULL_FROM_END);
	}

	protected void bothRefresh(){
		mPullRefreshListView.setMode(Mode.BOTH);
	}

	protected void disableRefresh(){
		mPullRefreshListView.setMode(Mode.DISABLED);
	}

	protected void onRefreshComplete(){
		mPullRefreshListView.onRefreshComplete();
	}

	public void refreshData(){
		
	}

	protected void initUI(){
		initTopbar();
		initListView();
		initAdapter();
	}

	protected void initListView(){
		// TODO Auto-generated method stub
			mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
	}

	protected void initTopbar(){
		// TODO Auto-generated method stub
		
	}

	protected void initData(){
		loadData();
	};

	/** 加载listview数据 */
	public void loadData(){
	}


	/**
	 * 显示http对话框
	 */
	public void openProgressDialog(){
		Utils.showProgressDialog((Activity) mContext);
	}

	/**
	 * 关闭http对话框
	 */
	protected void closeProgressDialog(){
		Utils.dismissProgressDialog();
	}

	protected int retry = 0;

	/**
	 * http响应处理
	 */
	protected class ResponseHandler extends AsyncHttpResponseHandler {
		@Override
		public void onSuccess(String response){
			AppConfig.retry = 0;
		}

		@Override
		public void onFailure(Throwable error,String content){
			super.onFailure(error, content);
			AppConfig.retry++;
			Log.i("AAA", "操作失败：content:" + content);
			if(mPullRefreshListView != null){
				onRefreshComplete();
			}
			closeProgressDialog();
			if(AppConfig.retry < 3){
				initData();
			}

		}
	}


}
