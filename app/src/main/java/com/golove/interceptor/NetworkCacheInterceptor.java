package com.golove.interceptor;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;



public class NetworkCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

//        boolean connected = NetworkUtil.isConnected(context);
        boolean connected = true;

        //请求在网络不可用的时候强制使用缓存
        if (!connected) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
         }

        return chain.proceed(request);
    }
}