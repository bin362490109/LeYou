package com.fjby.travel.leyou.application;

import android.app.ActivityManager;
import android.content.Context;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.volley.BitmapCache;
import com.fjby.travel.leyou.volley.BitmapLruCache;


/**
 * MyVolley.java
 * @see http://blog.csdn.net/zimo2013
 * @author zimo2013
 *
 */
public class MyVolley {
	private static final String TAG = "MyVolley";
	private static MyVolley instance;
	private static RequestQueue mRequestQueue;
	private static ImageLoader mImageLoader;
	private final static int RATE = 10; // 默认分配最大空间的几分之一

	private MyVolley(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
		// 确定在LruCache中，分配缓存空间大小,默认程序分配最大空间的 1/8
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		int maxSize = manager.getMemoryClass() / RATE; // 比如 64M/8,单位为M
		// BitmapLruCache自定义缓存class，android本身支持二级缓存，在BitmapLruCache封装一个软引用缓存
		mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(1024 * 1024 * maxSize));
		//mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache(context));
		LogUtil.v("MyVolley初始化完成");
	}

	/**
	 * 初始化Volley相关对象，在使用Volley前应该完成初始化
	 *
	 * @param context
	 */
	public static void init(Context context) {
		if (instance == null) {
			instance = new MyVolley(context);
		}
	}

	/**
	 * 得到请求队列对象
	 *
	 * @return
	 */
	private static RequestQueue getRequestQueue() {
		throwIfNotInit();
		return mRequestQueue;
	}

	public static void addRequest(Request<?> request) {
		getRequestQueue().add(request);
	}

	/**
	 * 得到ImageLoader对象
	 *
	 * @return
	 */
	private static ImageLoader getImageLoader() {
		throwIfNotInit();
		return mImageLoader;
	}

	public static void getImage(Context ct,String requestUrl, ImageView imageView) {
		getImage(ct,requestUrl, imageView, 0, 0);
	}

	public static void getImage(Context ct,String requestUrl, ImageView imageView,int defaultImageResId, int errorImageResId) {
		getImage(ct,requestUrl, imageView, defaultImageResId, errorImageResId, 0,0);
	}

	public static void getImage(Context ct,String requestUrl, ImageView imageView,int defaultImageResId, int errorImageResId, int maxWidth,int maxHeight) {
		imageView.setTag(requestUrl);
	    getImageLoader().get(ct,requestUrl, ImageLoader.getImageListener(imageView, defaultImageResId, errorImageResId), maxWidth, maxHeight);
	}


	/**
	 * 检查是否完成初始化
	 */
	private static void throwIfNotInit() {
		if (instance == null) {// 尚未初始化
			throw new IllegalStateException("MyVolley尚未初始化，在使用前应该执行init()");
		}
	}
}
