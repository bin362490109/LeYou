package com.fjby.travel.leyou.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fjby.travel.baidulibrary.activity.MapTestActivity;
import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.utils.IntentUtils;
import com.fjby.travel.leyou.utils.ToastUtils;

/**
 * Created by abin on 2015/9/17.
 */
public class HomeTourLocationActivity extends BaseActivity {

    //声明窗口PopupWindow
    private PopupWindow pw = null;
    private ImageView mImageView;
    private int[] location = new int[2];

    @Override
    protected void setView() {
        setContentView(R.layout.activity_home_tour_location);
        initToolbar(true, true);
        setToolbarTitle(R.string.mytourguidelocation);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void doOther() {

    }

    public boolean openMune() {
        if (pw != null) {
            if (pw.isShowing()) {
                //关闭弹出窗口
                pw.dismiss();
            } else {
                //在指定位置弹出窗口
                pw.showAtLocation(mImageView, Gravity.RIGHT, 0, 0 - location[1]);
            }
        } else {
            //定义窗口菜单
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.popu_produce, null);
            TextView textView = (TextView) view.findViewById(R.id.menu_text_1);
            TextView textView2 = (TextView) view.findViewById(R.id.menu_text_2);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShort(HomeTourLocationActivity.this, "消息中心");
                }
            });
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtils.getInstance().startActivity(HomeTourLocationActivity.this, MapTestActivity.class);
                }
            });
            //生成PopupWindow对象
            pw = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            // 必须设置背景
            pw.setBackgroundDrawable(new BitmapDrawable());
            //在指定位置弹出窗口
            pw.setFocusable(false);
            pw.setOutsideTouchable(true);
            pw.showAtLocation(mImageView, Gravity.RIGHT, 0, 0 - location[1]);
        }

        // 此处返回false，系统不会执行onCreateOptionsMenu中添加的菜单
        return false;
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
                //  openMune();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
