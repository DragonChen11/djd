package com.ksy.djd.util;




import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.djd.R;


public class NLProgressDialog extends Dialog {
	TextView tvMsg;
	
	public NLProgressDialog(Context context){
		super(context);
	}
	
	public NLProgressDialog(Context context, int theme) {
        super(context, theme);
    }
	
	public static NLProgressDialog createDialog(Context context, boolean cancelable){
		NLProgressDialog mNLProgressDialog = new NLProgressDialog(context,R.style.NLProgressDialog);
		mNLProgressDialog.setContentView(R.layout.nlprogressdialog);
		mNLProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		mNLProgressDialog.setCancelable(cancelable);
		return mNLProgressDialog;
	}
	
	public static NLProgressDialog createDialog(Context context, String msg, boolean cancelable){
		NLProgressDialog mNLProgressDialog = createDialog(context, cancelable);
		mNLProgressDialog.setMsg(msg);
		return mNLProgressDialog;
	}
 
    public void onWindowFocusChanged(boolean hasFocus){
        try {
        	if(hasFocus){
    			ImageView imageView = (ImageView) findViewById(R.id.iv_loading);
    			AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
    			animationDrawable.start();
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
 
    public void setMsg(String msg){
    	TextView tvMsg = (TextView)findViewById(R.id.tv_msg);
    	if(tvMsg != null && !TextUtils.isEmpty(msg))
    		tvMsg.setText(msg);
    }
   
}
