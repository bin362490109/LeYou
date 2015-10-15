package com.fjby.travel.leyou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.MyRecordActivity;

public class MyRecordRelationFragment extends Fragment {
    private TextView mTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_relation, container, false);
        MyRecordActivity myParentActivity=(MyRecordActivity)getActivity();
        myParentActivity.setToolbarShow(true);
        myParentActivity.setToolbarNavigationIcon(R.drawable.nav_cancel_selector);
        myParentActivity.setToolbarTitle(R.string.myrecordRelation_title);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_more, menu);
    }

}
