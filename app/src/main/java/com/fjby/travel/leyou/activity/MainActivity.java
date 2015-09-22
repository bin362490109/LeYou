package com.fjby.travel.leyou.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.adapter.SampleFragmentPagerAdapter;
import com.fjby.travel.leyou.entity.ResUser;
import com.fjby.travel.leyou.utils.IntentUtils;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.ToastUtils;
import com.google.gson.Gson;

public class MainActivity extends BaseActivity {
    private ViewPager mViewPager;
    private LinearLayout mLinearLayout;
    private LinearLayout []mLinearButton=new LinearLayout[5];
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ResUser mResUser;
    private int oldPosition=0;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            String msg = bundle.getString("user");
            Gson gson = new Gson();
            mResUser = gson.fromJson(msg, ResUser.class);
        }


        if(savedInstanceState!=null){
            LogUtil.e("savedInstanceState-------------");
            oldPosition=savedInstanceState.getInt("position",0);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
       // setToolbarNavigationIcon(R.drawable.nav_mine_selector);
        //slidemune另一个版本
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(mNavigationView);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        // 设置ViewPager最大缓存的页面个数
        mViewPager.setOffscreenPageLimit(3);

        SampleFragmentPagerAdapter pagerAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.e("onPageSelected--------------" + position);
                changeToobarSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initCustomToobar();
    }

   private void initCustomToobar(){
       mLinearLayout = (LinearLayout) findViewById(R.id.linear_tablayout);
       for(int i=0;i<mLinearLayout.getChildCount();i++){
           final int finalI ;
           if (i<2) {
               finalI=i;
           }else if(i==2){
               finalI=4;
           }else{
               finalI=i-1;
           }
           mLinearButton[finalI]= (LinearLayout)mLinearLayout.getChildAt(i);

           mLinearButton[finalI].setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (finalI != oldPosition) {
                       changeToobarSelect(finalI);
                   }
               }
           });
       }
       changeToobarSelect(0);
    }

private  void  changeToobarSelect(int position){
    if(position<4){
        mLinearButton[oldPosition].setSelected(false);
        mLinearButton[position].setSelected(true);
        mViewPager.setCurrentItem(position);
        oldPosition=position;
    }else{
        ToastUtils.showLong(MainActivity.this,"写游记吗？");
    }


}

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getInt("position", 0);
        changeToobarSelect(3);
        LogUtil.e("onRestoreInstanceState-------------");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", oldPosition);
        LogUtil.e("onSaveInstanceState-------------");
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.navigation_item_book:
                                IntentUtils.getInstance().startActivity(MainActivity.this, ProduceInfoActivity.class);
                                break;
                            case R.id.navigation_item_example:
                                LogUtil.v("---------------------------2");
                                break;
                            case R.id.navigation_item_blog:
                                LogUtil.v("---------------------------3");
                                break;
                            case R.id.navigation_item_about:
                                LogUtil.v("---------------------------4");
                                break;

                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    public void production(View view){
        IntentUtils.getInstance().startActivity(MainActivity.this, ProduceInfoActivity.class);
    }
    public void mytour(View view){
        IntentUtils.getInstance().startActivity(MainActivity.this, MyTourActivity.class);
    }
}
