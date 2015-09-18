package com.fjby.travel.leyou.db.service;

import android.content.Context;

import com.fjby.travel.leyou.db.bean.City;
import com.fjby.travel.leyou.db.bean.County;
import com.fjby.travel.leyou.db.bean.Province;
import com.fjby.travel.leyou.db.dao.CoolWeatherDao;
import com.fjby.travel.leyou.db.dao.impl.CoolWeatherDaoImpl;

import java.util.List;

/**
 * Created by abin on 2015/9/9.
 */
public class CoolWeatherService {
    private static CoolWeatherService mCoolWeatherService;
    private static CoolWeatherDao mCoolWeatherDao;
    private Context mContext;
    static final Object mInstanceSync = new Object();
    private CoolWeatherService(Context ctx) {
        if (mCoolWeatherDao == null) {
            mCoolWeatherDao =CoolWeatherDaoImpl.getInstance(ctx);
            mContext = ctx;
        }
    }
    public static CoolWeatherService getInstance(Context ctx) {
        synchronized (mInstanceSync) {
            if (mCoolWeatherService == null) {
                mCoolWeatherService = new CoolWeatherService(ctx);
            }
        }
        return mCoolWeatherService;
    }

    /**
     * 保存中国省级数据到数据库
     */
    public void saveProvince(Province province){
        mCoolWeatherDao.saveProvince(province);
    };
    /**
     * 从数据库中加载中国省级数据
     */
    public List<Province> loadProvince(){
        return mCoolWeatherDao.loadProvince();
    };
    /**
     * 保存某省市级数据到数据库
     */
    public void saveCity(City city){
        mCoolWeatherDao.saveCity(city);
    };
    /**
     * 从数据库中加载某省市级数据
     */
    public List<City> loadCity(int provinceId){
        return mCoolWeatherDao.loadCity(provinceId);
    };
    /**
     * 保存某省市级数据到数据库
     */
    public void saveCounty(County county){
        mCoolWeatherDao.saveCounty(county);
    };
    /**
     * 从数据库中加载某市县级数据
     */
    public List<County> loadCounty(int cityId){
        return mCoolWeatherDao.loadCounty(cityId);
    };
}
