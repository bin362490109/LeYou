package com.fjby.travel.leyou.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.adapter.ListViewAdapter;
import com.fjby.travel.leyou.utils.DensityUtil;
import com.fjby.travel.leyou.utils.IntentUtils;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.ToastUtils;

import java.util.ArrayList;

/**
 * Created by abin on 2015/9/17.
 */
public class MyGuideActivity extends BaseActivity {

    //声明窗口PopupWindow
    private PopupWindow pw_play = null;

    private ImageButton mImageButton;
    private int[] location = new int[2];
    int x, y;
    boolean isFirst = true;
    private PopupWindow pw_spinner = null;
    TextView mSpinnerTV;
    private ListViewAdapter mListViewAdapter;
    int clickPsition =-1;

    private ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myguide);
        initToolbar(true, true);
        setToolbarTitle(R.string.mytourguide);
        //第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        list.add("动物园  3.69km");
        list.add("金牛山  23.6km");
        list.add("左海公园  16.6km");
        list.add("森林公园  4.56km");

        mSpinnerTV = (TextView) findViewById(R.id.spinner_text);
        mListViewAdapter=new ListViewAdapter(MyGuideActivity.this, list);
        mSpinnerTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(pw_spinner==null||!pw_spinner.isShowing()){
                    list.remove(mSpinnerTV.getText());
                    list.add(mSpinnerTV.getText().toString());
                    mListViewAdapter.notifyDataSetChanged();
                    openMune();
                }

            }

        });
        mSpinnerTV.setText(list.get(0));

        mImageButton = (ImageButton) findViewById(R.id.attractions);
        ViewTreeObserver vto = mImageButton.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mImageButton.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                x = mImageButton.getWidth();
                y = mImageButton.getHeight();
            }
        });
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirst) {
                    mImageButton.getLocationOnScreen(location);
                    x = location[0] - (DensityUtil.dip2px(MyGuideActivity.this, 150) - x) / 2;
                    y = location[1] + y;
                    LogUtil.e("x:" + x + "y:" + y);
                    isFirst = false;
                }
                openPlay();
            }
        });

    }

    public boolean openMune() {
        if (pw_spinner != null) {
            if (pw_spinner.isShowing()) {
                //关闭弹出窗口
                pw_spinner.dismiss();
            } else {
                //在指定位置弹出窗口
           pw_spinner.showAtLocation(mSpinnerTV, Gravity.BOTTOM,0, 0);
            }
        } else {
            //定义窗口菜单
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.popu_guide_pop, null);
            ListView lv = (ListView) view.findViewById(R.id.popu_list);
            lv.setAdapter(mListViewAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mSpinnerTV.setText(list.get(position));
                    if (clickPsition != position) {
                        clickPsition = position;
                    }
                    pw_spinner.dismiss();
                }
            });
            //生成PopupWindow对象
            pw_spinner = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            // 必须设置背景
            pw_spinner.setBackgroundDrawable(new BitmapDrawable());
            //在指定位置弹出窗口
            pw_spinner.setFocusable(false);
            pw_spinner.setOutsideTouchable(true);
          pw_spinner.showAtLocation(mSpinnerTV, Gravity.BOTTOM, 0, 0);
        }

        // 此处返回false，系统不会执行onCreateOptionsMenu中添加的菜单
        return false;
    }
    public boolean openPlay() {
        if (pw_play != null) {
            if (pw_play.isShowing()) {
                //关闭弹出窗口
                pw_play.dismiss();
            } else {
                //在指定位置弹出窗口
                pw_play.showAtLocation(mImageButton, Gravity.NO_GRAVITY, x, y);
            }
        } else {
            //定义窗口菜单
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.popu_play, null);
            TextView textView = (TextView) view.findViewById(R.id.menu_text_1);
            TextView textView2 = (TextView) view.findViewById(R.id.menu_text_2);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShort(MyGuideActivity.this, "消息中心");
                }
            });
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtils.getInstance().startActivity(MyGuideActivity.this, ProduceInfoActivity.class);
                }
            });
            //生成PopupWindow对象
            pw_play = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            // 必须设置背景
            pw_play.setBackgroundDrawable(new BitmapDrawable());
            //在指定位置弹出窗口
            pw_play.setFocusable(false);
            pw_play.setOutsideTouchable(true);
            pw_play.showAtLocation(mImageButton, Gravity.NO_GRAVITY, x, y);
        }

        // 此处返回false，系统不会执行onCreateOptionsMenu中添加的菜单
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pw_play!=null&&pw_play.isShowing()) {
                pw_play.dismiss();
                return false;
            }
            if (pw_spinner!=null&&pw_spinner.isShowing()) {
                pw_spinner.dismiss();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}

