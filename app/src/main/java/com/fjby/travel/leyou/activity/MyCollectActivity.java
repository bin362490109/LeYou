package com.fjby.travel.leyou.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.fragment.MyCollectProduceFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abin on 2015/9/17.
 */
public class MyCollectActivity extends BaseActivity {
    private ViewPager mViewPager;
    private   TabLayout tabLayout;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_mycollect);
        initToolbar(true, true);
        setToolbarTitle(R.string.mycollect_title);
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.mycollect_viewpaper);
        tabLayout = (TabLayout) findViewById(R.id.mycollect_tablayout);
    }

    @Override
    protected void setListener() {
        setupViewPager(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void doOther() {

    }

    private void setupViewPager(ViewPager mViewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyCollectProduceFragment(), "产品");
        adapter.addFragment(new MyCollectProduceFragment(), "游记");
        adapter.addFragment(new MyCollectProduceFragment(), "旅讯");
        mViewPager.setAdapter(adapter);
    }


    static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
