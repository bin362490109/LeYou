package com.fjby.travel.leyou.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.application.LeYouMyApplication;
import com.fjby.travel.leyou.http.HttpCallbackListener;
import com.fjby.travel.leyou.http.HttpUtil;
import com.fjby.travel.leyou.pojo.ResGetCodeK;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by abin on 2015/9/17.
 */
public class HomeLocationActivity extends BaseActivity{
    private String [] itemNames;
    private  GridView gridView;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_home_location);
        initToolbar(true, true);
        setToolbarTitle(R.string.city_location);
        setToolbarNavigationIcon(R.drawable.nav_cancel_selector);
    }

    @Override
    protected void initView() {
         gridView = (GridView) findViewById(R.id.gridView);
    }

    @Override
    protected void setListener() {
        gridView.setAdapter(new ImageAdapter(HomeLocationActivity.this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                ToastUtils.showShort(HomeLocationActivity.this, itemNames[position].toString());
            }
        });

    }
    @Override
    protected void doOther() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("req", "GetCodeK");
        map.put("guid", LeYouMyApplication.mCashHhid);
        HttpUtil.sendVolleyRequesttoParam(map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Gson gson = new Gson();
                ResGetCodeK resGetCodeK=gson.fromJson(response,ResGetCodeK.class);
                LogUtil.e(response);
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }


    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater inflater;

        private class GridHolder {
            TextView gridviewTV;
        }

        public ImageAdapter(Context c){

            mContext = c;
            itemNames=getResources().getStringArray(R.array.provarray);
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return itemNames.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridHolder holder;
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.adapter_gridview_text, null);
                holder = new GridHolder();
                holder.gridviewTV = (TextView) convertView.findViewById(R.id.gridView_text);
                convertView.setTag(holder);
            } else {
                holder = (GridHolder) convertView.getTag();
            }
            holder.gridviewTV.setText(itemNames[position]);
            return convertView;
        }

    }
}
