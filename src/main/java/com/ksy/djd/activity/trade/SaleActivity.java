package com.ksy.djd.activity.trade;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;


import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.bmob.BmobProFile;
import com.ksy.djd.util.ListActivityBase;
import com.ksy.djd.config.AppConfig;
import com.ksy.djd.mainpanel.MenuItem;
import com.ksy.djd.model.trade.Goods;
import com.ksy.djd.util.ui.TopBar;
import com.sky.djd.R;

public class SaleActivity extends ListActivityBase implements OnItemClickListener {
	List<Goods> list = new ArrayList<Goods>();
	BaseAdapter adapter = null;
	private ViewHolder holder = new ViewHolder();
	private List<MenuItem> menus = new ArrayList<MenuItem>();
	String type = "";
	int point = 0,point2 = 0;
	Bitmap bitmap = null;
	RequestQueue mQueue = null;
	ImageLoader mImageLoader = null;
	private long exitTime = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initImageLoader();
		setContentView(R.layout.trade_sale);
		initUI();
		initData();

	}
	
	@SuppressLint("NewApi")
	private void initImageLoader(){
		
		mQueue = Volley.newRequestQueue(mContext);
		final LruCache<String, Bitmap> mImageCache = new LruCache<String, Bitmap>(20);
		ImageCache imageCache = new ImageCache() {
			@SuppressLint("NewApi")
			@Override
			public void putBitmap(String key,Bitmap value){
				mImageCache.put(key, value);
			}
			
			@Override
			public Bitmap getBitmap(String key){
				return mImageCache.get(key);
			}
		};
		mImageLoader = new ImageLoader(mQueue, imageCache);
	}

	@Override
	protected void initUI(){
		// TODO Auto-generated method stub
		super.initUI();
		initRefreshView();
		holder.gridView = (GridView) findViewById(R.id.gridview);
		initGridViewData();
		onlyUpRefresh();
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,View view,int position,long id){
				// TODO Auto-generated method stub
				Goods goods = list.get(position - 1);
				Intent intent = new Intent(SaleActivity.this, PayActivity.class);
				intent.putExtra("tradeName", goods.getTableName());
				intent.putExtra("price", goods.getPrice());
				startActivity(intent);
			}
		});

	}

	@Override
	protected void initTopbar(){
		// TODO Auto-generated method stub
		super.initTopbar();
		TextView textView = new TextView(mContext);
		textView.setText("宝贝");
		textView.setTextSize(22);
		textView.setTextColor(Color.BLACK);
		TopBar topBar = new TopBar(this,false, textView, null);
		topBar.bind();
	}

	private void initGridViewData(){
		menus.clear();
		menus.addAll(getMenuItems());
		BaseAdapter adapter = new BaseAdapter() {

			@Override
			public View getView(int position,View convertView,ViewGroup parent){
				// TODO Auto-generated method stub
				convertView = getLayoutInflater().inflate(R.layout.main_griditem_home, null);
				View view = convertView.findViewById(R.id.item);
				// TextView txt_notice = (TextView)
				// convertView.findViewById(R.id.txt_notice);
				if((position + 1) <= menus.size()){
					MenuItem menuItem = menus.get(position);
					int Id = getResources().getIdentifier(menuItem.getIcon(), "drawable", mContext.getPackageName());// 这个是图片id
					if(Id == 0){
						view.setBackgroundResource(R.color.white);
					}else{
						view.setBackgroundDrawable(getResources().getDrawable(Id));
					}
					TextView txt_notice = (TextView) convertView.findViewById(R.id.txt_notice);
					txt_notice.setText(menuItem.getName());
					txt_notice.setVisibility(0);
					// updateNum(menuItem, txt_notice);
				}else{
					view.setBackgroundResource(R.drawable.kongbai);
				}
				AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, (int) (app.getScreenHeight() * 0.15));
				convertView.setLayoutParams(layoutParams);
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
				return null;
			}

			@Override
			public int getCount(){
				// TODO Auto-generated method stub
				return menus.size();
			}
		};
		holder.gridView.setAdapter(adapter);
		holder.gridView.setOnItemClickListener(this);
	}

	public List<MenuItem> getMenuItems(){
		final String class_snakes = "com.ksy.djd.activity.trade.SnakesActivity";
		final String class_bread = "com.ksy.djd.activity.trade.BreadActivity";
		final String class_drinks = "com.ksy.djd.activity.trade.DrinksActivity";
		final String class_daily_necessities = "com.ksy.djd.activity.trade.DailyNecessitiesActivity";
		List<MenuItem> list = new ArrayList<MenuItem>();
		list.add(new MenuItem("snacks_selector_btn", "零食", class_snakes));
		list.add(new MenuItem("bread_selector_btn", "面包", class_bread));
		list.add(new MenuItem("drinks_selector_btn", "饮料", class_drinks));
		list.add(new MenuItem("daily_necessities_selector_main", "日常用品", class_daily_necessities));
		return list;
	}

	@Override
	protected void initData(){
		// TODO Auto-generated method stub
		super.initData();
	}

	@Override
	protected void initAdapter(){
		// TODO Auto-generated method stub
		super.initAdapter();
		adapter = new BaseAdapter() {

			@Override
			public View getView(int position,View convertView,ViewGroup parent){
				// TODO Auto-generated method stub
				Goods goods = list.get(position);
				LayoutInflater inflater = getLayoutInflater();
				View view = inflater.inflate(R.layout.trade_listitem, null);
				TextView txt_goods_name = (TextView) view.findViewById(R.id.txt_goods_name);
				BmobFile file = new BmobFile();
				file = goods.getPicture();

				if(file != null){
					String fileName = file.getFilename();
					String url = file.getFileUrl(mContext);
					String URL = BmobProFile.getInstance(mContext).signURL(fileName, url, AppConfig.AccessKey, 0, null);
					ImageView imageView = (ImageView) view.findViewById(R.id.img_picture);
					ImageListener listener = ImageLoader.getImageListener(imageView, android.R.drawable.ic_menu_rotate, android.R.drawable.ic_delete);
					mImageLoader.get(URL, listener);

				}
				String brand = "";
				if(goods.getBrand() != null){
					brand = goods.getBrand();
				}
				txt_goods_name.setText(brand + goods.getTradeName());
				TextView txt_goods_price = (TextView) view.findViewById(R.id.txt_goods_price);
				txt_goods_price.setText(goods.getPrice() + "");
				return view;
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
	public void loadData(){
		// TODO Auto-generated method stub
		super.loadData();
		openProgressDialog();
		BmobQuery<Goods> query = new BmobQuery<Goods>();
		pageSize = 5;
		query.setLimit(pageSize);
		query.setSkip((pageNum - 1) * pageSize);
		if(point == 1){
			query.addWhereEqualTo("type", type);
		}else if(point == 2){
			query.addWhereEqualTo("tradeName", type);
		}
		query.findObjects(this, new FindListener<Goods>() {
			@Override
			public void onSuccess(List<Goods> object){
				// TODO Auto-generated method st
				closeProgressDialog();
				onRefreshComplete();
				list.addAll(object);
				Log.v("AAA", JSON.toJSONString(list));
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onError(int code,String msg){
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "查询失败", Toast.LENGTH_SHORT).show();
				closeProgressDialog();
			}
		});

	}

	public void queryData(String type,int point){
		this.type = type;
		this.point = point;
		if(pageNum == 1){
			list.clear();
		}
		loadData();

	}

	protected static class ViewHolder {
		public GridView gridView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent,View view,int position,long id){
		// TODO Auto-generated method stub
		int id1 = parent.getId();
		Log.v("AAA", position + "");
		if(id1 == R.id.gridview){
			pageNum = 1;
			if(position == 0){
				queryData("零食", 1);
			}else if(position == 1){
				queryData("面包", 1);
			}else if(position == 2){
				queryData("饮料", 1);
			}else if(position == 3){
				queryData("日常用品", 1);
			}
		}
	}

	@Override
	public void refreshData(){
		// TODO Auto-generated method stub
		pageNum = 1;
		list.clear();
		loadData();
	}

	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event){
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if((System.currentTimeMillis() - exitTime) > 2000){
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			}else{
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
