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
import com.fjby.travel.leyou.fragment.ProduceInstructFragment;
import com.fjby.travel.leyou.fragment.ProduceReviewFragment;
import com.fjby.travel.leyou.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abin on 2015/9/17.
 */
public class MyTourActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytour);
        initToolbar(true, true);
        setToolbarTitle(R.string.mytour);




    }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_more, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            //noinspection SimplifiableIfStatement
            if (id == R.id.menu_more) {
                LogUtil.e("menu_produce                 onOptionsItemSelected");
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
}
