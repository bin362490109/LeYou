package com.fjby.travel.leyou.wheel;

import android.app.Activity;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.StringUtils;

import java.util.Calendar;

/**
 * Created by abin on 2015/12/3.
 */
public class CustomMaterialDate {
    private WheelView yearView;
    private WheelView monthView;
    private WheelView dayView;
    private  MaterialDialog materialDialog;
    private  Calendar c = Calendar.getInstance();
    private Activity mActivity;
    public  interface TextCallbackListener{
        void getTime(String response);
    };
    public CustomMaterialDate(final Activity ctx,final TextCallbackListener textCallbackListener){
        if (mActivity==null)
            mActivity=ctx;
        if(materialDialog==null)
            materialDialog = new MaterialDialog.Builder(ctx).title("时间选择器") .customView(R.layout.datapick, true)
                    .positiveText("确定").negativeText("取消").theme(Theme.LIGHT)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                            String str = (yearView.getCurrentItem()+2010) + "-"+ StringUtils.setLenghtTwo((monthView.getCurrentItem()+1)+"")+"-"+ StringUtils.setLenghtTwo((dayView.getCurrentItem()+1)+"");
                            LogUtil.e("MaterialDateDialog=="+str);
                            textCallbackListener.getTime(str);
                            Toast.makeText(ctx, str, Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            super.onNegative(dialog);
                            dialog.dismiss();
                        }
                    }).build();

        int curYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
        int curDate = c.get(Calendar.DATE);
         yearView = (WheelView) materialDialog.getCustomView().findViewById(R.id.year);
        yearView.setAdapter(new NumericWheelAdapter(2010, curYear));
        yearView.setLabel("年");
        yearView.setCyclic(false);
        yearView.addScrollingListener(scrollListener);
        	monthView = (WheelView) materialDialog.getCustomView().findViewById(R.id.month);
        monthView.setAdapter(new NumericWheelAdapter(1, 12));
        monthView.setLabel("月");
        monthView.setCyclic(true);
        monthView.addScrollingListener(scrollListener);
        	dayView = (WheelView) materialDialog.getCustomView().findViewById(R.id.day);
        initDay(curYear,curMonth);
        dayView.setLabel("日");
        dayView.setCyclic(true);

        dayView.setCurrentItem(curDate - 1);
        monthView.setCurrentItem(curMonth - 1);
        yearView.setCurrentItem(curYear-2010);
    }

    public void show(){
        if (materialDialog!=null&&!materialDialog.isShowing())
        materialDialog.show();
    }

    public void dismiss(){
        if (materialDialog!=null&&materialDialog.isShowing()){
            materialDialog.dismiss();
            materialDialog=null;
            mActivity=null;
        }
    }

    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {

        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            // TODO Auto-generated method stub
            int n_year = yearView.getCurrentItem() + 2010;// 楠烇拷
            int n_month = monthView.getCurrentItem() + 1;// 閺堬拷
            initDay(n_year,n_month);
        }
    };
    /**
     *
     * @param year
     * @param month
     * @return
     */
    private int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }

    /**
     */
    private void initDay(int arg1, int arg2) {
        int cur=getDay(arg1, arg2);
        dayView.setAdapter(new NumericWheelAdapter(1, cur, "%02d"));
        if (dayView.getCurrentItem()+1>cur) {
            dayView.setCurrentItem(cur - 1, true);
        }
    }
}
