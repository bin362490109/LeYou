package com.fjby.travel.leyou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.MyAppealActivity;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.StringUtils;
import com.fjby.travel.leyou.utils.ToastUtils;

public class MyAppealTwoFragment extends Fragment {
    ImageButton mImageButton;
    MyAppealActivity myAppealActivity;
    EditText titleEditText;
    EditText contentEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.fragment_myappeal_two, container, false);
        setHasOptionsMenu(true);
        titleEditText= (EditText) view.findViewById(R.id.myappeal_title);
        contentEditText= (EditText) view.findViewById(R.id.myappeal_content);
        myAppealActivity=(MyAppealActivity)getActivity();
        myAppealActivity.setToolbarTitle(R.string.myappeal_more_title);
        if(!StringUtils.isEmpty(myAppealActivity.titile)){
            titleEditText.setText(myAppealActivity.titile);
        }
        if(!StringUtils.isEmpty(myAppealActivity.content)){
            contentEditText.setText(myAppealActivity.content);
        }
        LogUtil.e("MyAppealTwoFragment");
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_appeal, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_appeal:
                myAppealActivity.titile=titleEditText.getText().toString().trim();
                myAppealActivity.content=contentEditText.getText().toString().trim();
                getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
