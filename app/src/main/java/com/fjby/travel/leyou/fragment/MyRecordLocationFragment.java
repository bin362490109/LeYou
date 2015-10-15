package com.fjby.travel.leyou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.MyRecordActivity;

public class MyRecordLocationFragment extends Fragment {
    private TextView mTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_location, container, false);
        ((MyRecordActivity)getActivity()).setToolbarShow(false);
        setHasOptionsMenu(true);

        TextView  mNotesSearch = (TextView) view.findViewById(R.id.record_text_search);
        mNotesSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        ImageButton mImageButton = (ImageButton) view.findViewById(R.id.record_nav_finish);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
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
