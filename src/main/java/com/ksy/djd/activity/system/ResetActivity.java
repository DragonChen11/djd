package com.ksy.djd.activity.system;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ksy.djd.util.ActivityBase;
import com.ksy.djd.activity.trade.AddressActivity;
import com.ksy.djd.activity.user.ResetPhoneActivity;
import com.ksy.djd.activity.user.UsernameActivity;
import com.ksy.djd.util.ui.TopBar;
import com.sky.djd.R;

public class ResetActivity extends ActivityBase {
	LinearLayout linear_sex;
	TextView tv_userGender;
	String gender;
	final int TAKE_PICTURE = 1;
	final int PICK_PICTURE = 2;
	ImageView img_sculpture;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.global_reset);
		initTopBar();
		initUI();
		initData();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == TAKE_PICTURE){
			if(resultCode == RESULT_OK){
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				img_sculpture.setBackgroundColor(Color.WHITE);
				img_sculpture.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				img_sculpture.setImageBitmap(bm);// 想图像显示在ImageView视图上，private ImageView img;
				File myCaptureFile = new File("sdcard/123456.jpg");
				try{
					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
					bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
					bos.flush();

					/* 结束OutputStream */
					bos.close();
				}catch(FileNotFoundException e){
					// TODO Auto-generated catch block
					e.printStackTrace();

				}catch(IOException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if(requestCode == PICK_PICTURE){
			if(null!=data){
				Uri uri = data.getData();
				img_sculpture.setBackgroundColor(Color.WHITE);
				img_sculpture.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				img_sculpture.setImageURI(uri);
			}
//			Bitmap bitmap=BitmapFactory.decodeFile(uri.getPath());
//			 img_sculpture.setImageBitmap(bitmap);
//			Drawable drawable=new BitmapDrawable(bitmap);
//			img_sculpture.setBackgroundDrawable(drawable);
		}
	}

	private void initTopBar(){
		// TODO Auto-generated method stub
		TextView textView = new TextView(mContext);
		textView.setText("设置");
		textView.setTextSize(18);
		textView.setTextColor(Color.BLACK);
		TopBar topBar = new TopBar(this, textView, null);
		topBar.bind();
	}

	@Override
	protected void initUI(){
		// TODO Auto-generated method stub
		super.initUI();
		LinearLayout linear_username = (LinearLayout) findViewById(R.id.linear_username);
		LinearLayout linear_number = (LinearLayout) findViewById(R.id.linear_number);
		linear_sex = (LinearLayout) findViewById(R.id.linear_sex);
		LinearLayout linear_address = (LinearLayout) findViewById(R.id.linear_address);
		LinearLayout linear_sculture = (LinearLayout) findViewById(R.id.linear_sculture);
		img_sculpture = (ImageView) findViewById(R.id.img_sculpture);
		linear_sculture.setOnClickListener(this);
		linear_username.setOnClickListener(this);
		linear_number.setOnClickListener(this);
		linear_sex.setOnClickListener(this);
		linear_address.setOnClickListener(this);
		TextView tv_user = (TextView) findViewById(R.id.tv_user);
		TextView tv_userPhone = (TextView) findViewById(R.id.tv_userPhone);
		tv_userGender = (TextView) findViewById(R.id.tv_userGender);
		if(null != app.currentUser){
			tv_user.setText(app.currentUser.getUsername());
			String number = app.currentUser.getMobilePhoneNumber();
			if(!number.equalsIgnoreCase("")){
				number = number.substring(0, 3) + "****" + number.substring(7, 11);
			}
			tv_userPhone.setText(number);
			tv_userGender.setText(app.currentUser.getSex());
		}

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
		if(id == R.id.linear_username){
			Intent intent = new Intent(this, UsernameActivity.class);
			startActivity(intent);
		}else if(id == R.id.linear_number){
			Intent intent = new Intent(this, ResetPhoneActivity.class);
			startActivity(intent);
		}else if(id == R.id.linear_sex){
			int width = linear_sex.getWidth();
			int height = LayoutParams.WRAP_CONTENT;
			showAreaDropDownView(v, width, height);

		}else if(id == R.id.linear_address){
			Intent intent = new Intent(this, AddressActivity.class);
			startActivityForResult(intent,5);
		}else if(id == R.id.linear_sculture){
			LayoutInflater inflater = getLayoutInflater();
			View contentView = inflater.inflate(R.layout.popup_picture_selector, null);
			View parent = inflater.inflate(R.layout.global_reset, null);
			int width = v.getWidth();
			int height = LayoutParams.WRAP_CONTENT;
			final PopupWindow popupWindow = new PopupWindow(contentView, width, height, true);
			WindowManager.LayoutParams params = this.getWindow().getAttributes();
			params.alpha = 0.7f;
			this.getWindow().setAttributes(params);

			popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL, 0, 0);
			LinearLayout linear_album = (LinearLayout) contentView.findViewById(R.id.linear_album);
			LinearLayout linear_photo = (LinearLayout) contentView.findViewById(R.id.linear_photo);
			linear_album.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v){
					// TODO Auto-generated method stub
					WindowManager.LayoutParams params = ResetActivity.this.getWindow().getAttributes();
					params.alpha = 1f;
					ResetActivity.this.getWindow().setAttributes(params);
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intent, PICK_PICTURE);
					popupWindow.dismiss();
				}
			});
			linear_photo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v){
					// TODO Auto-generated method stub
					WindowManager.LayoutParams params = ResetActivity.this.getWindow().getAttributes();
					params.alpha = 1f;
					ResetActivity.this.getWindow().setAttributes(params);
					startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), TAKE_PICTURE);
					popupWindow.dismiss();
				}
			});

		}
	}

	public void showAreaDropDownView(View v,int width,int height){
		ListView listView = new ListView(mContext);
		listView.setBackgroundColor(Color.WHITE);
		String[] type = new String[] { "男", "女" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, type);
		final PopupWindow popupWindow = new PopupWindow(listView, width, height);
		popupWindow.showAsDropDown(v);
		popupWindow.setFocusable(true);
		popupWindow.update();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent,View view,int position,long id){
				if(position == 0){
					gender = "男";
				}else if(position == 1){
					gender = "女";
				}
				tv_userGender.setText(gender);
				popupWindow.dismiss();
			}
		});
	}

}
