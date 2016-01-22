package com.ksy.djd.activity.system;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.ksy.djd.util.ActivityBase;
import com.ksy.djd.util.ui.TopBar;
import com.sky.djd.R;

public class RegisterActivity extends ActivityBase {

	EditText et_register_uid;
	EditText et_register_pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		initTopBar();
		initUI();
		initData();
	}

	private void initTopBar(){
		// TODO Auto-generated method stub
		TextView textView = new TextView(mContext);
		textView.setText("注册页面");
		textView.setTextSize(18);
		textView.setTextColor(Color.BLACK);
		TopBar topBar = new TopBar(this, textView, null);
		topBar.bind();
	}

	@Override
	protected void initUI(){
		// TODO Auto-generated method stub
		super.initUI();
		et_register_uid = (EditText) findViewById(R.id.et_register_uid);
		et_register_pwd = (EditText) findViewById(R.id.et_register_pwd);
		Button btn_login_login = (Button) findViewById(R.id.btn_login_login);
		btn_login_login.setOnClickListener(this);
	}

	@Override
	public void initData(){
		// TODO Auto-generated method stub
		super.initData();
	}

	@Override
	public void onClick(View v){
		// TODO Auto-generated method stub
		super.onClick(v);
		int id = v.getId();
		if(id == R.id.btn_login_login){
			register();
		}
	}

	private void register(){

		String username = et_register_uid.getText().toString().trim();
		String password = et_register_pwd.getText().toString().trim();
		if(!username.equalsIgnoreCase("") && !password.equalsIgnoreCase(""))
		{
			openProgressDialog();
			BmobUser bu = new BmobUser();
			bu.setUsername(username);
			bu.setPassword(password);
			// 注意：不能用save方法进行注册
			bu.signUp(mContext, new SaveListener() {
				@Override
				public void onSuccess(){
					closeProgressDialog();
					Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onFailure(int code,String msg){
                    closeProgressDialog();
					Toast.makeText(mContext, "注册失败", Toast.LENGTH_SHORT).show();
				}
			});
		}

	}
}
