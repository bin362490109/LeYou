package com.fjby.travel.leyou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.a.a.a.b;
import com.baidu.platform.comapi.map.o;
import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.MyAppealActivity;
import com.fjby.travel.leyou.utils.LogUtil;

public class MyAppealOneFragment extends Fragment {
    public TextView mTextView;
    Button buttonsubmit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

     View view = inflater.inflate(R.layout.fragment_myappeal_one, container, false);
        mTextView=(TextView)view.findViewById(R.id.myappeal_more);
        buttonsubmit=(Button) view.findViewById(R.id.buttonsubmit);

        final Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        final MyAppealActivity myAppealActivity=(MyAppealActivity)getActivity();
        myAppealActivity.setToolbarTitle(R.string.myappeal_title);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAppealActivity.repalceFragmentWithTag(new MyAppealTwoFragment());
            }
        });

        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.startAnimation(shake);
            }
        });
      //  LogUtil.e("MyAppealOneFragment");

        return view;
    }

}
