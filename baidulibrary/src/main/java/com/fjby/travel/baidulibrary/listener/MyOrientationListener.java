package com.fjby.travel.baidulibrary.listener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
  
public class MyOrientationListener implements SensorEventListener  
{  
  
    private Context context;  
    private SensorManager sensorManager;  
    private Sensor magneticSensor, accelerometerSensor;
    private float[] values, r, gravity, geomagnetic;
    private double oldValue;  
    private OnOrientationListener onOrientationListener ;   
  
    public MyOrientationListener(Context context)  
    {  
        this.context = context;  
        //初始化数组
        values = new float[3];//用来保存最终的结果
        gravity = new float[3];//用来保存加速度传感器的值
        r = new float[9];//
        geomagnetic = new float[3];//用来保存地磁传感器的值
        oldValue=0.0f;
    }  
  
    // 开始  
    public void start()  
    {  
        // 获得传感器管理器  
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);  
        if (sensorManager != null)  
        {  
            //获取Sensor
            magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        // 注册  
          if (magneticSensor != null&& accelerometerSensor!=null)  
        {//SensorManager.SENSOR_DELAY_UI  
            sensorManager.registerListener(this, magneticSensor,SensorManager.SENSOR_DELAY_NORMAL);  
            sensorManager.registerListener(this, accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);  
        } 
    }
  
    // 停止检测  
    public void stop()  
    {  
        sensorManager.unregisterListener(this);  
    }  
  
    @Override  
    public void onAccuracyChanged(Sensor sensor, int accuracy)  
    {  
          
    }  
  
    @Override  
    public void onSensorChanged(SensorEvent event)  
    {   
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values;
        }
        getValue();
    }  
      
    public void setOnOrientationListener(OnOrientationListener onOrientationListener)  
    {  
        this.onOrientationListener = onOrientationListener ;  
    }  
      
      
    public interface OnOrientationListener   
    {  
        void onOrientationChanged(float x);  
    }  
    
    public void getValue() {
        // r从这里返回
        SensorManager.getRotationMatrix(r, null, gravity, geomagnetic);
        //values从这里返回
        SensorManager.getOrientation(r, values);
        //提取数据
        double azimuth = Math.toDegrees(values[0]);
        if (azimuth<0) {
            azimuth=azimuth+360;
        }
        if(Math.abs(azimuth-oldValue)>1.7){
        onOrientationListener.onOrientationChanged((int)azimuth);  
        Log.v("crb", " ---------偏移手机");
        }
        oldValue=azimuth;
    //    double pitch = Math.toDegrees(values[1]);
  //     double roll = Math.toDegrees(values[2]);
//  Log.v("crb","Azimuth：" + (int)azimuth + "    Pitch：" + (int)pitch + "     Roll：" + (int)roll);
    }
}  