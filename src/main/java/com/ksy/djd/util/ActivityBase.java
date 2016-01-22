package com.ksy.djd.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.ksy.djd.app.MyApplication;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;






public class ActivityBase extends Activity implements View.OnClickListener{
	private final String TAG = "< ActivityBase >";
	static  String[] mAllowedContentTypes = new String[] { "image/jpeg","image/png ", "audio/mpeg", "audio/midi", "text/html ", "text/html", "image/gif ", "application/octet-stream", "application/msword", "application/octet-stream" };
	
	//注意:protected标识的方法不能删除super(),public标识的方法须要重写
	protected Context mContext;
//	protected ActivityTaskManager activityTaskManager;
	protected MyApplication app;
//	public TopBar topBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext=this;
		app=MyApplication.getInstance();
	}
	
	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		app.curActivity=this;
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
	}

	public void loadExtrasData() {}

	protected void initUI() {
	}
	/**
	 * 初始化头部
	 */

	
	public void initData() {}
	
	/**错误时重新加载数据*/
	public void errorReload(){}
	
	/** 显示http对话框 */
	public void openProgressDialog() {
		Utils.showProgressDialog((Activity) mContext);
	}
	
	/** 关闭http对话框 */
	protected void closeProgressDialog() {
		Utils.dismissProgressDialog();
	}

	@Override
	public void onClick(View v) {

	}

	/** http响应处理   */
	protected class ResponseHandler extends AsyncHttpResponseHandler {
		   @Override
		   public void onFailure(Throwable error, String content) {  
		       super.onFailure(error,content);
		       Log.i(TAG, "错误提示:" + content);
		       closeProgressDialog();
		       if(content!=null && content.contains("can't resolve host")){
		    	   Utils.showToast(mContext, "服务器连接失败!");
		       }
		   }
	}

	protected class FileResponseHandler extends BinaryHttpResponseHandler {
		
		public FileResponseHandler() {
			super(mAllowedContentTypes);
		}

		@Override
		public void onFailure(Throwable error,String content){
			super.onFailure(error, content);
			 Log.i(TAG, "错误提示:" + content);
			closeProgressDialog();
		}

	}
}
