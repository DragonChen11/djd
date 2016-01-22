package com.ksy.djd.activity.trade;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.OrderQueryListener;
import com.bmob.pay.tool.PayListener;
import com.ksy.djd.util.ActivityBase;
import com.ksy.djd.model.trade.Address;
import com.ksy.djd.model.trade.Order;
import com.ksy.djd.util.ui.TopBar;
import com.sky.djd.R;


public class PayActivity extends ActivityBase {
	private String tradeName;
	private Number amount;
	private double price;
	private double total;
	private double pay;
	int i=1;
	TextView tv_trade_address;
	BmobPay bmobPay=new BmobPay(this);

	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trade_pay);
		loadExtrasData();
	    initTopBar();
		initUI();
		initData();
//		LinearLayout layout = new LinearLayout(mContext);
//		Button button = new Button(mContext);
//		button.setText("支付");
//		layout.addView(button);
//		setContentView(layout);
//		button.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v){
//				// TODO Auto-generated method stub
//				payByAli();
//			}
//		});
	}
	
	@Override
	public void onClick(View v){
		// TODO Auto-generated method stub
		super.onClick(v);
		int id=v.getId();
	}
	
	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1 && resultCode==1){
		    Address address=(Address) data.getSerializableExtra("address");
		    String name=address.getAddress();
		    tv_trade_address.setText(name);
		}
	}
	
	private void initTopBar(){
		TextView textView = new TextView(mContext);
		textView.setText("提交订单");
		textView.setTextSize(18);
		textView.setTextColor(Color.BLACK);
		TopBar topBar = new TopBar(this, textView, null);
		topBar.bind();
	}
	
	@Override
	protected void initUI(){
		// TODO Auto-generated method stub
		super.initUI();
		Button trade_number_minus=(Button) findViewById(R.id.trade_number_minus);
		Button trade_number_add=(Button) findViewById(R.id.trade_number_add);
		final EditText edit_number=(EditText) findViewById(R.id.edit_number);
		final TextView tv_trade_total=(TextView) findViewById(R.id.tv_trade_total);
		TextView tv_trade_total_pay=(TextView) findViewById(R.id.tv_trade_total_pay);
		TextView tv_user_number=(TextView) findViewById(R.id.tv_user_number);
		tv_trade_address=(TextView) findViewById(R.id.tv_trade_address);
		RelativeLayout rlayout_trade_coupon=(RelativeLayout) findViewById(R.id.rlayout_trade_coupon);
		RelativeLayout rlayout_address=(RelativeLayout) findViewById(R.id.rlayout_address);
		edit_number.setText(i+"");
		tv_trade_total.setText(total+"");
		tv_user_number.setText(app.currentUser.getMobilePhoneNumber());
		trade_number_minus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				if(i>1){
					i--;
		            total=i*price;
		            edit_number.setText(i+"");
		            tv_trade_total.setText(total+"");
				}
			}
		});
		trade_number_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				i++;
			    total=i*price;
			    edit_number.setText(i+"");
			    tv_trade_total.setText(total+"");
			}
		});
		
		rlayout_trade_coupon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
			    	
			}
		});
		
		rlayout_address.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				Intent intent=new Intent(PayActivity.this,AddressActivity.class);
				startActivityForResult(intent, 1);
			}
		});
	}
	
	@Override
	public void initData(){
		// TODO Auto-generated method stub
		super.initData();
	}
	

	@Override
	public void loadExtrasData(){
		// TODO Auto-generated method stub
		super.loadExtrasData();
		Intent intent = getIntent();
		tradeName = intent.getStringExtra("tradeName");
		price = intent.getDoubleExtra("price", 0);
		total=price;
	}

	void payByAli(){

		new BmobPay(PayActivity.this).pay(0.02, "衣服", "漂亮", new PayListener() {

			// 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
			@Override
			public void unknow(){
				Toast.makeText(mContext, "支付结果未知,请稍后手动查询", Toast.LENGTH_SHORT).show();

			}

			// 支付成功,如果金额较大请手动查询确认
			@Override
			public void succeed(){
				Toast.makeText(PayActivity.this, "支付成功!", Toast.LENGTH_SHORT).show();

			}

			// 无论成功与否,返回订单号
			@Override
			public void orderId(String orderId){
				// 此处应该保存订单号,比如保存进数据库等,以便以后查询
				queryOrder(orderId);
				saveOrderId(orderId);
			}

			// 支付失败,原因可能是用户中断支付操作,也可能是网络原因
			@Override
			public void fail(int code,String reason){
				Toast.makeText(PayActivity.this, "支付中断!", Toast.LENGTH_SHORT).show();

			}
		});
	}

	private void queryOrder(String orderId){
		bmobPay.query(orderId, new OrderQueryListener() {

			@Override
			public void succeed(String status) {
				
			}
			@Override
			public void fail(int code, String reason) {
				
			}
		});
	}

	private void saveOrderId(String orderId){
		Order order = new Order();
		// 注意：不能调用gameScore.setObjectId("")方法
		order.setOrderId(orderId);
		order.setTradeName(tradeName);
		order.setPrice(price);
		order.save(mContext, new SaveListener() {

			@Override
			public void onSuccess(){
				// TODO Auto-generated method stub
				Toast.makeText(PayActivity.this, "添加订单成功!", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(int code,String arg0){
				// TODO Auto-generated method stub
				// 添加失败
				Toast.makeText(PayActivity.this, "添加订单失败!", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
