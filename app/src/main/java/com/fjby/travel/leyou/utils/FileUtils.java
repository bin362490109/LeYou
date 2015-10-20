package com.fjby.travel.leyou.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by abin on 2015/9/16.
 */
public class FileUtils {

    /**
     * 扩展卡根目录
     */
    public static String MAIN_PATH = "/sdcard/leyou/"; // 文件存储的主路径
    public static String IMG_PATH = "/sdcard/leyou/images/"; // 图片文件存储的主路径
    public static String CONF_PATH = "/sdcard/leyou/config/"; // 书籍等文件存储的主路径
    public static String DOWN_PATH = "/sdcard/leyou/download/"; // 书籍等文件存储的主路径
    public static String EDC_PATH="/sdcard/leyou/edc/";//文书存储的主路径
    public static final String CRASH_FILE_NAME = "ex.cr";
    public  static  void  CreateFileUrl(){
        // 本地扩展内存根目录
        File ext = Environment.getExternalStorageDirectory();
        MAIN_PATH=ext.getAbsolutePath()+"/leyou/";
        IMG_PATH=MAIN_PATH+"images/";
        CONF_PATH=MAIN_PATH+"config/";
        DOWN_PATH=MAIN_PATH+"download/";
        EDC_PATH=MAIN_PATH+"edc/";
        LogUtil.i("MAIN_PATH===="+ MAIN_PATH);
        //初始化目录
        File file = new File(MAIN_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(IMG_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(CONF_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(DOWN_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(EDC_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
