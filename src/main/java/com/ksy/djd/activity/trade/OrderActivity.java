package com.ksy.djd.activity.trade;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ksy.djd.util.ListActivityBase;
import com.ksy.djd.model.trade.Order;
import com.ksy.djd.util.ui.TopBar;
import com.sky.djd.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class OrderActivity extends ListActivityBase{
	ArrayList<Order> list=new ArrayList<Order>();
	boolean result;
	private long exitTime = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trade_order);
		initUI();
		initData();
	}
	
	@Override
	protected void initUI(){
		// TODO Auto-generated method stub
		super.initUI();
		initRefreshView();
		bothRefresh();
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,View view,int position,long id){
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	protected void initTopbar(){
		// TODO Auto-generated method stub
		super.initTopbar();
		final TextView textView=new TextView(mContext);
		textView.setText("订单");
		textView.setTextSize(18);
		textView.setTextColor(Color.BLACK);
		SearchView searchView=new SearchView(mContext);
		searchView.setIconifiedByDefault(true);
		searchView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
			    	textView.setVisibility(View.INVISIBLE);
			}
		});
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query){
				// TODO Auto-generated method stub
				textView.setVisibility(View.VISIBLE);
				searchOrder(query);
				return result;
			}
			
			@Override
			public boolean onQueryTextChange(String newText){
				// TODO Auto-generated method stub
				textView.setVisibility(View.VISIBLE);
				return false;
			}
			
		});
		TopBar topBar=new TopBar(this,false, textView, searchView);
		topBar.bind();
	}
	
	@Override
	protected void initAdapter(){
		// TODO Auto-generated method stub
		super.initAdapter();
		adapter=new BaseAdapter() {
			
			@Override
			public View getView(int position,View convertView,ViewGroup parent){
				convertView = getLayoutInflater().inflate(R.layout.order_item, null);
				Order order=list.get(position);
				TextView tv_order_name=(TextView)convertView.findViewById(R.id.tv_order_name);
				TextView tv_order_price=(TextView)convertView.findViewById(R.id.tv_order_price);
				tv_order_name.setText(order.getTradeName());
				tv_order_price.setText(order.getPrice()+"");
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
		
	}
	
	@Override
	public void loadData(){
		// TODO Auto-generated method stub
		super.loadData();
		loadOrderId();
	}

	private void loadOrderId(){
		// TODO Auto-generated method stub
		
	}
	
	private void searchOrder(String key){
		
		BmobQuery<Order> query=new BmobQuery<Order>();
		query.addWhereContains("tradeName", key);
		query.findObjects(mContext, new FindListener<Order>() {
			
			@Override
			public void onSuccess(List<Order> arg0){
				// TODO Auto-generated method stub
				list.clear();
				list.addAll(arg0);
				adapter.notifyDataSetChanged();
				result=true;
			}
			
			@Override
			public void onError(int arg0,String arg1){
				// TODO Auto-generated method stub
				Log.v("AAA", "queryError:  "+arg1);
				 Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
				result=false;
			}
		
		});
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event){
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
