package com.ksy.djd.activity.system;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;

import com.ksy.djd.util.ActivityBase;
import com.sky.djd.R;

public class PersonalActivity extends ActivityBase {
	private long exitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.global_personal);
		initUI();
		initData();
	}

	@Override
	public void onClick(View v){
		// TODO Auto-generated method stub
		super.onClick(v);
		int id = v.getId();
		if(id == R.id.tv_reset){
			Intent intent = new Intent(PersonalActivity.this, ResetActivity.class);
			startActivity(intent);
		}else if(id == R.id.btn_login_login){
			BmobUser.logOut(this);
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	protected void initUI(){
		// TODO Auto-generated method stub
		super.initUI();
		TextView tv_reset = (TextView) findViewById(R.id.tv_reset);
		tv_reset.setOnClickListener(this);
		Button btn_login_login = (Button) findViewById(R.id.btn_login_login);
		btn_login_login.setOnClickListener(this);
	}

	@Override
	public void initData(){
		// TODO Auto-generated method stub
		super.initData();
	}

	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event){
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if((System.currentTimeMillis() - exitTime) > 2000){
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			}else{
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
