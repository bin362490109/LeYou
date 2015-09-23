package com.fjby.travel.leyou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fjby.travel.leyou.R;

public class ProduceDetailFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

  /*      View view = inflater.inflate(R.layout.fragment_production_instruct, container, false);
        return view;*/
        return inflater.inflate(R.layout.fragment_production_detail, container, false);
    }


}
