package com.fjby.travel.leyou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.MyRecordActivity;

public class MyRecordMainFragment extends Fragment {
    private TextView mTextView;
    private TextView mRecordLocationText;
    private ImageButton mRecordTabFfile;
    private ImageButton mRecordTabCamera;
    private ImageButton mRecordTabLoCation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_main, container, false);
        mRecordTabLoCation = (ImageButton) view.findViewById(R.id.record_tab_location);
        mRecordTabLoCation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.myappeal_framlayout, new MyRecordLocationFragment()).addToBackStack("tag").commit();
            }
        });
        mRecordTabCamera = (ImageButton) view.findViewById(R.id.record_tab_camera);
        mRecordTabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRecordTabFfile = (ImageButton) view.findViewById(R.id.record_tab_file);
        mRecordTabFfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.myappeal_framlayout, new MyRecordFileFragment()).addToBackStack("tag").commit();
            }
        });
        mRecordLocationText = (TextView) view.findViewById(R.id.record_location_text);
        mRecordLocationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.myappeal_framlayout, new MyRecordRelationFragment()).addToBackStack("tag").commit();
            }
        });

        MyRecordActivity myParentActivity=(MyRecordActivity)getActivity();
        myParentActivity.setToolbarShow(true);
        myParentActivity.setToolbarNavigationIcon(R.drawable.nav_cancel_selector);
        myParentActivity.setToolbarTitle(R.string.myrecord_title);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_more, menu);
    }

}
