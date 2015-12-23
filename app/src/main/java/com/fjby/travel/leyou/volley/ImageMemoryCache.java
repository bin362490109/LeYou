package com.fjby.travel.leyou.volley;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.fjby.travel.leyou.utils.LogUtil;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

public class ImageMemoryCache {
    /**
     * 从内存读取数据速度是最快的，为了更大限度使用内存，这里使用了两层缓存。
     * 硬引用缓存不会轻易被回收，用来保存常用数据，不常用的转入软引用缓存。
     */
    private static final int SOFT_CACHE_SIZE = 15;  //软引用缓存容量
    private static LruCache<String, Bitmap> mLruCache;  //硬引用缓存
    private static LinkedHashMap<String, SoftReference<Bitmap>> mSoftCache;  //软引用缓存

    public ImageMemoryCache(Context context) {
        int memClass = ((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        int cacheSize = 1024 * 1024 * memClass / 10;  //硬引用缓存容量，为系统可用内存的1/10
        LogUtil.e("cacheSize===================="+cacheSize/1024/1024);
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                if (value != null){
                    //其实这里也可以用getByteCount()，就是计算出图片占用的容量，但是getByteCount()方法是从API Level 12开始
                    //一般为了兼容，所以采用value.getRowBytes() * value.getHeight()的方式，不过现在普遍手机版本都比较高，所以其实换掉也没事
                    return value.getRowBytes() * value.getHeight();}
                else
                    return 0;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                if (oldValue != null)
                    // 硬引用缓存容量满的时候，会根据LRU算法把最近没有被使用的图片转入此软引用缓存
                    LogUtil.e("-------------entryRemoved-----------------");
                mSoftCache.put(key, new SoftReference<Bitmap>(oldValue));
            }
        };

        //这里采用LinkedHashMap，在其构造器内的三个参数分别为：初始容量、加载因子、排序模式
        //初始容量，LinkedHashMap可以通过重写removeEldestEntry来控制内部存储的数量，但是HashMap则不行
        //加载因子，指空间的利用率，当设置的值高时比较省空间，但是会因此降低查询速度，反之也一样
        //排序方式，当为true时，按照访问顺序排序，最后访问的在第一个，当为false时，按照插入顺序排序。HashMap的是没有特定顺序的
        //在这里，我指定了按照访问顺序排序。
        mSoftCache = new LinkedHashMap<String, SoftReference<Bitmap>>(SOFT_CACHE_SIZE, 0.75f, true) {
            private static final long serialVersionUID = 6040103833179403725L;

            @Override
            protected boolean removeEldestEntry(
                    java.util.Map.Entry<String, SoftReference<Bitmap>> eldest) {
                // TODO Auto-generated method stub
          /*      if(Build.VERSION.SDK_INT<15){
                    if (size() > SOFT_CACHE_SIZE){
                        return true;
                    }
                    return false;
                }
                return super.removeEldestEntry(eldest);*/
                if (size() > SOFT_CACHE_SIZE){
                    return true;
                }
                return false;
            }
        };
    }

    /**
     * 从缓存中获取图片
     */
    public Bitmap getBitmapFromCache(String url) {
        Bitmap bitmap;
        //先从硬引用缓存中获取
        synchronized (mLruCache) {
            bitmap = mLruCache.get(url);
            if (bitmap != null) {
                //如果找到的话，把元素移到LinkedHashMap的最前面，从而保证在LRU算法中是最后被删除
                mLruCache.remove(url);
                mLruCache.put(url, bitmap);
                return bitmap;
            }
        }
        //如果硬引用缓存中找不到，到软引用缓存中找
        synchronized (mSoftCache) {
            SoftReference<Bitmap> bitmapReference = mSoftCache.get(url);
            if (bitmapReference != null) {
                bitmap = bitmapReference.get();
                if (bitmap != null) {
                    //将图片移回硬缓存
                    mLruCache.put(url, bitmap);
                    mSoftCache.remove(url);
                    return bitmap;
                } else {
                    mSoftCache.remove(url);
                }
            }
        }
        return null;
    }
    /**
     * 添加图片到缓存
     */
    public void addBitmapToCache(String url, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (mLruCache) {
                mLruCache.put(url, bitmap);
            }
        }
    }
    public void clearCache() {
        mSoftCache.clear();
    }
}