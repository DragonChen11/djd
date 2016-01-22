package com.ksy.djd.mainpanel;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksy.djd.app.MyApplication;
import com.sky.djd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class GridViewAdapter extends BaseAdapter {
    Activity activity;
    private Context mContext;
    private List<MenuItem> menus = new ArrayList<MenuItem>();
    private ViewGroup.LayoutParams textViewParams=null;

    public  GridViewAdapter(Activity activity,List<MenuItem> menus){
        this.activity=activity;
        mContext=activity.getBaseContext();
        this.menus=menus;
        textViewParams=new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,GridView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public Object getItem(int position) {
        return menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = activity.getLayoutInflater().inflate(R.layout.gridview_item, null);
        ImageView item = (ImageView) convertView.findViewById(R.id.item);
        if ((position + 1) <= menus.size()) {
            MenuItem menuItem = menus.get(position);
            int Id = menuItem.getIconId();
            if (Id == 0) {
            //    view.setBackgroundResource(R.drawable.kongbai);
            } else {
               item.setImageResource(Id);
            }
            // updateNum(menuItem, txt_notice);
            TextView textView= (TextView) convertView.findViewById(R.id.txt_notice);
            textView.setText(menuItem.getName());
        } else {
            //    view.setBackgroundResource(R.drawable.kongbai);
        }

        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                (int) (MyApplication.getInstance().screenHeight * 0.15));
        convertView.setLayoutParams(layoutParams);
        return convertView;
    }

}
