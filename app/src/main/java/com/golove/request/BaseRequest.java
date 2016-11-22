package com.golove.request;

import android.content.Context;

import com.golove.BuildConfig;
import com.golove.interceptor.HttpLoggingInterceptor;
import com.golove.interceptor.NetworkCacheInterceptor;
import com.golove.listener.InitRequestListener;
import com.golove.param.OnBuildRequestBodyListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-04-04 23:17
 * 修改备注：
 */
public class BaseRequest {


    private CacheControl cacheControl;
    private OkHttpClient client;

    public BaseRequest(Context context) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG
                ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);

        Cache cache = new Cache(context.getCacheDir(), 10 * 1024 * 1024);
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new NetworkCacheInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .cache(cache)
                .build();

        final CacheControl.Builder builder = new CacheControl.Builder();
        builder.maxAge(60, TimeUnit.MILLISECONDS);//指示客户机可以接收生存期不大于指定时间的响应。
        builder.minFresh(10, TimeUnit.SECONDS);//指示客户机可以接收响应时间小于当前时间加上指定时间的响应。
        cacheControl = builder.build();
    }


    public void sendRequest(String url, OnBuildRequestBodyListener onBuildRequestBodyListener, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .cacheControl(cacheControl)
                .put(onBuildRequestBodyListener.buildRequestBody())
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
