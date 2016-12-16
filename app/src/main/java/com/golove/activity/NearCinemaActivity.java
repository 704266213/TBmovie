package com.golove.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.golove.R;

import java.util.List;

/*
 * 附近影院
 */
public class NearCinemaActivity extends AppCompatActivity {

    private MapView mapView;
    private PoiSearch poiSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_cinema);

        initView();
    }

    private void initView(){
        mapView = (MapView) findViewById(R.id.mapView);
        /** POI搜索  **/
        poiSearch = PoiSearch.newInstance();
        poiSearch.searchInCity((new PoiCitySearchOption())
                .city("深圳")
                .keyword("影院")
                .pageNum(1000));
        poiSearch.setOnGetPoiSearchResultListener(poiListener);
    }



   private OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){
       public void onGetPoiResult(PoiResult result){
           //获取POI检索结果
           List<PoiInfo> poiInfos = result.getAllPoi();
           for(PoiInfo poiInfo : poiInfos){
               Log.e("XLog","========name============" +  poiInfo.name);
           }

       }
       public void onGetPoiDetailResult(PoiDetailResult result){
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

}
