package com.fjby.travel.leyou.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.utils.ToastUtils;
import com.fjby.travel.leyou.widget.GrapeGridview;
import com.fjby.travel.leyou.widget.VerticalImageSpan;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private int mParam1;
    private TextView mTextView;
    private  int[] mIamges={R.drawable.main_1,R.drawable.main_2,R.drawable.main_3,R.drawable.main_1,R.drawable.main_2,R.drawable.main_3,R.drawable.main_2,R.drawable.main_3};
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PageFragment.
     */
    public static NewsFragment newInstance(int param1) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_news, container, false);
        mTextView= (TextView) v.findViewById(R.id.news_text);
        String s1=getResources().getText(R.string.notes_2).toString();
        String s2=getResources().getText(R.string.notes_3).toString();
        SpannableStringBuilder ss = new SpannableStringBuilder(s1+s2);
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ToastUtils.showLong(getActivity(), "X你点击了该链接");
            }
        } ;
        ss.setSpan(clickableSpan, s1.length(),s1.length()+s2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        VerticalImageSpan span = new VerticalImageSpan(getActivity(),R.drawable.link);
        ss.setSpan(span, s1.length(), s1.length()+1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTextView.setText(ss);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());

        GrapeGridview gridView = (GrapeGridview) v.findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(getActivity()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ToastUtils.showShort(getActivity(), "第【 "+(position+1)+" 】幅图");
            }
        });
        return v;
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater inflater;

        private class GridHolder {
            ImageView gridviewTV;
        }

        public ImageAdapter(Context c) {

            mContext = c;
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mIamges.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final GridHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.adapter_gridview_image, null);
                holder = new GridHolder();
                holder.gridviewTV = (ImageView) convertView.findViewById(R.id.gridView_image);
                convertView.setTag(holder);
            } else {
                holder = (GridHolder) convertView.getTag();
            }
                            holder.gridviewTV.setBackgroundResource(mIamges[position]);
            return convertView;
        }

    }
    
}
