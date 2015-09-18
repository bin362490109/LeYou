package com.fjby.travel.leyou.volley;


import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.fjby.travel.leyou.utils.LogUtil;

/**
 * LruCache缓存管理类，该类实现了ImageCache接口，并实现了LruCache
 * 一旦bitmap对象从LruCache中被挤出，将会被放置在BitmapSoftRefCache中，再配合该框架本身支持的硬盘缓存，可以完成图片三级缓存
 * <p/>
 * BitmapLruCache.java
 *
 * @author zimo2013
 * @see http://blog.csdn.net/zimo2013
 */
public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageCache {

    private BitmapSoftRefCache softRefCache;

    public BitmapLruCache(int maxSize) {
        super(maxSize);
        softRefCache = new BitmapSoftRefCache();
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        if (evicted) {
            LogUtil.i("空间已满，缓存图片被挤出:" + key);
            // 将被挤出的bitmap对象，添加至软引用BitmapSoftRefCache
            softRefCache.putBitmap(key, oldValue);
        }
    }

    /**
     * 得到缓存对象
     */
    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bitmap = get(url);
        // 如果bitmap为null，尝试从软引用缓存中查找
        if (bitmap == null) {
            bitmap = softRefCache.getBitmap(url);
        } else {
            LogUtil.i("LruCache命中：" + url);
        }
        return bitmap;
    }

    /**
     * 添加缓存对象
     */
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

}
