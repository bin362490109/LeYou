/**
 * 
 */

package com.fjby.travel.leyou.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.utils.ToastUtils;

/**
 * 
 */
public class PlayPopuwindow extends PopupWindow implements OnClickListener {

    private Activity mActivity;

    public PlayPopuwindow(Activity activity) {
        super(activity);
        this.mActivity = activity;
        initView(activity);
    }

    @SuppressWarnings("deprecation")
    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.popu_play, null);
        rootView.findViewById(R.id.popu_play).setOnClickListener(this);
        setContentView(rootView);
        setWidth(LayoutParams.WRAP_CONTENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setTouchable(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.popu_play:
                ToastUtils.showShort(mActivity, "播放语音");
                break;
            default:
                break;
        }
    }
}
