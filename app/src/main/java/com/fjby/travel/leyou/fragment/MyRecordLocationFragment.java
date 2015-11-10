package com.fjby.travel.leyou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.MyRecordActivity;
import com.fjby.travel.leyou.utils.ToastUtils;
import com.fjby.travel.leyou.widget.CleanableEditText;

public class MyRecordLocationFragment extends Fragment {
    private  TextView mRecordBack ;
    private   TextView mRecordSearch;
    private CleanableEditText mCleanableEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_location, container, false);
        ((MyRecordActivity)getActivity()).setToolbarShow(false);
        setHasOptionsMenu(true);
        mRecordBack = (TextView) view.findViewById(R.id.record_text_back);
        mRecordSearch = (TextView) view.findViewById(R.id.record_text_search);
        mCleanableEditText=(CleanableEditText) view.findViewById(R.id.record_location_editext);

        mCleanableEditText.setTextEmptyListener(new CleanableEditText.TextEmptyListener() {
            @Override
            public void onTextEmptyListener() {
                mRecordBack.setVisibility(View.VISIBLE);
                mRecordSearch.setVisibility(View.GONE);
            }

            @Override
            public void onTextlengthListener() {
                mRecordBack.setVisibility(View.GONE);
                mRecordSearch.setVisibility(View.VISIBLE);
            }
        });
        mRecordBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        mRecordSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(getActivity(),"搜索下次做",0);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_recoder_location, menu);
        MenuItem search=menu.findItem(R.id.menu_record_search);//自定义样式
        SearchView searchview=(SearchView) search.getActionView();
        searchview.setIconifiedByDefault(false);
        searchview.setQueryHint("搜索旅行地");
    }

}
