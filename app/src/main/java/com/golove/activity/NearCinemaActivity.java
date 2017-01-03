package com.golove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.golove.R;

import java.util.List;

/*
 * 附近影院
 */
public class NearCinemaActivity extends BaseActivity {

    private MapView mapView;
    private BaiduMap baiduMap;
    private PoiSearch poiSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_cinema);

        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra("latitude",0);
        double longitude = intent.getDoubleExtra("longitude",0);

        mapView = (MapView) findViewById(R.id.mapView);
        baiduMap = mapView.getMap();

        //设定中心点坐标
        LatLng cenpt = new LatLng(latitude,longitude);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        baiduMap.setMapStatus(mMapStatusUpdate);

        //定义Maker坐标点
        LatLng point = new LatLng(latitude, longitude);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.position);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option);

        nearbySearch(latitude,longitude);
    }

    /**
     * 附近检索,范围搜索需要指定圆心.以圆形的方式进行搜索.
     */
    private void nearbySearch(double latitude,double longitude) {
        /** POI搜索  **/
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(poiListener);

        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
        nearbySearchOption.location(new LatLng(latitude, longitude));
        nearbySearchOption.keyword("影院");
        nearbySearchOption.radius(10000);// 检索半径，单位是米
        nearbySearchOption.pageNum(1000);
        poiSearch.searchNearby(nearbySearchOption);// 发起附近检索请求
    }

    private OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
        public void onGetPoiResult(PoiResult result) {
            Log.e("XLog", "========PoiResult============" + result);
            Log.e("XLog", "========PoiResult======error======" + result.error);

            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                return;
            }
            //获取POI检索结果
            List<PoiInfo> poiInfos = result.getAllPoi();
            if (poiInfos != null) {
                for (PoiInfo poiInfo : poiInfos) {
                    Log.e("XLog", "========name============" + poiInfo.name);
                }
//                mBaiduMap.clear();
//                PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
//                mBaiduMap.setOnMarkerClickListener(overlay);
//                overlay.setData(result);
//                overlay.addToMap();
//                overlay.zoomToSpan();
            }
        }

        public void onGetPoiDetailResult(PoiDetailResult result) {
            //获取Place详情页检索结果
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }


    };


    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        // 关闭定位图层
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }

//    public class MyPoiOverlay extends PoiOverlay  {
//
//        public MyPoiOverlay(BaiduMap baiduMap) {
//            super(baiduMap);
//        }
//
//        // 检索Poi详细信息.获取PoiOverlay
//        @Override
//        public boolean onPoiClick(int arg0) {
//            super.onPoiClick(arg0);
//            PoiInfo poiInfo = getPoiResult().getAllPoi().get(arg0);
//            poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(poiInfo.uid));
//            return true;
//        }
//    }

}
