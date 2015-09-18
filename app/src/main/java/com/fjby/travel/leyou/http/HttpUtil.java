package com.fjby.travel.leyou.http;

import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.application.MyVolley;
import com.fjby.travel.leyou.utils.LogUtil;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
    public final static String SRV_URL = "http://192.168.0.52:8080/tour/";
    public final static String API_URL = "http://192.168.0.52:8080/tour/SaApi";


    /**
     * 普通请求网络
     */
    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null) {
                        //回调ononFinish函数
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        //回调onError函数
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * volley请求网络
     */
    public static void sendVolleyRequestToString(final HashMap<String, String> map, final HttpCallbackListener listener) {
                VolleyRequestByPost(map, listener);
    }

    //POST表单提交

    private static void VolleyRequestByPost(final HashMap<String, String> map, final HttpCallbackListener listener) {
        StringRequest request = new StringRequest(Request.Method.POST, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onFinish(response);
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
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Charset", "UTF-8");
                headers.put("Accept-Encoding", "gzip,deflate");
                return headers;
            }
        };
        MyVolley.addRequest(request);
    }
    //POST表单提交

    public static void VolleyRequestByString( final HttpCallbackListener listener) {
        StringRequest request = new StringRequest(Request.Method.POST, API_URL+"?req=login&name=333&pass=222", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogUtil.e("onResponse===============" + response);
                listener.onFinish(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        }) ;
        MyVolley.addRequest(request);
    }
}
