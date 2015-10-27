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

import com.fjby.travel.baidulibrary.activity.MapTestActivity;
import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.utils.IntentUtils;
import com.fjby.travel.leyou.utils.ToastUtils;

/**
 * 
 */
public class MunePopuwindow extends PopupWindow implements OnClickListener {

    private Activity mActivity;

    public MunePopuwindow(Activity activity) {
        super(activity);
        this.mActivity = activity;
        initView(activity);
    }

    @SuppressWarnings("deprecation")
    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.popu_produce, null);
        rootView.findViewById(R.id.menu_text_1).setOnClickListener(this);
        rootView.findViewById(R.id.menu_text_2).setOnClickListener(this);
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
            case R.id.menu_text_1:
                ToastUtils.showShort(mActivity, "消息中心");
                break;
            case R.id.menu_text_2:
                IntentUtils.getInstance().startActivity(mActivity, MapTestActivity.class);
                break;
            default:
                break;
        }
    }
}
