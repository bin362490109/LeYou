package com.fjby.travel.leyou.volley;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;
import com.fjby.travel.leyou.utils.LogUtil;

/**
 * 使用LruCache来缓存图片
 */
public class BitmapCache implements ImageLoader.ImageCache {
    private ImageFileCache fileCache;
    private ImageMemoryCache memoryCache;

    public BitmapCache(Context context) {
        memoryCache=new ImageMemoryCache(context);
        fileCache = new ImageFileCache();
    }

    @Override
    public Bitmap getBitmap(String url) {
        //	return mCache.get(url);
        // 从内存缓存中获取图片
        Bitmap result = memoryCache.getBitmapFromCache(url);
        if (result == null) {
            // 文件缓存中获取
            result = fileCache.getImage(url);
            if (result == null) {
                LogUtil.e( "getBitmap--------------网络获取-----------------------wwwww");
                return null;
            } else {
                // 添加到内存缓存
                memoryCache.addBitmapToCache(url, result);
                LogUtil.e(  "getBitmap-----------------文件中读取-------------------file");
            }
        }else{
            LogUtil.e( "getBitmap---------------内存中读取-------------------cach");
        }
        return result;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        //mCache.put(url, bitmap);
        LogUtil.e( "putBitmap-------------------------------------wwwww");
        fileCache.saveBitmap(bitmap, url);
        memoryCache.addBitmapToCache(url, bitmap);
    }

}
