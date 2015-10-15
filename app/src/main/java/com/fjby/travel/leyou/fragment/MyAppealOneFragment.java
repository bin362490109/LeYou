package com.fjby.travel.leyou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.MyAppealActivity;

public class MyAppealOneFragment extends Fragment {
    TextView mTextView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

     View view = inflater.inflate(R.layout.fragment_myappeal_one, container, false);
        mTextView=(TextView)view.findViewById(R.id.myappeal_more);
        ((MyAppealActivity)getActivity()).setToolbarTitle(R.string.myappeal_title);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.myappeal_framlayout, new MyAppealTwoFragment()).addToBackStack("tag").commit();
            }
        });
        return view;
    }

}
