package com.ksy.djd.util;

import android.os.Bundle;
import android.view.View;

public class ModelActivity extends ActivityBase {
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    initTopBar();
		initUI();
		initData();
	}
	
	@Override
	public void onClick(View v){
		// TODO Auto-generated method stub
		super.onClick(v);
		int id=v.getId();
	}
	
	private void initTopBar(){
		
	}
	
	@Override
	protected void initUI(){
		// TODO Auto-generated method stub
		super.initUI();
	}
	
	@Override
	public void initData(){
		// TODO Auto-generated method stub
		super.initData();
	}
	
	

}
