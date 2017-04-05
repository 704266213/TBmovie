package com.golove.request;

import com.golove.BuildConfig;
import com.golove.interceptor.HttpLoggingInterceptor;
import com.golove.request.listerner.OnBuildOkHttpClientLister;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by shuhj on 2017/4/5.
 */

public abstract class AbstartOkHttpClient implements OnBuildOkHttpClientLister {

    @Override
    public OkHttpClient onCreateOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG
                ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS);
        return onCreateOkHttpClientBuilder(builder).build();
    }

    public abstract OkHttpClient.Builder onCreateOkHttpClientBuilder(OkHttpClient.Builder builder);

}
