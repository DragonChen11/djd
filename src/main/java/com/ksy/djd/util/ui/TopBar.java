package com.ksy.djd.util.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.sky.djd.R;


public class TopBar {

	private Activity activity;
	private ImageView img_back;
	private LinearLayout linearlayout_left,linearlayout_right,linearLayout_center;
	private String[] contents;
	private boolean hasLeftView = true;// 是否显示左边的view
	private  View leftView;
	private View rightView;
	private View centerView;

	private Context mContext;

	public TopBar(Activity activity){
		this.activity = activity;
	}

	public TopBar(Activity activity,View centerView,View rightView){
		this.activity = activity;
		this.centerView = centerView;
		this.rightView = rightView;
	}

	public TopBar(Activity activity,Boolean hasLeftView,View leftView,View centerView,View rightView){
		this.hasLeftView = hasLeftView;
		this.activity = activity;
		this.leftView=leftView;
		this.centerView = centerView;
		this.rightView = rightView;
	}

	public TopBar(Activity activity,Boolean hasLeftView,View centerView,View rightView){
		this.hasLeftView = hasLeftView;
		this.activity = activity;
		this.centerView = centerView;
		this.rightView = rightView;
	}

	/** 绑定数据控件 */
	public void bind(){
		if(hasLeftView){
			initLeftView();
		}else{
			initLeftView1(leftView);
		}
		initCenterView(centerView);
		initRightView(rightView);
	}

	public void bind(View centerView,View rightView){
		initCenterView(centerView);
		initRightView(rightView);
	}


	private void initLeftView(){


		img_back = (ImageView) activity.findViewById(R.id.img_back);
		img_back.setVisibility(View.VISIBLE);
		img_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView){
				// 退回到上一个界面
				LeftViewClicklistener();
				activity.finish();
			}
		});

	}

	private void initLeftView1(View leftView){
		if(leftView == null){
			return;
		}
		img_back = (ImageView) activity.findViewById(R.id.img_back);
		img_back.setVisibility(View.GONE);
		linearlayout_left = (LinearLayout) activity.findViewById(R.id.global_topbar_linearlayout_left);
		linearlayout_left.addView(leftView);
	}

	private void initRightView(View rightView){
		if(rightView == null){
			return;
		}
		linearlayout_right = (LinearLayout) activity.findViewById(R.id.global_topbar_linearlayout_right);
		linearlayout_right.addView(rightView);
	}

	private void initCenterView(View centerView){
		if(centerView == null){
			return;
		}
		linearLayout_center = (LinearLayout) activity.findViewById(R.id.global_topbar_linearlayout_center);
		linearLayout_center.addView(centerView);
	}

	/** 设置显示的内容 */
	public void setContent(String...contents){
		this.contents = contents;
	}

	private void initContentView(String content){
		LinearLayout linearLayout = new LinearLayout(activity);
		linearLayout.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 0, 0, 0);
		ImageView img = new ImageView(activity);
		img.setBackgroundResource(R.drawable.global_topbar_divider);
		TextView txt = new TextView(activity);
		txt.setText(content);
		txt.setTextColor(Color.parseColor("#ff444444"));
		txt.setGravity(Gravity.CENTER);
		txt.setTextSize(16);
		linearLayout.addView(img);
		linearLayout.addView(txt);
		linearlayout_left.addView(linearLayout, params);
	}

	private void LeftViewClicklistener(){
		if(sBarLeftViewClicklistener != null){
			sBarLeftViewClicklistener.click();
		}
	}

	private OnTobBarLeftViewClicklistener sBarLeftViewClicklistener;

	public void SetOnTobBarLeftViewClicklistener(OnTobBarLeftViewClicklistener sBarLeftViewClicklistener){
		this.sBarLeftViewClicklistener = sBarLeftViewClicklistener;
	}

	public interface OnTobBarLeftViewClicklistener {
		public void click();
	}

}
