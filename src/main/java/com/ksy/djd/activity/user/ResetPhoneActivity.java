package com.ksy.djd.activity.user;


import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;

import com.ksy.djd.util.Tools;
import com.ksy.djd.util.ui.TopBar;
import com.sky.djd.R;

public class ResetPhoneActivity extends FinalActivity {
	@ViewInject(id = R.id.edit_user_phone)
	EditText edit_user_phone;
	@ViewInject(id = R.id.et_reset_checknumber)
	EditText et_reset_checknumber;
	@ViewInject(id = R.id.btn_global_sendpwd)
	Button btn_global_sendpwd;
	MyCountTimer timer;
	Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext = this;
		setContentView(R.layout.reset_phone);
		initTopBar();
		initUI();

	}

	private void initTopBar(){
		TextView textView = new TextView(mContext);
		textView.setText("绑定新号码");
		textView.setTextSize(18);
		textView.setTextColor(Color.BLACK);
		TopBar topBar = new TopBar(this, textView, null);
		topBar.bind();
	}

	private void initUI(){
		// TODO Auto-generated method stub
		btn_global_sendpwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub

				String phoneNumber = edit_user_phone.getText().toString().trim();
				if(Tools.isMobile(phoneNumber)){
					timer = new MyCountTimer(60000, 1000);
					timer.start();
					BmobSMS.requestSMSCode(mContext, phoneNumber, "djd", new RequestSMSCodeListener() {

						@Override
						public void done(Integer arg0,BmobException arg1){
							// TODO Auto-generated method stub
							if(arg1 == null){
							}
						}

					});
				}
				else{
					Toast.makeText(mContext, "所输号码不符合格式！", Toast.LENGTH_SHORT).show();
				}
			}
		});

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
