package com.fjby.travel.leyou.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.utils.ToastUtils;

/**
 * Created by abin on 2015/9/17.
 */
public class NotesDetailReviewsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_detail_reviews);
        initToolbar(true, true);
        setToolbarTitle(R.string.notes_reviews_title);
    }
}
