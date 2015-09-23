package com.fjby.travel.leyou.adapter;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fjby.travel.leyou.R;

/**
 * 适配器
 * @author Administrator
 *
 */
public class ListViewAdapter extends BaseAdapter{

    private LayoutInflater inflater;

    private ArrayList<String> list;



    public ListViewAdapter(Context context, ArrayList<String> list) {
        super();
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.popu_guide_listview, null);
        }
        TextView tv = (TextView)convertView.findViewById(R.id.popu_list_text);
        tv.setText(list.get(position));

        return convertView;
    }

}