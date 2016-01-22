package com.ksy.djd.mainpanel;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.djd.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/21.
 */
public class ListViewMenuAdapter extends BaseAdapter {
    ArrayList<Item> arrayList=null;
    Activity activity=null;

    public ListViewMenuAdapter(Activity activity,ArrayList<Item> arrayList) {
        this.arrayList = arrayList;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        convertView=inflater.inflate(R.layout.list_menu_item,null);
        ImageView imageView= (ImageView) convertView.findViewById(R.id.imageView);
        TextView textView= (TextView) convertView.findViewById(R.id.textView);
        Item item=arrayList.get(position);
        imageView.setBackgroundResource(item.id);
        textView.setText(item.text);
        return convertView;
    }

}
