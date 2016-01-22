package com.ksy.djd.activity.system;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.pay.tool.BmobPay;
import com.ksy.djd.util.ActivityBase;
import com.ksy.djd.config.AppConfig;
import com.ksy.djd.model.system.User;
import com.ksy.djd.util.Tools;
import com.sky.djd.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends ActivityBase {
	EditText et_login_uid;
	EditText et_login_pwd;
	CheckBox cbShowPwd;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Bmob.initialize(this, AppConfig.ApplicationID);
		BmobPay.init(mContext, AppConfig.ApplicationID);
		initUI();
		initData();
	}

	@Override
	protected void initUI(){
		// TODO Auto-generated method stub
		super.initUI();
		et_login_uid = (EditText) findViewById(R.id.et_login_uid);
		et_login_pwd = (EditText) findViewById(R.id.et_login_pwd);
		Button btn_login_login = (Button) findViewById(R.id.btn_login_login);
		btn_login_login.setOnClickListener(this);
		TextView tv_login = (TextView) findViewById(R.id.tv_login);
		TextView tv_forget = (TextView) findViewById(R.id.tv_forget);
		tv_login.setOnClickListener(this);
		tv_forget.setOnClickListener(this);
		cbShowPwd = (CheckBox) findViewById(R.id.cb_login_showpwd);
		cbShowPwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton paramCompoundButton,boolean paramBoolean){
				if(paramBoolean){
					et_login_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
					Tools.setSelection(et_login_pwd);
				}else{
					et_login_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
					Tools.setSelection(et_login_pwd);
				}
			}
		});
		
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
			login();
		}else if(id == R.id.tv_login){
			Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
		}else if(id==R.id.tv_forget){
			Intent intent = new Intent(LoginActivity.this, CheckPhone.class);
			startActivity(intent);
		}

	}

	private void login(){
		// TODO Auto-generated method stub
		
		//String userName = et_login_uid.getText().toString().trim();
		//String password = et_login_pwd.getText().toString().trim();
		String userName="15913351650";
		String password="zhangsan";
		
		openProgressDialog();
		final BmobUser bu2 = new BmobUser();
		if(Tools.isMobile(userName)){
			BmobUser.loginByAccount(mContext, userName, password, new LogInListener<BmobUser>() {

				
				
				@Override
				public void done(BmobUser arg0,BmobException arg1){
					// TODO Auto-generated method stub
					closeProgressDialog();
					 if(arg0!=null){
						 app.currentUser=BmobUser.getCurrentUser(mContext, User.class);
						 Toast.makeText(mContext, "登陆成功", Toast.LENGTH_SHORT).show();
					//	 Intent intent=new Intent(LoginActivity.this,TradeMainActivity.class);
					//	 startActivity(intent);
				        }

				}
				
			});

		}else{
			bu2.setUsername(userName);
			bu2.setPassword(password);
			openProgressDialog();
			bu2.login(this, new SaveListener() {

				@Override
				public void onSuccess(){
					// TODO Auto-generated method stub
					closeProgressDialog();
					app.currentUser=BmobUser.getCurrentUser(mContext, User.class);
					Toast.makeText(mContext, "登陆成功", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFailure(int code,String msg){
					// TODO Auto-generated method stub
					closeProgressDialog();
					Toast.makeText(mContext, "登陆失败", Toast.LENGTH_SHORT).show();
				}
			});
		}

	}

	
}
