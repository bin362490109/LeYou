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
import com.fjby.travel.leyou.utils.ToastUtils;

/**
 * Created by abin on 2015/9/17.
 */
public class NotesLocationActivity extends BaseActivity {
    private String[] itemNames;
    private TextView mNotesSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_location);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(NotesLocationActivity.this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ToastUtils.showShort(NotesLocationActivity.this, itemNames[position].toString());
            }
        });

         mNotesSearch=(TextView)findViewById(R.id.notes_text_search);
        mNotesSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater inflater;

        private class GridHolder {
            TextView gridviewTV;
        }

        public ImageAdapter(Context c) {

            mContext = c;
            itemNames = getResources().getStringArray(R.array.hot_search);
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
            if (convertView == null) {
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

    public void navFinish(View view) {
        finish();
    }
}
