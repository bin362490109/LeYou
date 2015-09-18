package com.fjby.travel.leyou.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.fragment.ProduceInfoFragment;
import com.fjby.travel.leyou.utils.LogUtil;

/**
 * Created by abin on 2015/9/17.
 */
public class ProduceInfoActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        initToolbar(true, true);
        setToolbarTitle(R.string.produce_info);
       repalceFragment(new ProduceInfoFragment());
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
            if (id == R.id.menu_produce) {
                LogUtil.e("menu_produce                 onOptionsItemSelected");
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
}
