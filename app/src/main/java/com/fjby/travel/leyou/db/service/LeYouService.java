package com.fjby.travel.leyou.db.service;

import android.content.Context;

import com.fjby.travel.leyou.db.dao.LocationCodeDao;
import com.fjby.travel.leyou.pojo.CityCode;
import com.fjby.travel.leyou.pojo.ProvinceCode;

import java.util.List;

/**
 * Created by abin on 2015/9/9.
 */
public class LeYouService {
    private static LeYouService mLeYouService;
    private static LocationCodeDao mLocationCodeDao;
    private Context mContext;
    static final Object mInstanceSync = new Object();
    private LeYouService(Context ctx) {
        if (mLocationCodeDao == null) {
            mLocationCodeDao = mLocationCodeDao.getInstance(ctx);
            mContext = ctx;
        }
    }
    public static LeYouService getInstance(Context ctx) {
        synchronized (mInstanceSync) {
            if (mLeYouService == null) {
                mLeYouService = new LeYouService(ctx);
            }
        }
        return mLeYouService;
    }

    /**
     * 删除数据库
     */
    public void deleteCodeK(){
        mLocationCodeDao.deleteCodeK();
    };
    /**
     * 保存中国省级数据到数据库
     */
    public void saveProvinces(List<ProvinceCode> provinces){
        mLocationCodeDao.saveProvinces(provinces);
    };
    /**
     * 保存某省市级数据到数据库
     */
    public void saveCitys(List<CityCode> citys){
        mLocationCodeDao.saveCitys(citys);
    };
    /**
     * 保存某省市级数据到数据库
     */
    public String selecltCode(String name){
        return mLocationCodeDao.selecltCode(name);
    };
}
