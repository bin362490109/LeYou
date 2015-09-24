package com.fjby.travel.leyou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.HomeLocationActivity;
import com.fjby.travel.leyou.activity.LoginActivity;
import com.fjby.travel.leyou.activity.HomeProduceActivity;
import com.fjby.travel.leyou.utils.IntentUtils;
import com.fjby.travel.leyou.widget.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private int mParam1;
    LinearLayout images_dots;
    private ImageButton mHomeAccoutIB;
    private TextView mHomeLocationTV;

    private AutoScrollViewPager image_viewpager;
    private List<ImageView> mListImageViews;
    private ImageView[] imageDots;
    private int currentIndex; // 当前图片顺序


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PageFragment.
     */
    public static HomeFragment newInstance(int param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        image_viewpager = (AutoScrollViewPager) view.findViewById(R.id.image_viewpager);
        images_dots = (LinearLayout) view.findViewById(R.id.images_dots);
        mHomeAccoutIB = (ImageButton) view.findViewById(R.id.home_account);
        mHomeAccoutIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.getInstance().startActivity(getActivity(), LoginActivity.class);
            }
        });
        mHomeLocationTV = (TextView) view.findViewById(R.id.home_location);
        initImageViews();
        // 5秒滚动变换一次
        image_viewpager.startAutoScroll(5000);
        image_viewpager.setInterval(5000);
        image_viewpager.setScrollDurationFactor(5);

        image_viewpager.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // Remove it here unless you want to get this callback
                        // for EVERY
                        // layout pass, which can get you into infinite loops if
                        // you ever
                        // modify the layout from within this method.
                        image_viewpager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        initImageViews();
                        initDots();

                    }
                });

        mHomeLocationTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.getInstance().startActivity(getActivity(), HomeLocationActivity.class);
            }
        });
        return view;
    }
    /**
     * 初始化图片点
     */
    private void initDots() {
        // dots数组
        imageDots = new ImageView[mListImageViews.size()];
        // 初始化dot，默认为非选定
        if (mListImageViews.size() > 1) {
            for (int i = 0; i < mListImageViews.size(); i++) {
                imageDots[i] = (ImageView) images_dots.getChildAt(i);
                if (i == 0) {
                    currentIndex = 0;
                    imageDots[i].setSelected(true);
                } else {
                    imageDots[i].setSelected(false);
                }
            }
        }
    }

    private void initImageViews() {
        mListImageViews = new ArrayList<>(3);
        for (int i = 0; i < 3; ++i) {
            final ImageView iv = new ImageView(getActivity());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(R.drawable.no_image);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtils.getInstance().startActivity(getActivity(), HomeProduceActivity.class);
                }
            });

            mListImageViews.add(iv);
        }
        image_viewpager.setAdapter(new ImageViewPagerAdapter());

        image_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                doCurrentDotChange(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    private void doCurrentDotChange(int position) {
        if (position < 0 || position == mListImageViews.size()
                || currentIndex == position) {
            return;
        }
        imageDots[position].setSelected(true);
        imageDots[currentIndex].setSelected(false);
        currentIndex = position;
    }


    private class ImageViewPagerAdapter extends PagerAdapter {
        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListImageViews.get(arg1));

        }
        @Override
        public int getCount() {
            // Set the total list item count
            if (mListImageViews != null)
                return mListImageViews.size();
            else
                return 0;
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
        @Override
        public Object instantiateItem(View collection, int position) {
            ((ViewPager) collection).addView(mListImageViews.get(position), 0);
            return mListImageViews.get(position);
        }
    }




}
