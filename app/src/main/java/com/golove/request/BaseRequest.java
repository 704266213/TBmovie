package com.golove.request;

import com.golove.BuildConfig;
import com.golove.GoloveApplication;
import com.golove.interceptor.HttpLoggingInterceptor;
import com.golove.interceptor.NetworkCacheInterceptor;
import com.golove.request.listerner.OnBuildRequestBodyListener;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-04-04 23:17
 * 修改备注：
 */
public class BaseRequest {


    private CacheControl cacheControl;
    private OkHttpClient client;

    public BaseRequest() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG
                ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);

        Cache cache = new Cache(GoloveApplication.goloveApplication.getCacheDir(), 10 * 1024 * 1024);
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new NetworkCacheInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .cache(cache)
                .build();

        final CacheControl.Builder builder = new CacheControl.Builder();
        builder.maxAge(60  , TimeUnit.SECONDS);//指示客户机可以接收生存期不大于指定时间的响应。
        builder.minFresh(10, TimeUnit.SECONDS);//指示客户机可以接收响应时间小于当前时间加上指定时间的响应。
        builder.maxStale(10 * 60, TimeUnit.SECONDS);
        cacheControl = builder.build();
    }


    public void sendRequest(String url, OnBuildRequestBodyListener onBuildRequestBodyListener, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .cacheControl(cacheControl)
                .method("POST",onBuildRequestBodyListener.buildRequestBody())
                .build();

        client.newCall(request).enqueue(callback);
    }

    public void sendRequest(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .cacheControl(cacheControl)
                .build();
        client.newCall(request).enqueue(callback);
    }


}
