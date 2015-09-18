package com.fjby.travel.leyou.db.dao;


import com.fjby.travel.leyou.db.bean.City;
import com.fjby.travel.leyou.db.bean.County;
import com.fjby.travel.leyou.db.bean.Province;

import java.util.List;

/**
 * Created by abin on 2015/9/9.
 */
public interface CoolWeatherDao {
    /**
     * 保存中国省级数据到数据库
     */
    public void saveProvince(Province province);
    /**
     * 从数据库中加载中国省级数据
     */
    public List<Province> loadProvince();
    /**
     * 保存某省市级数据到数据库
     */
    public void saveCity(City city);
    /**
     * 从数据库中加载某省市级数据
     */
    public List<City> loadCity(int provinceId);
    /**
     * 保存某省市级数据到数据库
     */
    public void saveCounty(County county);
    /**
     * 从数据库中加载某市县级数据
     */
    public List<County> loadCounty(int cityId);

}
