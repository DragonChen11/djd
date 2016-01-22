package com.ksy.djd.mainpanel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.ksy.djd.app.MyApplication;
import com.ksy.djd.model.trade.Goods;
import com.ksy.djd.util.ActivityBase;
import com.ksy.djd.util.ui.TopBar;
import com.ksy.djd.widget.ViewPagerAdapter;
import com.sky.djd.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainPanelActivity extends ActivityBase implements AdapterView.OnItemClickListener{
    private ArrayList<View> listViews = new ArrayList<View>();
    private ArrayList<RadioButton> radioButtons = new ArrayList<RadioButton>();
    ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<MenuItem> menus = new ArrayList<MenuItem>();
    View view0;
    private SlidingMenu menu;
    private MyApplication app;
    public Context context;
    public Resources r;
    public LayoutInflater inflater;
    PullToRefreshListView mPullRefreshListView;
    protected BaseAdapter adapter;
    List<Goods> list = new ArrayList<Goods>();
    protected int pageNum = 1;
    protected int pageSize=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initLeftMenu();
        initSlideMenu();
        initTopBar();
        initRadioButton();
        initViewPager();
        initPage0();
    }

    private void initView() {
        setContentView(R.layout.activity_main_panel);
        r=getResources();
        inflater=getLayoutInflater();
    }

    private void initSlideMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidth(0);

        // 设置滑动菜单视图的宽度

        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(1f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW );
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.layout_left_menu);

    }
    private void initLeftMenu(){
        View left=inflater.inflate(R.layout.layout_left_menu,null);
        CircleImageView circleImageView= (CircleImageView)left.findViewById(R.id.profile_image);
        TextView textView= (TextView) left.findViewById(R.id.textView);
        ListView list_view_item_image_view= (ListView) left.findViewById(R.id.list_view_item_image_view);
        ArrayList<Item> listItem=new ArrayList<Item>();
        Item item1=new Item(R.drawable.kongbai,R.string.my_wallet);
        listItem.add(item1);
        ListViewMenuAdapter adapter=new ListViewMenuAdapter(this,listItem);
        list_view_item_image_view.setAdapter(adapter);
    }

    private void initTopBar() {
        View leftView=getLayoutInflater().inflate(R.layout.global_widget_topbar_left,null);
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menu.getMenu().isShown()){

                }else{
                    menu.showMenu();
                }
            }
        });
        TextView centerView = new TextView(this);
        centerView.setText("校园通");
        centerView.setTextColor(Color.WHITE);
        centerView.setTextSize(23);
        View rightView = getLayoutInflater().inflate(R.layout.global_widget_topbar_mainview, null);
        ImageView img_search= (ImageView) rightView.findViewById(R.id.img_search);
        img_search.setVisibility(View.VISIBLE);
        TopBar topBar = new TopBar(this,false,leftView,centerView, rightView);
        topBar.bind();
    }


    private  void initRadioButton(){
        RadioButton radioButton0= (RadioButton) findViewById(R.id.radioButton0);
        RadioButton radioButton1= (RadioButton) findViewById(R.id.radioButton1);
        RadioButton radioButton2= (RadioButton) findViewById(R.id.radioButton2);
        RadioButton radioButton3= (RadioButton) findViewById(R.id.radioButton3);
        radioButtons.add(radioButton0);
        radioButtons.add(radioButton1);
        radioButtons.add(radioButton2);
        radioButtons.add(radioButton3);
        for(RadioButton radioButton:radioButtons){
            radioButton.setOnClickListener(MainPanelActivity.this);
        }
    }

    private void initViewPager(){
        viewPager= (ViewPager) findViewById(R.id.vp_panel);
        //inflater= LayoutInflater.from(this);
        View view_homepage=inflater.inflate(R.layout.viewpager_home_screen,null);
        View view_topic=inflater.inflate(R.layout.viewpager_topic,null);
        View view_notice=inflater.inflate(R.layout.viewpager_notice,null);
        View view_job=inflater.inflate(R.layout.viewpager_job,null);
        listViews.add(0,view_homepage);
        listViews.add(1,view_topic);
        listViews.add(2,view_notice);
        listViews.add(3,view_job);
        viewPagerAdapter = new ViewPagerAdapter(listViews, viewPager);
        viewPagerAdapter.setViewPagerScrollSpeed();
        viewPager.setOffscreenPageLimit(listViews.size());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int i,float f,int i1){
            }

            public void onPageScrollStateChanged(int i){
            }

            public void onPageSelected(int i){
                viewPagerAdapter.setCureentRadioButtontIndex(i);
                RadioButton radioButton = radioButtons.get(i);
                radioButton.setChecked(true);

            }
        });
    }

    private void initPage0() {
        View view_homepage=listViews.get(0);
        Integer[] images=new Integer[]{R.drawable.account_tag,R.drawable.account_tag};
        MyGallery myGallery=new MyGallery(this,view_homepage);
        myGallery.setImages(images);
        GridView gridview= (GridView) view_homepage.findViewById(R.id.gridview);

        gridview.setOnItemClickListener(this);
        Integer[] i=new Integer[]{R.drawable.backgroup,R.drawable.backgroup1,R.drawable.backgroup4,R.drawable.background_app};

        MenuItem item1=new MenuItem(R.drawable.panel_message_selected,r.getString(R.string.app_name),"MainPanelActivity.this");
        MenuItem item2=new MenuItem(R.drawable.panel_message_selected,r.getString(R.string.app_name),"MainPanelActivity.this");
        MenuItem item3=new MenuItem(R.drawable.panel_message_selected,r.getString(R.string.app_name),"MainPanelActivity.this");
        MenuItem item4=new MenuItem(R.drawable.panel_message_selected,r.getString(R.string.app_name),"MainPanelActivity.this");
        MenuItem item5=new MenuItem(R.drawable.panel_message_selected,r.getString(R.string.app_name),"MainPanelActivity.this");
        MenuItem item6=new MenuItem(R.drawable.panel_message_selected,r.getString(R.string.app_name),"MainPanelActivity.this");
        menus.add(item1);
        menus.add(item2);
        menus.add(item3);
        menus.add(item4);
        menus.add(item5);
        menus.add(item6);
        GridViewAdapter adapter=new GridViewAdapter(this,menus);
        gridview.setAdapter(adapter);
        mPullRefreshListView = (PullToRefreshListView) view_homepage.findViewById(R.id.pull_refresh_list);
        onlyUpRefresh();
        initRefreshView();

    }
    protected void onlyUpRefresh(){
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
    }

    protected void initRefreshView(){
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView){
                refreshData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView){
                pageNum += 1;
                loadData();
            }
        });
    }

    private void refreshData() {
    }

    public void loadData(){

        openProgressDialog();
        BmobQuery<Goods> query = new BmobQuery<Goods>();
        pageSize = 5;
        query.setLimit(pageSize);
        query.setSkip((pageNum - 1) * pageSize);
//        if(point == 1){
//            query.addWhereEqualTo("type", type);
//        }else if(point == 2){
//            query.addWhereEqualTo("tradeName", type);
//        }
        query.findObjects(this, new FindListener<Goods>() {
            @Override
            public void onSuccess(List<Goods> object){
                // TODO Auto-generated method st
                closeProgressDialog();
                onRefreshComplete();
                list.addAll(object);
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

    protected void onRefreshComplete(){
        mPullRefreshListView.onRefreshComplete();
    }
    
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
