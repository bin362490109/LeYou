package com.fjby.travel.leyou.http;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.ext.tools.BitmapTools;
import com.android.volley.toolbox.StringRequest;
import com.fjby.travel.leyou.application.LeYouMyApplication;
import com.fjby.travel.leyou.application.MyVolley;
import com.fjby.travel.leyou.utils.DialogUtils;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.NetworkUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpUtil {
    public final static String SRV_URL = "http://192.168.0.52:8080/tour/";
    public final static String API_URL = "http://192.168.0.10:7009/tour/tourApi";
    public final static String UPLOAD_URL = "http://192.168.0.10:7009/tour/UploadApi";
    //   public final static String API_URL = "http://192.168.0.102:8080/tour/tourApi";
    // public final static String UPLOAD_URL = "http://192.168.0.102:8080/tour/UploadApi";


/*   public static void sendVolleyRequestToString(final HashMap<String, String> map, final HttpCallbackListener listener) {
        VolleyRequestByPost(map, listener);
    }*/

    //POST表单提交
    private static void VolleyRequestByPost(final HashMap<String, String> map, final HttpCallbackListener listener) {
        StringRequest request = new StringRequest(Request.Method.POST, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onFinish(response);
                LogUtil.e("response===" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        }) {
            // 携带参数
            @Override
            protected HashMap<String, String> getParams() throws AuthFailureError {
                if (LeYouMyApplication.mUser != null) {
                    map.put("hhid", LeYouMyApplication.mCashHhid);
                    map.put("dlid", LeYouMyApplication.mUser.getGuid());
                } else {
                    map.put("hhid", LeYouMyApplication.mCashHhid);
                }
                map.put("check", "");
                map.put("ver", LeYouMyApplication.versionName);
                LogUtil.e("map==" + map.toString() + "     " + LeYouMyApplication.mCashHhid);
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Charset", "UTF-8");
                headers.put("Content-Type", "application/x-javascript");
                headers.put("Accept-Encoding", "gzip,deflate");
                return headers;
            }

            @Override
            public RetryPolicy getRetryPolicy() {
                RetryPolicy retryPolicy = new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                return retryPolicy;
            }
        };
        MyVolley.addRequest(request);
    }

    /**
     * volley请求网络
     */
    public static void sendVolleyRequesttoParam(final HashMap<String, String> map, final HttpCallbackListener listener) {
        if (LeYouMyApplication.mUser != null) {
            map.put("guid", LeYouMyApplication.mUser.getGuid());
            map.put("verifycode", LeYouMyApplication.mUser.getVerifyCode());
        } else {
            map.put("guid", LeYouMyApplication.mCashHhid);
            map.put("verifycode", "-1");
        }
        map.put("check", "");
        map.put("ver", LeYouMyApplication.versionName);
        Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
        Map.Entry<String, String> entry;
        StringBuffer input = new StringBuffer();
        input.append(API_URL + "?");
        while (iter.hasNext()) {
            entry = iter.next();
            String key = entry.getKey();
            String value = entry.getValue();
            try {
                input.append(key + "=" + URLEncoder.encode(value, "UTF-8") + "&");
            } catch (UnsupportedEncodingException e) {
                input.append("");
            }
        }
        input.deleteCharAt(input.length() - 1);
        LogUtil.e("http  input  is=" + input.toString());
        VolleyRequestByParam(input.toString(), listener);
    }

    //POST表单提交
    private static void VolleyRequestByParam(String input, final HttpCallbackListener listener) {
        StringRequest request = new StringRequest(Request.Method.POST,input, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onFinish(response);
                LogUtil.e("response===" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //     listener.onError(error);
                LogUtil.e("VolleyRequestByParam"+error);
                if (NetworkUtils.isNetworkConnected(LeYouMyApplication.currentActivity())) {
                    DialogUtils.mdialogShowNoFinish(LeYouMyApplication.currentActivity(), "网络错误", "服务器正在更新，或则咨询客服人员");
                } else {
                    DialogUtils.mdialogShowNoFinish(LeYouMyApplication.currentActivity(), "网络错误", "请检查你的网络问题");
                }
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //请用缓存
        request.setShouldCache(true);
        //设置缓存时间10分钟
        //// TODO: 2015/11/16  
       // request.setCacheTime(10*60);
        MyVolley.addRequest(request);
    }

    public static void testImageLoad(Context ct, String url, ImageView imageView, int defaultImageResId, int errorImageResId) {
        MyVolley.getImage(ct,url, imageView, defaultImageResId, errorImageResId,imageView.getWidth(),imageView.getHeight());
    }

    public static void testImageLoad(Context ct,String url, ImageView imageView, int defaultImageResId, int errorImageResId, int maxWidth, int maxHeight) {
        LogUtil.e("http  url  is=" + url.toString());
        MyVolley.getImage(ct,url, imageView, defaultImageResId, errorImageResId, maxWidth, maxHeight);
    }

}
