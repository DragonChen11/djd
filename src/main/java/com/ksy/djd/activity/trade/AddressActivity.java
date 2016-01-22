package com.ksy.djd.activity.trade;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.ksy.djd.util.ListActivityBase;
import com.ksy.djd.model.trade.Address;
import com.ksy.djd.util.ui.TopBar;
import com.sky.djd.R;

public class AddressActivity extends ListActivityBase{
	
	Intent intent;
	ArrayList<Address> list=new ArrayList<Address>();
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trade_address);
		loadExtrasData();
		initUI();
		initData();
	}
	
	@Override
	protected void loadExtrasData(){
		// TODO Auto-generated method stub
		super.loadExtrasData();
		intent=getIntent();
	}
	
	@Override
	protected void initUI(){
		// TODO Auto-generated method stub
		super.initUI();
		TextView tv_add_address=(TextView) findViewById(R.id.tv_add_address);
		initRefreshView();
		onlyDownRefresh();
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,View view,int position,long id){
				// TODO Auto-generated method stub
				if(null!=intent){
					Address address=list.get(position-1);
					intent.putExtra("address", address);
					AddressActivity.this.setResult(1, intent);
					AddressActivity.this.finish();
				}
			}});
		tv_add_address.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				Intent intent=new Intent(AddressActivity.this,AddAddressActivity.class);
				startActivity(intent);
			}
		});
		
	}
	
	@Override
	protected void initTopbar(){
		// TODO Auto-generated method stub
		super.initTopbar();
		TextView textView = new TextView(mContext);
		textView.setText("收货地址管理");
		textView.setTextSize(18);
		textView.setTextColor(Color.BLACK);
		TopBar topBar = new TopBar(this, textView, null);
		topBar.bind();
	}
	
	@Override
	protected void initAdapter(){
		// TODO Auto-generated method stub
		super.initAdapter();
		adapter=new BaseAdapter() {
			
			@Override
			public View getView(int position,View convertView,ViewGroup parent){
				convertView = getLayoutInflater().inflate(R.layout.address_item, null);
				Address address=list.get(position);
					TextView tv_name=(TextView)convertView.findViewById(R.id.tv_name);
					TextView tv_phone=(TextView)convertView.findViewById(R.id.tv_phone);
					TextView tv_address=(TextView)convertView.findViewById(R.id.tv_address);
					tv_name.setText(address.getUsername());
					tv_phone.setText(address.getMobilePhoneNumber());
					tv_address.setText(address.getAddress());
				return convertView;
			}
			
			@Override
			public long getItemId(int position){
				// TODO Auto-generated method stub
				return position;
			}
			
			@Override
			public Object getItem(int position){
				// TODO Auto-generated method stub
				return list.get(position);
			}
			
			@Override
			public int getCount(){
				// TODO Auto-generated method stub
				return list.size();
			}
		};
		mPullRefreshListView.setAdapter(adapter);
	}
	
	@Override
	public void initData(){
		// TODO Auto-generated method stub
		super.initData();
	}
	
	@Override
	public void loadData(){
		// TODO Auto-generated method stub
		super.loadData();
		BmobQuery<Address> query = new BmobQuery<Address>();
		query.setLimit(20);
		//执行查询方法
        query.findObjects(mContext, new FindListener<Address>() {
			
			@Override
			public void onSuccess(List<Address> arg0){
				// TODO Auto-generated method stub
				if(null!=arg0 && arg0.size()>0){
					
					list.addAll(arg0);
					adapter.notifyDataSetChanged();
				}
			}
			
			@Override
			public void onError(int arg0,String arg1){
				// TODO Auto-generated method stub
				
			}

			
		});
	}
	
  
}
