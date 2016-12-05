package com.golove;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.golove.downloader.OkHttp3Downloader;
import com.golove.service.LocationService;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;

/**
 * Created by jeffersonxia on 15-10-17.
 */
public class GoloveApplication extends Application{

    public LocationService locationService;
    public static Context goloveApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        goloveApplication = this;

        goloveApplication = this;
        initPicasso();

        LeakCanary.install(this);

        locationService = new LocationService(getApplicationContext());
        //百度地图初始化
        SDKInitializer.initialize(getApplicationContext());
    }


    /*
     * 初始化Picasso对象
     */
    private void initPicasso(){
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(this))
                .build();
        Picasso.setSingletonInstance(picasso);
    }

}
