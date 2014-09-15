package com.example.activitydesigner;




import resource.MyPoiOverlay;
import resource.locationOverlay;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionInfo;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class AroundActivity extends Activity implements OnClickListener{

	private MapView mapView = null;  		//MapView 是地图主控件
	private MapController mapController  = null;  //用MapController完成地图控制 
	private MKMapViewListener mapViewListener= null;       //用于处理地图事件回调
	private MKSearch mSearch = null;   // 搜索模块，也可去掉地图模块独立使用
	private View my = null; //我的活动按钮
	private View around = null;//附近活动按钮
	private View current = null;//当前活动按钮
	private ImageView back = null;//回退按钮
	private TextView search = null; //搜索按钮
	private TextView nextData = null;//下一组数据按钮
	private AutoCompleteTextView searchEdit = null;
	private ArrayAdapter<String> sugAdapter = null;
    private int load_Index;
    //搜索城市 
  	private String mCity = "上海";//默认为上海
  	//搜索关键字
  	private String searchKey = "嘉定";//搜索关键字默认为嘉定
  	public String resultAddr = "无";////保存搜索结果地址
    //反地理编译点坐标
  	private GeoPoint mPoint = new GeoPoint((int)(40.056878*1E6),(int)(116.308141*1E6));
    
  	/**
     * 定位相关
     */
 	LocationClient mLocClient;
 	LocationData locData = null;
	boolean isRequest = false;//是否手动触发请求定位
	boolean isFirstLoc = true;//是否首次定位
	//定位图层
    locationOverlay myLocationOverlay = null;
    public MyLocationListenner myListener = new MyLocationListenner();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		/**
         * 使用地图sdk前需先初始化BMapManager.
         * BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
         * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
         */
        MyApplication myApplication = (MyApplication)this.getApplication();
        if (myApplication.mapManager== null)
        {
        	myApplication.mapManager = new BMapManager(getApplicationContext());
            /**
             * 如果BMapManager没有初始化则初始化BMapManager
             */
        	myApplication.mapManager.init(new MyApplication.MyGeneralListener());
        }
        /**
          * 由于MapView在setContentView()中初始化,所以它需要在BMapManager初始化之后
          */
		setContentView(R.layout.activity_around);
		resInitial();
		 /**
         * 获取地图控制器
         */
        mapController = mapView.getController();
        /**
         *  设置地图是否响应点击事件  .
         */
        mapController.enableClick(true);
        /**
         * 设置地图缩放级别
         */
        mapController.setZoom(12);
        mapView.setLongClickable(true);
        /**
         * 将地图移动至指定点
         * 使用百度经纬度坐标，可以通过http://api.map.baidu.com/lbsapi/getpoint/index.html查询地理坐标
         * 如果需要在百度地图上显示使用其他坐标系统的位置，请发邮件至mapapi@baidu.com申请坐标转换接口
         */
        GeoPoint p ;
        double cLat = 39.945 ;
        double cLon = 116.404 ;
        Intent  intent = getIntent();
        if ( intent.hasExtra("x") && intent.hasExtra("y") ){
        	//当用intent参数时，设置中心点为指定点
        	Bundle b = intent.getExtras();
        	p = new GeoPoint(b.getInt("y"), b.getInt("x"));
        }else{
        	//设置中心点为天安门
        	 p = new GeoPoint((int)(cLat * 1E6), (int)(cLon * 1E6));
        }   
        
        //定位初始化
        mLocClient = new LocationClient( this );
        locData = new LocationData();
        mLocClient.registerLocationListener( myListener );
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");     //设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
       //定位图层初始化
      	myLocationOverlay = new locationOverlay(mapView);
      	//设置定位数据
      	myLocationOverlay.setData(locData);
      	//添加定位图层
        mapView.getOverlays().add(myLocationOverlay);
      	myLocationOverlay.enableCompass();
      	//修改定位数据后刷新图层生效
      	mapView.refresh();
	
	
	mapController.setCenter(p);
	
	 /**
     * 显示内置缩放控件
     */
		mapView.setBuiltInZoomControls(true);
    
    /**
	 *  MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
	 */
    mapViewListener = new MKMapViewListener() {
		@Override
		public void onMapMoveFinish() {
			/**
			 * 在此处理地图移动完成回调
			 * 缩放，平移等操作完成后，此回调被触发
			 */
		}
		
		@Override
		public void onClickMapPoi(MapPoi mapPoiInfo) {
			/**
			 * 在此处理底图poi点击事件
			 * 显示底图poi名称并移动至该点
			 * 设置过： mMapController.enableClick(true); 时，此回调才能被触发
			 * 
			 */
			String title = "";
			if (mapPoiInfo != null){
				title = mapPoiInfo.strText;
				Toast.makeText(AroundActivity.this,title,Toast.LENGTH_SHORT).show();
				mapController.animateTo(mapPoiInfo.geoPt);
			}
		}

		@Override
		public void onGetCurrentMap(Bitmap b) {
			/**
			 *  当调用过 mMapView.getCurrentMap()后，此回调会被触发
			 *  可在此保存截图至存储设备
			 */
		}

		@Override
		public void onMapAnimationFinish() {
			/**
			 *  地图完成带动画的操作（如: animationTo()）后，此回调被触发
			 */
		}
        /**
         * 在此处理地图载完成事件 
         */
		@Override
		public void onMapLoadFinish() {
			Toast.makeText(AroundActivity.this, 
					       "地图加载完成", 
					       Toast.LENGTH_SHORT).show();
			
			}
		};
		mapView.regMapViewListener(MyApplication.getInstance().mapManager, mapViewListener);

		
		
		
		
		/**
		 * 下面完成的是活动搜索的模块
		 */
		// 初始化搜索模块，注册搜索事件监听
		 mSearch = new MKSearch();
		 mSearch.init(myApplication.mapManager, new MKSearchListener()
		 {
			 //在此处理详情页结果
	            @Override
	            public void onGetPoiDetailSearchResult(int type, int error) {
	                if (error != 0) {
	                    Toast.makeText(AroundActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
	                }
	                else {
	                    Toast.makeText(AroundActivity.this, "成功，查看详情页面", Toast.LENGTH_SHORT).show();
	                }
	            }

				@Override
				public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onGetDrivingRouteResult(MKDrivingRouteResult arg0,
						int arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onGetPoiResult(MKPoiResult res, int type, int error)
				{
					// TODO Auto-generated method stub
					// 错误号可参考MKEvent中的定义
	                if (error != 0 || res == null) {
	                    Toast.makeText(AroundActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
	                    return;
	                }
	                // 将地图移动到第一个POI中心点
	                if (res.getCurrentNumPois() > 0) {
	                    // 将poi结果显示到地图上
	                    MyPoiOverlay poiOverlay = new MyPoiOverlay(AroundActivity.this, mapView, mSearch,resultAddr);
	                    poiOverlay.setData(res.getAllPoi());
	                    mapView.getOverlays().clear();
	                    mapView.getOverlays().add(poiOverlay);
	                    mapView.refresh();
	                    //当ePoiType为2（公交线路）或4（地铁线路）时， poi坐标为空
	                    for( MKPoiInfo info : res.getAllPoi() ){
	                    	if ( info.pt != null ){
	                    		mapView.getController().animateTo(info.pt);
	                    		break;
	                    	}
	                    }
	                } else if (res.getCityListNum() > 0) {
	                	//当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
	                    String strInfo = "在";
	                    for (int i = 0; i < res.getCityListNum(); i++) {
	                        strInfo += res.getCityListInfo(i).city;
	                        strInfo += ",";
	                    }
	                    strInfo += "找到结果";
	                    Toast.makeText(AroundActivity.this, strInfo, Toast.LENGTH_LONG).show();
	                }
				}

				@Override
				public void onGetShareUrlResult(MKShareUrlResult result,
						int arg1, int arg2) {
					// TODO Auto-generated method stub
					//分享短串结果
					Intent it = new Intent(Intent.ACTION_SEND);  
					it.putExtra(Intent.EXTRA_TEXT, "您的朋友通过Activity Designer与您分享一个活动位置: "+
							resultAddr+"     --     "+result.url);  
					it.setType("text/plain");  
					startActivity(Intent.createChooser(it, "将短串分享到"));
				}

				/**
	             * 更新建议列表
	             */
	            @Override
	            public void onGetSuggestionResult(MKSuggestionResult res, int arg1)
	            {
	            	if ( res == null || res.getAllSuggestions() == null){
	            		return ;
	            	}
	            	sugAdapter.clear();
	            	for ( MKSuggestionInfo info : res.getAllSuggestions()){
	            		if ( info.key != null)
	            		    sugAdapter.add(info.key);
	            	}
	            	sugAdapter.notifyDataSetChanged();   
	            }
				@Override
				public void onGetTransitRouteResult(MKTransitRouteResult arg0,
						int arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onGetWalkingRouteResult(MKWalkingRouteResult arg0,
						int arg1) {
					// TODO Auto-generated method stub
					
				}
		 });
		 
	        sugAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line);
	        searchEdit.setAdapter(sugAdapter);
	  
	        
	        /**
	         * 当输入关键字变化时，动态更新建议列表
	         */
	        searchEdit.addTextChangedListener(new TextWatcher(){

				@Override
				public void afterTextChanged(Editable arg0) {
					
				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					
				}
				@Override
				public void onTextChanged(CharSequence cs, int arg1, int arg2,
						int arg3) {
					 if ( cs.length() <=0 ){
						 return ;
					 }
					 String city =  mCity;//进行一个识别
					 /**
					  * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
					  */
	                 mSearch.suggestionSearch(cs.toString(), city);				
				}
	        });
	        
	        
		 
	}
	//oncrete()结束

	@Override
	protected void onPause()
	{
		/**
		 *  MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		 */
		mapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() 
	{
		/**
		 *  MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		 */
		mapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy()
	{
		/**
		 *  MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		 */
    	mapView.destroy();
    	mSearch.destory();
    	super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) 
	{
		super.onRestoreInstanceState(savedInstanceState);
		mapView.onRestoreInstanceState(savedInstanceState);
	}
	
	
    
	 public void shareAddr(View view){
	    	//发起反地理编码请求
	    	mSearch.reverseGeocode(mPoint);
	    	Toast.makeText(this,
	    			String.format("搜索位置： %f，%f", (mPoint.getLatitudeE6()*1E-6),(mPoint.getLongitudeE6()*1E-6)),
	    			Toast.LENGTH_SHORT).show();
	    }
	 
	/**
	 * 手动触发一次定位请求
	 */
	public void requestLocClick()
	{
	    isRequest = true;
	    mLocClient.requestLocation();
	    Toast.makeText(AroundActivity.this, "正在定位……", Toast.LENGTH_SHORT).show();
	 }
	
	/**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
    	
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            
            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();
            //如果不显示定位精度圈，将accuracy赋值为0即可
            locData.accuracy = location.getRadius();
            // 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
            locData.direction = location.getDerect();
            //更新定位数据
            myLocationOverlay.setData(locData);
            //更新图层数据执行刷新后生效
            mapView.refresh();
            //是手动触发请求或首次定位时，移动到定位点
            if (isRequest || isFirstLoc){
            	//移动地图到定位点
            	Log.d("LocationOverlay", "receive location, animate to it");
                mapController.animateTo(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
                isRequest = false;
                myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
            }
            //首次定位完成
            isFirstLoc = false;
        }
        
        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }
    }
    
    
    /**
     * 地图初始化
     */
    private void initMapView() {
        mapView.setLongClickable(true);
        mapView.getController().setZoom(14);
        mapView.getController().enableClick(true);
        mapView.setBuiltInZoomControls(true);
    }
    
    /**
     * 控件资源初始化
     */
	private void resInitial()
	{
		mapView = (MapView)findViewById(R.id.around_mapview);
		search = (TextView)findViewById(R.id.around_search);
		nextData = (TextView)findViewById(R.id.around_nextdata);
		around = (View)findViewById(R.id.around_around);
		my = (View)findViewById(R.id.around_my);
		current = (View)findViewById(R.id.around_current);
		searchEdit = (AutoCompleteTextView)findViewById(R.id.around_edit);
		back = (ImageView)findViewById(R.id.around_back);
		
		back.setOnClickListener(this);
		search.setOnClickListener(this);
		nextData.setOnClickListener(this);
		around.setOnClickListener(this);
		my.setOnClickListener(this);
		current.setOnClickListener(this);
		
	}
	
	/**
	 * 处理button的事件
	 */
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.around_back:
			Log.i("AroundActivity", "back onClick 调用成功");
			AroundActivity.this.finish();
			
			break;
			
		case R.id.around_search:
			Log.i("AroundActivity", "search onClick 调用成功");
			String SearchCity  = mCity;//获取当地位置
	        mSearch.poiSearchInCity(SearchCity, 
	                  searchEdit.getText().toString());
	        Toast.makeText(this,
	    			"在"+mCity+"搜索 "+searchKey,
	    			Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.around_nextdata:
			Log.i("AroundActivity", "nextdata onClick 调用成功");
			 //搜索下一组poi
			 int flag = mSearch.goToPoiPage(++load_Index);
		        if (flag != 0) {
		            Toast.makeText(AroundActivity.this, "先搜索开始，然后再搜索下一组数据", Toast.LENGTH_SHORT).show();
		        }
			break;
			
		case R.id.around_around:
			Log.i("AroundActivity", "around onClick 调用成功");
			
			break;
			
		case R.id.around_current:
			Log.i("AroundActivity", "current onClick 调用成功");
			requestLocClick();
			break;
			
		case R.id.around_my:
			Log.i("AroundActivity", "my onClick 调用成功");
		default:
			break;
		}
	}
}
