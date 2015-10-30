package com.fjby.travel.leyou.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.fragment.ProduceInfoFragment;
import com.fjby.travel.leyou.fragment.ProduceDetailFragment;
import com.fjby.travel.leyou.fragment.ProduceReviewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abin on 2015/9/17.
 */
public class HomeProduceActivity extends BaseActivity {
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_produce);
        initToolbar(true, true);
        setToolbarTitle(R.string.produce_info);

        mViewPager = (ViewPager) findViewById(R.id.prodice_viewpager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.produce_tablayout);
        tabLayout.setupWithViewPager(mViewPager);


    }

    private void setupViewPager(ViewPager mViewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProduceInfoFragment(), "基本信息");
        adapter.addFragment(new ProduceDetailFragment(), "行程介绍");
        adapter.addFragment(new ProduceReviewFragment(), "点评（10）");
        mViewPager.setAdapter(adapter);
    }


     class MyPagerAdapter extends FragmentPagerAdapter {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_phone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_phone) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
