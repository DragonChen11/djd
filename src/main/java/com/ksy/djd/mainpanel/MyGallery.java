package com.ksy.djd.mainpanel;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.sky.djd.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/12/16.
 */
public class MyGallery {
    private Timer timer;
    private Handler handler;
    public int current = 0;
    public ImageSwitcher imageSwitcher;
    private Integer[] images;
    private View view;
    Activity activity;
    public MyGallery(Activity context, Integer[] images,View view){
        this.view=view;
        this.images=images;
        this.activity=context;
        init();
    }

    public MyGallery( Activity context,View  view){
        this.activity=context;
        this.view=view;
    }

    private void init()
    {
        imageSwitcher= (ImageSwitcher) view.findViewById(R.id.imageSwitcher);
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_left_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_left_out));
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView=new ImageView(activity);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
                return  imageView;
            }
        });
        imageSwitcher.setImageResource(images[0]);
        handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                if(msg.what == 0x123)
                {
                    //设置gallery显示第几张图片
                    imageSwitcher.setImageResource(images[current%images.length]);

                }
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask()
                {

                    @Override
                    public void run() {
                current++;
                Message msg = new Message();
                msg.what = 0x123;
                handler.sendMessage(msg);
            }
        }, 0, 1000);

    }

    public  void setImages(Integer[] images){
        this.images=images;
        init();
    }

    public Integer[] getImages(){
        return  images;
    }
}
