package com.ksy.djd.activity.trade;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.listener.SaveListener;

import com.ksy.djd.util.ActivityBase;
import com.ksy.djd.model.trade.Address;
import com.ksy.djd.util.ui.TopBar;
import com.sky.djd.R;

public class AddAddressActivity extends ActivityBase {

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trade_add_address);
		initTopBar();
		initUI();
		initData();
	}

	private void initTopBar(){
		// TODO Auto-generated method stub
		TextView textView=new TextView(mContext);
		textView.setText("添加地址");
		textView.setTextSize(18);
		textView.setTextColor(Color.BLACK);
		TopBar topBar=new TopBar(this, textView, null);
		topBar.bind();
	}

	@Override
	public void onClick(View v){
		// TODO Auto-generated method stub
		super.onClick(v);
		int id = v.getId();
		if(id == R.id.btn_login_login){
			saveAddress();
		}
	}
	
	

	@Override
	protected void initUI(){
		// TODO Auto-generated method stub
		super.initUI();
		Button btn_login_login=(Button) findViewById(R.id.btn_login_login);
		btn_login_login.setOnClickListener(this);
	}

	@Override
	public void initData(){
		// TODO Auto-generated method stub
		super.initData();

	}

	public void saveAddress(){
		EditText edit_address = (EditText) findViewById(R.id.edit_address);
		EditText edit_name = (EditText) findViewById(R.id.edit_name);
		EditText edit_phone = (EditText) findViewById(R.id.edit_phone);
		String address=edit_address.getText().toString().trim();
		String name=edit_name.getText().toString().trim();
		String phone=edit_phone.getText().toString().trim();
		Address add=new Address();
		add.setAddress(address);
		add.setMobilePhoneNumber(phone);
		add.setUsername(name);
		add.save(mContext, new SaveListener() {
			
			@Override
			public void onSuccess(){
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "添加地址成功", Toast.LENGTH_SHORT).show();
				finish();
			}
			
			@Override
			public void onFailure(int arg0,String arg1){
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "添加地址失败", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}
}
