package com.golove;

import android.app.Application;
import android.content.Context;

import com.golove.downloader.OkHttp3Downloader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;

/**
 * Created by jeffersonxia on 15-10-17.
 */
public class GoloveApplication extends Application{

    public static Context goloveApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        goloveApplication = this;

        goloveApplication = this;
        initPicasso();

        LeakCanary.install(this);
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
