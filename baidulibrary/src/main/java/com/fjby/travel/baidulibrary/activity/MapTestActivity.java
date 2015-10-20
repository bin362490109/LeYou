package com.fjby.travel.baidulibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.share.LocationShareURLOption;
import com.baidu.mapapi.search.share.OnGetShareUrlResultListener;
import com.baidu.mapapi.search.share.ShareUrlResult;
import com.baidu.mapapi.search.share.ShareUrlSearch;
import com.fjby.travel.baidulibrary.R;
import com.fjby.travel.baidulibrary.util.MyOrientationListener;

/**
 * 此demo用来展示如何结合定位SDK实现定位，并使用MyLocationOverlay绘制定位位置 同时展示如何使用自定义图标绘制并点击时弹出泡泡
 * 
 */
public class MapTestActivity extends Activity implements OnGetGeoCoderResultListener,OnGetShareUrlResultListener {
	
	//定位图片  默认为null
    private BitmapDescriptor mCurrentMarker;
    private BitmapDescriptor bd;
    private Marker mMarker = null;
    
    private MyOrientationListener myOrientationListener;
	private Button MRequestLocButton;
	private Button mShareButton;
	private TextView mAdressTextView;
	private LinearLayout mShrteLayout;
	
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	// 定位相关
	private LocationClient mLocClient;

	// UI相关 可以隐藏指南针
	private UiSettings mUiSettings;
	private GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	private ShareUrlSearch mShareUrlSearch = null;
	
	
	
	//当前模式
	private LocationMode mCurrentMode;
	private float sensorValue;
	private boolean isFirstLoc = true;// 是否首次定位


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);

		initDatas();
		initViews();

		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(new MyLocationListenner());

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);//
		// option.setScanSpan(3000);
		mLocClient.setLocOption(option);
		mLocClient.start();

	
	}

	private void initDatas() {
		// 地图模式
		mCurrentMode = LocationMode.NORMAL;
		sensorValue = 100.0f;
		mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
		bd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
	}

	private void initViews() {
		mShrteLayout=(LinearLayout)findViewById(R.id.shareLayout);
		mAdressTextView=(TextView)findViewById(R.id.adressText);
		mShareButton=(Button)findViewById(R.id.shareButton);
		mShareButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mShareUrlSearch.requestLocationShareUrl(new LocationShareURLOption().location(mMarker.getPosition()).snippet("测试分享点").name(mMarker.getTitle()));
				Log.v("crb", "onMarkerClick");
			}
		});
		mShrteLayout.setVisibility(View.INVISIBLE);
		// 模式按钮
		MRequestLocButton = (Button) findViewById(R.id.button1);
		MRequestLocButton.setText("普通");
		MRequestLocButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mShrteLayout.setVisibility(View.INVISIBLE);
				mBaiduMap.clear();
				switch (mCurrentMode) {
				case NORMAL:
					setLocationMode(LocationMode.COMPASS);
					break;
				default:
					setLocationMode(LocationMode.NORMAL);
					
					break;
				}
			}
		});

		// 单选按钮
		RadioGroup group = (RadioGroup) this.findViewById(R.id.radioGroup);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.defaulticon) {
					mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
				}
				if (checkedId == R.id.customicon) {
					// 修改为自定义marker
					mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
				}
			}
		});

		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);

		// 分享模块监听
		mShareUrlSearch = ShareUrlSearch.newInstance();
		mShareUrlSearch.setOnGetShareUrlResultListener(this);

		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				return true;
			}
		});
		
		// ui控制
		mUiSettings = mBaiduMap.getUiSettings();
		
		myOrientationListener = new MyOrientationListener(MapTestActivity.this);
		myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
					@Override
					public void onOrientationChanged(float x) {
						// TODO Auto-generated method stub
						if (mCurrentMode == LocationMode.COMPASS) {
							sensorValue = x;
							mLocClient.requestLocation();
						} else {
							sensorValue = 100.0f;
						}
					}
				});
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;

			setMyLocationData(location);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng currenLatlng = new LatLng(location.getLatitude(),location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(currenLatlng, 18);
				mBaiduMap.animateMapStatus(u);
			}
			//List<PoiInfo> list = location.getPoiList();
			Log.v("crb","onReceiveLocation    location = "+ location.getLocationDescribe()+ "   sensorValue=  " + sensorValue);
		}

		public void onReceivePoi(BDLocation poiLocation) {

		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		// 获得屏幕点击的位置
		int x = (int) event.getX();
		int y = (int) event.getY();
		// 将像素坐标转为地址坐标
		LatLng loc = mBaiduMap.getProjection().fromScreenLocation(new Point(x, y));
		mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(loc));
		Log.v("crb", x + "  " + y + "   " + loc.latitude + "   "+ loc.longitude);
		return super.onTouchEvent(event);
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapTestActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
					.show();
		}
		mBaiduMap.clear();
		OverlayOptions ooA = new MarkerOptions().position(result.getLocation()).icon(bd).zIndex(5).title(result.getAddress());
		mMarker = (Marker) mBaiduMap.addOverlay(ooA);
		mShrteLayout.setVisibility(View.VISIBLE);
		mAdressTextView.setText(result.getAddress());
		
	}



	@Override
	public void onGetPoiDetailShareUrlResult(ShareUrlResult result) {

	}

	@Override
	public void onGetLocationShareUrlResult(ShareUrlResult result) {
		// 分享短串结果
		Intent it = new Intent(Intent.ACTION_SEND);
		it.putExtra(Intent.EXTRA_TEXT, "您的朋友通过百度地图SDK与您分享一个位置: " + mAdressTextView.getText().toString()
				+ " -- " + result.getUrl());
		it.setType("text/plain");
		startActivity(Intent.createChooser(it, "将短串分享到"));

	}

	private void setMyLocationData(BDLocation location) {
		MyLocationData locData = new MyLocationData.Builder()
				.accuracy(location.getRadius())
				// 此处设置开发者获取到的方向信息，顺时针0-360
				.direction(sensorValue).latitude(location.getLatitude())
				.longitude(location.getLongitude()).build();
		mBaiduMap.setMyLocationData(locData);
	}

	// 设置罗盘、普通模式
	private void setLocationMode(LocationMode currentMode) {
		mCurrentMode = currentMode;
		
		if (mCurrentMode == LocationMode.COMPASS) {
			mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
			MRequestLocButton.setText("罗盘");
			setCompassMode(true);
			myOrientationListener.start();
		} else {
			mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
			MRequestLocButton.setText("普通");
			setCompassMode(false);
			myOrientationListener.stop();
		}
	}

	// 罗盘是否隐身
	private void setCompassMode(boolean isTrue) {
		mUiSettings.setCompassEnabled(isTrue);
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		// 关闭方向传感器
		if (mCurrentMode == LocationMode.COMPASS)
			myOrientationListener.stop();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		// 开启方向传感器
		if (mCurrentMode == LocationMode.COMPASS)
			myOrientationListener.start();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mSearch.destroy();
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();

	}

}
