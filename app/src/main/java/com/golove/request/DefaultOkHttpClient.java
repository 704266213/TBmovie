package com.golove.request;

import com.golove.interceptor.NetworkCacheInterceptor;

import okhttp3.OkHttpClient;

/**
 * Created by shuhj on 2017/4/5.
 */

public class DefaultOkHttpClient extends AbstartOkHttpClient {


    @Override
    public OkHttpClient.Builder onCreateOkHttpClientBuilder(OkHttpClient.Builder builder) {
        return builder.addNetworkInterceptor(new NetworkCacheInterceptor());
    }
}
