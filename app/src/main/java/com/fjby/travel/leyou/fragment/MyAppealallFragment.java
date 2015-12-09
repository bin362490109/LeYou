package com.fjby.travel.leyou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.MyAppealActivity;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.StringUtils;

public class MyAppealAllFragment extends Fragment {
    ImageButton mImageButton;
    MyAppealActivity myAppealActivity;
    ListView listView;
    TextView empty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.fragment_myappeal_all, container, false);
        listView= (ListView) view.findViewById(R.id.listView);
        empty= (TextView) view.findViewById(android.R.id.empty);
        listView.setEmptyView(empty);// 设置清空以后显示的内容
       // listView.setAdapter(new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_list_item_1,getResources().getStringArray( R.array.myappeal_category)));

        myAppealActivity=(MyAppealActivity)getActivity();
        myAppealActivity.setToolbarTitle(R.string.myappeal_all);
        LogUtil.e("MyAppealallFragment");
        return view;
    }
}
