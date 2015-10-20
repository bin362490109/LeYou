package com.fjby.travel.leyou.http;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.fjby.travel.leyou.utils.LogUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class GZipRequest extends StringRequest {
    public GZipRequest(int paramInt, String paramString,
                       Response.Listener<String> paramListener,
                       Response.ErrorListener paramErrorListener) {
        super(paramInt, paramString, paramListener, paramErrorListener);
    }

    public GZipRequest(String paramString,
                       Response.Listener<String> paramListener,
                       Response.ErrorListener paramErrorListener) {
        super(paramString, paramListener, paramErrorListener);
    }

    @Override
    protected void deliverResponse(String response) {
        super.deliverResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(
            NetworkResponse paramNetworkResponse) {
        LogUtil.e("paramNetworkResponse======"+paramNetworkResponse.headers.toString());
        try {
        String aa=getRealString(paramNetworkResponse.data);
            return Response.success(aa.toString(),HttpHeaderParser.parseCacheHeaders(paramNetworkResponse));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    private int getShort(byte[] data)
    {
        return (int) ((data[0] << 8) | data[1] & 0xFF);
    }

    private String getRealString(byte[] data)
    {
        byte[] h = new byte[2];
        h[0] = (data)[0];
        h[1] = (data)[1];
        int head = getShort(h);
        boolean t = head == 0x1f8b;
        InputStream in;
        StringBuilder sb = new StringBuilder();
        try
        {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            if (t)
            {
                in = new GZIPInputStream(bis);
            }
            else
            {
                in = bis;
            }
            BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
            for (String line = r.readLine(); line != null; line = r.readLine())
            {
                sb.append(line);
            }
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }

}