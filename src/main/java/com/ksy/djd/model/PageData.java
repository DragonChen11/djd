package com.ksy.djd.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 分页数据
 */
public class PageData<T extends ModelBase> implements Serializable {
	private static final long serialVersionUID = 1L;

	private ArrayList<T> list = new ArrayList<T>();
	private int pageNum = 0;
	private int pageSize = 0;
	private int totalNum = 0;

	public PageData() {
	}

	/**
	 * @param list
	 * @param totalNum
	 * @param pageNum
	 * @param pageSize
	 */
	public PageData(ArrayList<T> list, int totalNum, int pageNum, int pageSize) {
		this.list = list;
		this.totalNum = totalNum;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	public ArrayList<T> getList() {
		return list;
	}

	public void setList(ArrayList<T> list) {
		this.list = list;
	}

	public int getTotalNum() {
		if (totalNum == 0 && list != null)
			return list.size();
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
