package com.ksy.djd.activity.system;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ksy.djd.util.ActivityBase;
import com.ksy.djd.mainpanel.MainPanelActivity;
import com.sky.djd.R;

public class LoadActivity extends ActivityBase {
	private final int SPLASH_DISPLAY_LENGHT = 2000; // 延迟三秒

	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.global_load);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run(){
			//	app.currentUser = BmobUser.getCurrentUser(LoadActivity.this);
				/*app.currentUser = BmobUser.getCurrentUser(LoadActivity.this,User.class);
				if(app.currentUser != null){
					Intent mainIntent = new Intent(LoadActivity.this, TradeMainActivity.class);
					LoadActivity.this.startActivity(mainIntent);
					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				}else{
					Intent mainIntent = new Intent(LoadActivity.this, LoginActivity.class);
					LoadActivity.this.startActivity(mainIntent);
					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				}*/
				Intent intent=new Intent(LoadActivity.this,MainPanelActivity.class);
				startActivity(intent);
				LoadActivity.this.finish();
			}
		}, SPLASH_DISPLAY_LENGHT);

	}

}
