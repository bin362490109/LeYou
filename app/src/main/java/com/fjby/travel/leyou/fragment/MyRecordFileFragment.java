package com.fjby.travel.leyou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fjby.travel.leyou.R;

public class MyRecordFileFragment extends Fragment {
    TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_file, container, false);
        TextView mToolbarTitle = (TextView) getActivity().findViewById(R.id.toolbar_title);
        Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.VISIBLE);
        mToolbar.setNavigationIcon(R.drawable.nav_cancel_selector);
        setHasOptionsMenu(true);
        mToolbarTitle.setText(R.string.myrecordfile_title);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_ok, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_ok:
                getFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }
}
