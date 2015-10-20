package com.fjby.travel.leyou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.InfoMessageActivity;
import com.fjby.travel.leyou.activity.InfoPushActivity;
import com.fjby.travel.leyou.adapter.MyRecyclerViewAdapter;
import com.fjby.travel.leyou.utils.IntentUtils;
import com.fjby.travel.leyou.utils.SnackbarUtil;
import com.fjby.travel.leyou.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private int mParam1;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private String[] mDataset;
    private String[] mTitle={"AA线路旅游团","导游私聊","旅游景区","旅游监管机构"};
    private String[] mBody={"AA线路旅游团群聊","聊天内容显示","旅游景区推送消息，旅游景区推送消息，旅游景区推送消息","旅游监管机构，旅游监管机构"};
    private List<String []> mDatasets;
    private MyRecyclerViewAdapter mAdapter;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PageFragment.
     */
    public static InfoFragment newInstance(int param1) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
        mDatasets=new ArrayList<>();
        for (int i=0;i<4;i++){
            mDataset=new String[4];
            mDataset[0]=mTitle[i];
            mDataset[1]=mBody[i];
            if(i/2==0){
                mDataset[2]="2015/8/29";
            }else{
                mDataset[2]="6:30";
            }

            mDataset[3]=i+"";
            mDatasets.add(mDataset);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.info_recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter=new MyRecyclerViewAdapter(getActivity(),mDatasets);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int i = Integer.parseInt(mDatasets.get(position)[3]);
                if (i < 2) {
                    IntentUtils.getInstance().startActivity(getActivity(), InfoMessageActivity.class);
                } else {
                    IntentUtils.getInstance().startActivity(getActivity(), InfoPushActivity.class);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                SnackbarUtil.show(view, mDatasets.get(position)[3], 1);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

}
