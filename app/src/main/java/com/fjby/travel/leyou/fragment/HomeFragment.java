package com.fjby.travel.leyou.fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.HomeLocationActivity;
import com.fjby.travel.leyou.activity.HomeProduceActivity;
import com.fjby.travel.leyou.activity.HomeTourActivity;
import com.fjby.travel.leyou.activity.MainActivity;
import com.fjby.travel.leyou.application.LeYouMyApplication;
import com.fjby.travel.leyou.http.HttpCallbackListener;
import com.fjby.travel.leyou.http.HttpUtil;
import com.fjby.travel.leyou.pojo.CityAd;
import com.fjby.travel.leyou.pojo.ResTourGroupData;
import com.fjby.travel.leyou.pojo.Tourist;
import com.fjby.travel.leyou.utils.DateUtil;
import com.fjby.travel.leyou.utils.IntentUtils;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.ToastUtils;
import com.fjby.travel.leyou.widget.AutoScrollViewPager;
import com.fjby.travel.leyou.xlistview.XListView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment implements XListView.IXListViewListener,View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private int mParam1;
    private LinearLayout images_dots;
    private ImageView iamgeview1;
    private ImageView iamgeview2;
    private ImageView iamgeview3;
    private ImageView iamgeview4;
    private LinearLayout homeTravel;
    private ImageView homeTravelImage;
    private XListView mListView;
    private ImageButton mHomeAccoutIB;
    private TextView mHomeLocationTV;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    private AutoScrollViewPager image_viewpager;
    private DrawerLayout mDrawerLayout;

    private ArrayAdapter<MessageItem> mAdapter;
    private List<MessageItem> mMessageItems = new ArrayList<MessageItem>();
    private Handler mHandler;
    private int mSelectedRow = 0;
    //// TODO: 2015/10/20   如果不放地址以后可以删除  写死
    private List<ImageView> mListImageViews;
    private ImageView[] imageDots;
    private int currentIndex; // 当前图片顺序
    private boolean mIsNoMore = false;
    private boolean mIsListVieIdle = true;
    // private int[] mImages ;
    private CityAd[] cityAds;
    private Tourist[] tourists;
    private ResTourGroupData resTourGroupData;

    public static HomeFragment newInstance(int param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
        geneCityAd();
        geneHot();
        geneItems();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mDrawerLayout = ((MainActivity) getActivity()).mDrawerLayout;
        mHandler = new Handler();
        mListView = (XListView) view.findViewById(R.id.xListView);
        mListView.setPullLoadEnable(true);
        mAdapter = new CustomAdapter(mMessageItems);

        View head = inflater.inflate(R.layout.adapter_home_head, null);
        iniHeadView(head);
        mListView.addHeaderView(head);

        View hot = inflater.inflate(R.layout.adapter_home_hot, null);
        iniHotView(hot);
        mListView.addHeaderView(hot);
        mListView.setAdapter(mAdapter);

        initLinsten();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                //这个还有下面的对话框position都要减去1  为咋的   我怀疑是上面的刷新按钮惹得祸
                mSelectedRow = position - mListView.getHeaderViewsCount();
                ;
                if (mSelectedRow >= 0) {
                    LogUtil.v("onItemClick=" + mAdapter.getItem(mSelectedRow).toString());
                    IntentUtils.getInstance().startActivity(getActivity(), HomeProduceActivity.class);
                }
            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    mIsListVieIdle = false;
                } else {
                    mIsListVieIdle = true;//list停止滚动时加载图片
                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        //	mListView.setPullRefreshEnable(false);  //默认为真
        mListView.setXListViewListener(this);

        //判断旅行进行时是否需要存在
        if (LeYouMyApplication.mUser != null) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("sts", "0");
            map.put("req", "GetTourGroupData");
            HttpUtil.sendVolleyRequesttoParam(map, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Gson gson = new Gson();
                    resTourGroupData = gson.fromJson(response, ResTourGroupData.class);
                    if (resTourGroupData.getStateCode() == 600) {
                        LogUtil.e("--------------------" +resTourGroupData.getTourGroup().toString());
                        if (resTourGroupData.getTourGroup().getGuid()!=null) {
                            homeTravel.setVisibility(View.VISIBLE);
                            HttpUtil.testImageLoad(getActivity(), "", homeTravelImage, R.drawable.no_image, R.drawable.no_image);
                        }
                    } else {
                        ToastUtils.showLong(getActivity(), resTourGroupData.getStateMsg());
                    }
                }
                @Override
                public void onError(Exception e) {
                }
            });
        }

        return view;
    }

    private void iniHeadView(View view) {
        image_viewpager = (AutoScrollViewPager) view.findViewById(R.id.image_viewpager);
        images_dots = (LinearLayout) view.findViewById(R.id.images_dots);
        mHomeAccoutIB = (ImageButton) view.findViewById(R.id.home_account);
        mHomeLocationTV = (TextView) view.findViewById(R.id.home_location);

        homeTravel = (LinearLayout) view.findViewById(R.id.home_traveling);
        homeTravelImage = (ImageView) view.findViewById(R.id.home_traveling_image);
        homeTravel.setOnClickListener(this);
        homeTravel.setVisibility(View.GONE);
    }


    private void iniHotView(View view) {
        iamgeview1 = (ImageView) view.findViewById(R.id.home_hot_image1);
        iamgeview2 = (ImageView) view.findViewById(R.id.home_hot_image2);
        iamgeview3 = (ImageView) view.findViewById(R.id.home_hot_image3);
        iamgeview4 = (ImageView) view.findViewById(R.id.home_hot_image4);
        HttpUtil.testImageLoad(getActivity(), tourists[0].getImgUrl(), iamgeview1, R.drawable.no_image, R.drawable.no_image);
        HttpUtil.testImageLoad(getActivity(), tourists[1].getImgUrl(), iamgeview2, R.drawable.no_image, R.drawable.no_image);
        HttpUtil.testImageLoad(getActivity(), tourists[2].getImgUrl(), iamgeview3, R.drawable.no_image, R.drawable.no_image);
        HttpUtil.testImageLoad(getActivity(), tourists[3].getImgUrl(), iamgeview4, R.drawable.no_image, R.drawable.no_image);
        iamgeview1.setOnClickListener(this);
        iamgeview2.setOnClickListener(this);
        iamgeview3.setOnClickListener(this);
        iamgeview4.setOnClickListener(this);
    }

    private void toggle() {
        if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void initLinsten() {
        mHomeAccoutIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        //  initImageViews();
        // 5秒滚动变换一次
        image_viewpager.startAutoScroll(5000);
        image_viewpager.setInterval(5000);
        image_viewpager.setScrollDurationFactor(5);
        image_viewpager.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            image_viewpager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }else{
                            image_viewpager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
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
            //  iv.setImageResource(mImages[i]);
            HttpUtil.testImageLoad(getActivity(), cityAds[i].getImgurl(), iv, R.drawable.no_image, R.drawable.no_image);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtils.getInstance().startActivity(getActivity(), HomeProduceActivity.class);
                }
            });
            mListImageViews.add(iv);
        }
        image_viewpager.setAdapter(new ImageViewPagerAdapter());
        image_viewpager.setOffscreenPageLimit(3);

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
        if (position < 0 || position == mListImageViews.size() || currentIndex == position) {
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

    private void geneCityAd() {
        int num = LeYouMyApplication.cityAdList.size();
        if (num >= 3) {
            cityAds = new CityAd[3];
            for (int i = 0; i < 3; i++) {
                cityAds[i] = LeYouMyApplication.cityAdList.get(i);
            }
        }
    }

    //初始化热门推荐数据（包括下面listview）
    private void geneHot() {
        int num = LeYouMyApplication.touristList.size();
        if (num >= 4) {
            tourists = new Tourist[num];
            for (int i = 0; i < LeYouMyApplication.touristList.size(); i++) {
                tourists[i] = LeYouMyApplication.touristList.get(i);
            }
        }
    }

    //初始化listview数据（一次10个）
    private void geneItems() {
        int total = tourists.length;
        int size = mMessageItems.size() + 3;
        mIsNoMore = false;
        for (int i = 0; i <= 10; ++i) {
            if (!mIsNoMore) {
                size++;
                if (size < total) {
                    MessageItem item = new MessageItem();
                    item.iconRes = tourists[size].getImgUrl();
                    item.title = tourists[size].getProductName();
                    item.account = tourists[size].getProductPrice();
                    item.reviews = tourists[size].getCommentCount();
                    item.like = tourists[size].getSatisfyValue();
                    mMessageItems.add(item);
                } else {
                    mIsNoMore = true;
                }
            }
        }
    }

    private void onLoad() {
        mAdapter.notifyDataSetChanged();
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(DateUtil.formatDateTime(new Date()));
        if (mIsNoMore) {
            mListView.noMore();
        }
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMessageItems.clear();
                geneItems();
                onLoad();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                geneItems();
                onLoad();
            }
        }, 2000);
    }


    private class CustomAdapter extends ArrayAdapter<MessageItem> {
        public CustomAdapter(List<MessageItem> mMessageItems) {
            super(getActivity(), 0, mMessageItems);
            // super 用0则是自定义
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mMessageItems.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.adapter_home_listview, null);
                holder = new ViewHolder();
                holder.icon = (ImageView) convertView.findViewById(R.id.home_listview_image);
                holder.title = (TextView) convertView.findViewById(R.id.home_listview_title);
                holder.account = (TextView) convertView.findViewById(R.id.home_listview_acount);
                holder.reviews = (TextView) convertView.findViewById(R.id.home_listview_review);
                holder.like = (TextView) convertView.findViewById(R.id.home_listview_like);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //    holder.icon.setImageResource(getItem(position).iconRes);
   /*         if(mIsListVieIdle) {
                HttpUtil.testImageLoad(getActivity(), getItem(position).iconRes, holder.icon, R.drawable.no_image, R.drawable.no_image);
            }else{
                holder.icon.setImageResource( R.drawable.no_image);
            }*/
            HttpUtil.testImageLoad(getActivity(), getItem(position).iconRes, holder.icon, R.drawable.no_image, R.drawable.no_image);
            holder.title.setText(getItem(position).title);
            holder.account.setText(getItem(position).account);
            holder.reviews.setText(getItem(position).reviews);
            holder.like.setText(getItem(position).like);
            return convertView;
        }


    }

    static class ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView account;
        public TextView reviews;
        public TextView like;
    }

    public class MessageItem {
        public String iconRes;
        public String title;
        public String account;
        public String reviews;
        public String like;

        @Override
        public String toString() {
            return "MessageItem{" +
                    "iconRes=" + iconRes +
                    ", title='" + title + '\'' +
                    ", account='" + account + '\'' +
                    ", reviews='" + reviews + '\'' +
                    ", like='" + like + '\'' +
                    '}';
        }
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.home_hot_image1 :
              IntentUtils.getInstance().startActivity(getActivity(), HomeProduceActivity.class);
              break;
          case R.id.home_hot_image2 :
              IntentUtils.getInstance().startActivity(getActivity(), HomeProduceActivity.class);
              break;
          case R.id.home_hot_image3 :
              IntentUtils.getInstance().startActivity(getActivity(), HomeProduceActivity.class);
              break;
          case R.id.home_hot_image4 :
              IntentUtils.getInstance().startActivity(getActivity(), HomeProduceActivity.class);
              break;
          case R.id.home_traveling :
              IntentUtils.getInstance().startActivity(getActivity(), HomeTourActivity.class);
              break;
          default: ;

      }
    }
}
