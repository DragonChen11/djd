package com.ksy.djd.activity.user;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ksy.djd.util.ActivityBase;
import com.ksy.djd.util.ui.TopBar;
import com.sky.djd.R;

public class UsernameActivity extends ActivityBase{
	
	EditText edit_username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reset_username);
	    initTopBar();
		initUI();
		initData();
	}
	
	@Override
	public void onClick(View v){
		// TODO Auto-generated method stub
		super.onClick(v);
		int id=v.getId();
		if(id==R.id.btn_save){
			
		}
	}
	
	private void initTopBar(){
		TextView textView = new TextView(mContext);
		textView.setText("我的昵称");
		textView.setTextSize(18);
		textView.setTextColor(Color.BLACK);
		TopBar topBar = new TopBar(this, textView, null);
		topBar.bind();
	}
	
	@Override
	protected void initUI(){
		// TODO Auto-generated method stub
		super.initUI();
		edit_username=(EditText) findViewById(R.id.edit_username);
		edit_username.setOnClickListener(this);
	}
	
	@Override
	public void initData(){
		// TODO Auto-generated method stub
		super.initData();
	}
}
