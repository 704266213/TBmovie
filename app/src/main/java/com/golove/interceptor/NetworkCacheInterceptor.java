package com.golove.interceptor;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;



public class NetworkCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //服务器不支持缓存，本地使用缓存，执行拦截器
        Request request = chain.request();
        Response response = chain.proceed(request);

        String cacheControl = request.cacheControl().toString();
        if (TextUtils.isEmpty(cacheControl)) {
            cacheControl = "public, max-age=60 ,max-stale= 6000";
        }
        return response.newBuilder()
                .header("Cache-Control", cacheControl)
                .removeHeader("Pragma")
                .build();


//        Request request = chain.request();
//        boolean connected = NetworkUtil.isConnected(context);
//
//        //请求在网络不可用的时候强制使用缓存
//        if (!connected) {
//            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
//         }
//        return chain.proceed(request);
    }
}