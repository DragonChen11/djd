package com.ksy.djd.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;

@SuppressWarnings("serial")
public class PageDataSerializable implements Serializable {
	public LoadMordDataListener listener;
	public Activity activity;
	@SuppressWarnings("rawtypes")
	public ArrayList list = new ArrayList();
	public int pageSize = 12;
	public int pageNum = 1;
	public int recordCount;
	public boolean isLoading = false;
	public boolean isLoadMoreError = false;
	
	/**
	 * 为了加载分页的更多记录，要实现LoadMordDataListener接口
	 * @param activity
	 */
	public void setActivity(Activity activity){
		this.activity = activity;
		if(activity instanceof LoadMordDataListener)
			listener = (LoadMordDataListener)activity;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized Object get(int position){
		final int listSize = list.size();
		
		if(position < listSize){
			return list.get(position);
		}
		
		if(position >= recordCount)
			return null;
		
		if(listener == null){
			return null;
		}
		
		//加载下一页
		if(recordCount > listSize){
			try {
				isLoading = true;
//				Utils.showProgressDialog(activity);
				
				PageData data = listener.loadMordData(pageSize, pageNum);
				if (data == null) {
					isLoadMoreError = true;
//					ApplicationUtils.toastMakeTextLong(R.string.APP_ERROR);
					return null;
				}
				list.addAll(data.getList());
				recordCount = data.getTotalNum();
				pageNum++;
			} catch (Exception e) {
				Log.e("PageDataSerializable", e.getMessage(), e);
				return null;
			}finally{
				isLoading = false;
//				Utils.dismissProgressDialog();
			}
		}
		
		isLoadMoreError = false;
		return list.get(position);
	}
	
	public interface LoadMordDataListener {
		/**
		 * 加载下一页
		 * @param pageSize
		 * @param pageNum
		 * @return
		 */
		public PageData loadMordData(int pageSize, int pageNum);
	}
	
	/**
	 * 加载下一页时，需要等待
	 * @param position
	 * @return
	 */
	public boolean isNeedWait(int position){
		if(isLoadMoreError){
			return false;
		}
		if(isLoading || (++position >= list.size() && recordCount > list.size())){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 删除对象
	 * @param obj
	 * @return
	 */
	public boolean removeItem(int position) {
		boolean result = list.remove(get(position));
		if (result)
			recordCount--;
		return result;
	}
	
	public int getListSize(){
		return list.size();
	}
}
