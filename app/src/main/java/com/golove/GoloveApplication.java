package com.golove;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by jeffersonxia on 15-10-17.
 */
public class GoloveApplication extends Application{


    @Override
    public void onCreate() {
        super.onCreate();


        LeakCanary.install(this);

    }
}
