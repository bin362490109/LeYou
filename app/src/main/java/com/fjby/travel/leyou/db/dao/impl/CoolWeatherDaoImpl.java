package com.fjby.travel.leyou.db.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fjby.travel.leyou.db.CoolWeatherOpenHelper;
import com.fjby.travel.leyou.db.bean.City;
import com.fjby.travel.leyou.db.bean.County;
import com.fjby.travel.leyou.db.bean.Province;
import com.fjby.travel.leyou.db.dao.CoolWeatherDao;

import java.util.ArrayList;
import java.util.List;

public class CoolWeatherDaoImpl implements CoolWeatherDao{
	/**
	 * 数据库名
	 */
	public static final String DB_NAME="cool_weather";
	/**
	 * 数据库版本
	 */
	public static final int VERSION=1;
	private static CoolWeatherDaoImpl coolWeatherDaoImpl;
	private SQLiteDatabase db;
	/**
	 * 构造方法私有化
	 */
	private CoolWeatherDaoImpl(Context context){
		CoolWeatherOpenHelper dbHelper=new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db=dbHelper.getWritableDatabase();
	}
	/**
	 * 获取CoolWeatherDB实例
	 */

	public synchronized static CoolWeatherDaoImpl getInstance (Context context) {
		if(coolWeatherDaoImpl ==null){
			coolWeatherDaoImpl =new CoolWeatherDaoImpl(context);
		}
		return coolWeatherDaoImpl;
	}
	/**
	 * 保存中国省级数据到数据库
	 */
	@Override
	public void saveProvince(Province province) {
		if(province!=null){

			ContentValues values=new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("province", null, values);
		}
	}
	/**
	 * 从数据库中加载中国省级数据
	 */
	@Override
	public List<Province> loadProvince() {
		List<Province> list=new ArrayList<Province>();
		Cursor cursor=db.query("province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province=new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			}while(cursor.moveToNext());
		}
		return list;
	}
	/**
	 * 保存某省市级数据到数据库
	 */
	@Override
	public void saveCity(City city) {
		if(city!=null){
			ContentValues values=new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("city", null, values);
		}
	}
	/**
	 * 从数据库中加载某省市级数据
	 */
	@Override
	public List<City> loadCity(int provinceId) {
		List<City> list=new ArrayList<City>();
		Cursor cursor=db.query("city", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city=new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
				list.add(city);
			}while(cursor.moveToNext());
		}
		return list;
	}
	/**
	 * 保存某省市级数据到数据库
	 */
	@Override
	public void saveCounty(County county) {
		if(county!=null){
			ContentValues values=new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("county", null, values);
		}
	}
	/**
	 * 从数据库中加载某市县级数据
	 */
	@Override
	public List<County> loadCounty(int cityId) {
		List<County> list=new ArrayList<County>();
		Cursor cursor=db.query("county", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				County county=new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cityId);
				list.add(county);
			}while(cursor.moveToNext());
		}
		return list;
	}
}

