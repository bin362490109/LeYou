package com.fjby.travel.leyou.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fjby.travel.leyou.db.LeYouOpenHelper;
import com.fjby.travel.leyou.pojo.CityCode;
import com.fjby.travel.leyou.pojo.ProvinceCode;
import com.fjby.travel.leyou.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class LocationCodeDao {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "cool_weather";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;
    private static LocationCodeDao mlLocationCodeDao;
    private SQLiteDatabase db;
    private LeYouOpenHelper dbHelper;

    /**
     * 构造方法私有化
     */
    private LocationCodeDao(Context context) {
        dbHelper = new LeYouOpenHelper(context, DB_NAME, null, VERSION);
    }

    /**
     * 获取CoolWeatherDB实例
     */

    public synchronized static LocationCodeDao getInstance(Context context) {
        if (mlLocationCodeDao == null) {
            mlLocationCodeDao = new LocationCodeDao(context);
        }
        return mlLocationCodeDao;
    }


    /**
     * 保存中国省级数据到数据库
     */
    public void deleteCodeK() {
            try {
                db = dbHelper.getWritableDatabase();
                db.delete("province",null,null);
                db.delete("city",null,null);
            } catch (Exception e) {
                LogUtil.e("数据库错误");
            } finally {
                db.close();
            }
    }


    /**
     * 保存中国省级数据到数据库
     */
    public void saveProvince(ProvinceCode province) {
        if (province != null) {
            try {
                db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("province", province.getProvince());
                values.put("province_code", province.getProvinceCode());
                db.insert("province", null, values);
            } catch (Exception e) {
                LogUtil.e("数据库错误");
            } finally {
                db.close();
            }
        }
    }

    /**
     * 保存中国省级数据到数据库
     */
    public void saveProvinces(List<ProvinceCode> provinces) {
        if (provinces != null) {

            try {
                db = dbHelper.getWritableDatabase();
                db.beginTransaction();
                ContentValues values;
                for (ProvinceCode province : provinces) {
                    values = new ContentValues();
                    values.put("province", province.getProvince());
                    values.put("province_code", province.getProvinceCode());
                    db.insert("province", null, values);
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                LogUtil.e("数据库错误");
            } finally {
                db.endTransaction();
                db.close();
            }


        }
    }

    /**
     * 从数据库中加载中国省级数据
     */
    public List<ProvinceCode> loadProvince() {
        List<ProvinceCode> list = new ArrayList<ProvinceCode>();
        try {
            db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("province", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    ProvinceCode province = new ProvinceCode();
                    province.setProvince(cursor.getString(cursor.getColumnIndex("province")));
                    province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                    list.add(province);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            LogUtil.e("数据库错误");
        } finally {
            db.close();
        }
        return list;
    }

    /**
     * 保存某省市级数据到数据库
     */
    public void saveCity(CityCode city) {
        if (city != null) {
            try {
                db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("city", city.getCity());
                values.put("city_code", city.getCityCode());
                values.put("province_code", city.getCityCode().substring(0, 4));
                db.insert("city", null, values);
            } catch (Exception e) {
                LogUtil.e("数据库错误");
            } finally {
                db.close();
            }

        }
    }

    /**
     * 保存某省市级数据到数据库
     */
    public void saveCitys(List<CityCode> citys) {
        if (citys != null) {
            try {
                db = dbHelper.getWritableDatabase();
                db.beginTransaction();
                ContentValues values;
                for (CityCode city : citys) {
                    values = new ContentValues();
                    values.put("city", city.getCity());
                    values.put("city_code", city.getCityCode());
                    values.put("province_code", city.getCityCode().substring(0, 2));
                    db.insert("city", null, values);
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                LogUtil.e("数据库错误");
            } finally {
                db.endTransaction();
                db.close();
            }

        }
    }

    /**
     * 从数据库中加载某省市级数据
     */
    public List<CityCode> loadCity(int provinceCode) {
        List<CityCode> list = new ArrayList<CityCode>();
        try {
            db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("city", null, "province_id=?", new String[]{String.valueOf(provinceCode)}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    CityCode city = new CityCode();
                    city.setCity(cursor.getString(cursor.getColumnIndex("city_name")));
                    city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                    list.add(city);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            LogUtil.e("数据库错误");
        } finally {
            db.close();
        }
        return list;
    }
    /**
     * 从数据库中加载某省市级数据
     */
    public String selecltCode(String name) {
        List<CityCode> list = new ArrayList<CityCode>();
        String codeK="";
        try {
            db = dbHelper.getWritableDatabase();

            Cursor cursor = db.query("city", null, "city like ?", new String[]{name+'%'}, null, null, null);
            if (cursor.moveToFirst()) {
                codeK=cursor.getString(cursor.getColumnIndex("city_code"));
            }
            if (codeK.equalsIgnoreCase("")) {
                cursor = db.query("province", null, "province like ?", new String[]{name + '%'}, null, null, null);
                if (cursor.moveToFirst()) {
                    codeK = cursor.getString(cursor.getColumnIndex("province_code"));
                }
            }
        } catch (Exception e) {
            LogUtil.e("数据库错误"+e);
        } finally {
            db.close();
            LogUtil.e("-------------"+codeK);
        }
        return codeK;
    }
}

