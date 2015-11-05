package com.fjby.travel.leyou.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.utils.IntentUtils;
import com.fjby.travel.leyou.utils.LogUtil;

/**
 * Created by abin on 2015/9/17.
 */
public class NotesDetailActivity extends BaseActivity {
    //声明窗口PopupWindow
    private PopupWindow pw = null;
    private LinearLayout notes_linear;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_notes_detail);
        initToolbar(true, true);
        setToolbarNavigationIcon(R.drawable.nav_back_selector);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        LogUtil.e("onCreateOptionsMenu   onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        LogUtil.e("onPrepareOptionsMenu   onPrepareOptionsMenu");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_review:
                IntentUtils.getInstance().startActivity(NotesDetailActivity.this,NotesDetailReviewsActivity.class);
                return true;
            case R.id.nav_share:
       /*         Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT, "I would like to share this with you...");
                startActivity(Intent.createChooser(intent, getTitle()));
                */
                openMune();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean openMune() {
        if (pw != null) {
            if (pw.isShowing()) {
                //关闭弹出窗口
                pw.dismiss();
            } else {
                //在指定位置弹出窗口
                pw.showAtLocation(notes_linear, Gravity.BOTTOM, 0, 0);
            }
        } else {
            //定义窗口菜单
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.popu_share_listview, null);
            notes_linear= (LinearLayout) findViewById(R.id.notes_linear);
            //生成PopupWindow对象
            pw = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            // 必须设置背景
            pw.setBackgroundDrawable(new BitmapDrawable());
            //在指定位置弹出窗口
            pw.setFocusable(false);
            pw.setOutsideTouchable(true);
            pw.showAtLocation(notes_linear, Gravity.BOTTOM, 0, 0);
        }

        // 此处返回false，系统不会执行onCreateOptionsMenu中添加的菜单
        return false;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pw!=null&&pw.isShowing()) {
                pw.dismiss();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
