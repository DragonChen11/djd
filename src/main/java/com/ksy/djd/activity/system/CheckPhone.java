package com.ksy.djd.activity.system;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ksy.djd.util.ActivityBase;
import com.ksy.djd.util.ui.TopBar;
import com.sky.djd.R;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.ResetPasswordByCodeListener;

public class CheckPhone extends ActivityBase {

	EditText et_global_phone;
	EditText et_global_checknumber;
	Button btn_global_sendpwd;
	MyCountTimer timer;
	String checkNumber = "";

	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.global_checkphone);
		initTopBar();
		initUI();
		initData();
	}

	private void initTopBar(){
		// TODO Auto-generated method stub
		TextView textView = new TextView(mContext);
		textView.setText("找回密码");
		textView.setTextSize(18);
		textView.setTextColor(Color.BLACK);
		TopBar topBar = new TopBar(this, textView, null);
		topBar.bind();
	}

	@Override
	protected void initUI(){
		// TODO Auto-generated method stub
		super.initUI();
		et_global_phone = (EditText) findViewById(R.id.et_global_phone);
		et_global_checknumber = (EditText) findViewById(R.id.et_global_checknumber);
		Button btn_login_login = (Button) findViewById(R.id.btn_login_login);
		btn_global_sendpwd = (Button) findViewById(R.id.btn_global_sendpwd);
		btn_login_login.setOnClickListener(this);
		btn_global_sendpwd.setOnClickListener(this);
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
			String checkNumber = et_global_checknumber.getText().toString().trim();
			BmobUser.resetPasswordBySMSCode(this, checkNumber, "1234567", new ResetPasswordByCodeListener() {

				@Override
				public void done(BmobException ex){
					// TODO Auto-generated method stub
					if(ex == null){
						Toast.makeText(mContext, "密码重置成功", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(mContext, "重置失败", Toast.LENGTH_SHORT).show();
					}
				}
			});

		}else if(id == R.id.btn_global_sendpwd){
			timer = new MyCountTimer(60000, 1000);
			timer.start();
			String phoneNumber = et_global_phone.getText().toString().trim();
			BmobSMS.requestSMSCode(mContext, phoneNumber, "djd", new RequestSMSCodeListener() {

				@Override
				public void done(Integer arg0,BmobException arg1){
					// TODO Auto-generated method stub
					if(arg1 == null){
					}
				}

			});

		}
	}

	class MyCountTimer extends CountDownTimer {

		public MyCountTimer(long millisInFuture,long countDownInterval){
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished){
			btn_global_sendpwd.setText((millisUntilFinished / 1000) + "秒后重发");
		}

		@Override
		public void onFinish(){
			btn_global_sendpwd.setText("重新发送验证码");
		}
	}
}
