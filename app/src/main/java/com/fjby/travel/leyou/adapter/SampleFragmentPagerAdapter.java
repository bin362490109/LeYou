package com.fjby.travel.leyou.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.fragment.HomeFragment;
import com.fjby.travel.leyou.fragment.InfoFragment;
import com.fjby.travel.leyou.fragment.NewsFragment;
import com.fjby.travel.leyou.fragment.NotesFragment;
import com.fjby.travel.leyou.fragment.PageFragment;

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    public static  int[] pagetitl={R.string.main_home,R.string.main_info, R.string.main_notes,R.string.main_news};
    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }


    @Override
    public int getCount() {
        return pagetitl.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return  new InfoFragment();
            case 2:
                return new NotesFragment();
            case 3:
                return  new NewsFragment();

            default:
                return PageFragment.newInstance(position);
        }

    }

}
