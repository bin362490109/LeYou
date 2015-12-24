package com.fjby.travel.leyou.activity;

import android.os.Build;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.utils.IntentUtils;
import com.fjby.travel.leyou.widget.MunePopuwindow;

/**
 * Created by abin on 2015/9/17.
 */
public class HomeTourActivity extends BaseActivity {

    //声明窗口PopupWindow
    private PopupWindow pw = null;
    private ImageView mImageView;
    private ToggleButton mToggleButton;
    private RelativeLayout mLinearLayout;
    private int[] location = new int[2];


    @Override
    protected void setView() {
        setContentView(R.layout.activity_home_tour);
        initToolbar(true, true);
        setToolbarTitle(R.string.mytour);
    }

    @Override
    protected void initView() {
        mImageView = (ImageView) findViewById(R.id.mytour_menu);
        mToggleButton = (ToggleButton) findViewById(R.id.tour_toggleBtn);
        mLinearLayout = (RelativeLayout) findViewById(R.id.tour_text);
    }

    @Override
    protected void setListener() {
        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    mLinearLayout.setVisibility(View.GONE);
                }

            }
        });
        ViewTreeObserver vto = mImageView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        mImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }else{
                        mImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                location[0] = mImageView.getWidth();
                location[1] = mImageView.getHeight();
            }
        });
    }

    @Override
    protected void doOther() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pw != null && pw.isShowing()) {
                pw.dismiss();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_more, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_more:
                openPopu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void  openPopu(){
        if (pw == null)
            pw = new MunePopuwindow(HomeTourActivity.this);
        pw.showAtLocation(mImageView, Gravity.RIGHT, 0, 0 - location[1]);
    }

    public void map(View view) {
        IntentUtils.getInstance().startActivity(HomeTourActivity.this, HomeTourGuideActivity.class);
    }
}
