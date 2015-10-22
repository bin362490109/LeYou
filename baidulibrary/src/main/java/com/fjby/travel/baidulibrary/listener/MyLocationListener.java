package com.fjby.travel.baidulibrary.listener;

/**
 * Created by abin on 2015/10/21.
 */
public class MyLocationListener {
    private OnLocationListener onLocationListener;

    public interface OnLocationListener {
        void onLocation(double x,double y);
    }


    public void setOnLocationListener(OnLocationListener listener) {
       onLocationListener = listener;
    }

    public void setCurrentLocation(double x,double y){
        if (onLocationListener!=null)
        onLocationListener.onLocation(x,y);
    }

}
