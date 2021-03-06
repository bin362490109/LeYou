package com.fjby.travel.leyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.adapter.MianFragmentPagerAdapter;
import com.fjby.travel.leyou.application.LeYouMyApplication;
import com.fjby.travel.leyou.http.HttpUtil;
import com.fjby.travel.leyou.utils.DialogUtils;
import com.fjby.travel.leyou.utils.IntentUtils;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.ToastUtils;
import com.fjby.travel.leyou.widget.RoundedImageView;

public class MainActivity extends BaseActivity {
    private ViewPager mViewPager;
    private LinearLayout mLinearLayout;
    private LinearLayout[] mLinearButton = new LinearLayout[5];
    public DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private int oldPosition = 0;
    private RoundedImageView mMenuImage;
    private TextView mNavTextView;
    private long exitTime = 0;

    @Override
    protected void setView() {
        LogUtil.e("-------------MainActivity-----------------------------");
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //slidemune另一个版本
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        View headeView= mNavigationView.inflateHeaderView(R.layout.navigation_header);
        mMenuImage = (RoundedImageView) headeView.findViewById(R.id.id_header_image);
        mNavTextView = (TextView) headeView.findViewById(R.id.id_header_text);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

    }

    @Override
    protected void setListener() {
        setupDrawerContent(mNavigationView);
        if (LeYouMyApplication.mUser!=null) {
            if (!TextUtils.isEmpty(LeYouMyApplication.mUser.getImageCode())){
                HttpUtil.testImageLoad(MainActivity.this,LeYouMyApplication.mUser.getImageCode(), mMenuImage, R.drawable.author, R.drawable.author);
            }
            mMenuImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(PassWordActivity.PassType, PassWordActivity.updateInfo);
                    IntentUtils.getInstance().startActivityWithBudle(MainActivity.this, PassWordActivity.class, bundle);
                }
            });
        }else{
            mMenuImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtils.getInstance().startActivity(MainActivity.this, LoginActivity.class);
                }
            });
        }

        // 设置ViewPager最大缓存的页面个数
        mViewPager.setOffscreenPageLimit(3);
        MianFragmentPagerAdapter pagerAdapter = new MianFragmentPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(myOnPageChangeListener);
        initCustomToobar();
    }

    @Override
    protected void doOther() {
        String vercode = getIntent().getExtras().getString("vercode", "700");
        if (vercode.equals("601")) {
            DialogUtils.mdialogShowOne(MainActivity.this, "升级", "版本太低了，一定要升级");
        } else if (vercode.equals("600")) {
            DialogUtils.mdialogShowTwo(MainActivity.this, "升级", "有新的版本，请选择升级");
        }
    }
    ViewPager.OnPageChangeListener myOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            changeToobarSelect(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private void initCustomToobar() {
        mLinearLayout = (LinearLayout) findViewById(R.id.linear_tablayout);
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            final int finalI;
            if (i < 2) {
                finalI = i;
            } else if (i == 2) {
                finalI = 4;
            } else {
                finalI = i - 1;
            }
            mLinearButton[finalI] = (LinearLayout) mLinearLayout.getChildAt(i);

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

    private void changeToobarSelect(int position) {
        if (position < 4) {
            mLinearButton[oldPosition].setSelected(false);
            mLinearButton[position].setSelected(true);
            mViewPager.setCurrentItem(position);
            oldPosition = position;
        } else {
            IntentUtils.getInstance().startActivity(MainActivity.this, MyRecordActivity.class);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getInt("position", 0);
        changeToobarSelect(1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", oldPosition);
    }

    @Override
    public void onResume() {
        super.onResume();
        setNavImageAndText();
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.navigation_item_collect:
                                IntentUtils.getInstance().startActivity(MainActivity.this, MyCollectActivity.class);
                                break;
                            case R.id.navigation_item_setting:
                                IntentUtils.getInstance().startActivity(MainActivity.this, MySettingActivity.class);
                                break;
                            case R.id.navigation_item_email:
                                IntentUtils.getInstance().startActivity(MainActivity.this, MyAppealActivity.class);
                                break;
                            case R.id.navigation_item_order:
                                break;
                            case R.id.navigation_item_like:
                                break;
                            case R.id.navigation_item_password:
                                if (LeYouMyApplication.mUser == null) {
                                    ToastUtils.show(MainActivity.this, "请先登录账户", 0);
                                    break;
                                }
                                Bundle bundle = new Bundle();
                                bundle.putInt(PassWordActivity.PassType, PassWordActivity.ChangePass);
                                IntentUtils.getInstance().startActivityWithBudle(MainActivity.this, PassWordActivity.class, bundle);
                                break;
                            case R.id.navigation_item_travel:
                                IntentUtils.getInstance().startActivity(MainActivity.this, MyTravelActivity.class);
                                break;

                        }
                        menuItem.setChecked(false);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    public void production(View view) {
        IntentUtils.getInstance().startActivity(MainActivity.this, HomeProduceActivity.class);
    }

    public void mytour(View view) {
        IntentUtils.getInstance().startActivity(MainActivity.this, HomeTourActivity.class);
    }

    public void notesSerch(View view) {
        IntentUtils.getInstance().startActivity(MainActivity.this, NotesLocationActivity.class);
    }

    public void notesCard(View view) {
        Intent intent = new Intent(MainActivity.this, NotesDetailActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, view.findViewById(R.id.notes_image), getString(R.string.transition_book_img));
        ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
    }

    private void setNavImageAndText() {
        if (LeYouMyApplication.mUser != null) {
            mNavTextView.setText(LeYouMyApplication.mUser.getUserName());
            mNavTextView.setVisibility(View.VISIBLE);
        } else {
            mNavTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.show(MainActivity.this, "再按一次返回", 0);
            exitTime = System.currentTimeMillis();
            return;
        }
        finish();
    }
}
