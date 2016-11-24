package com.golove.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 服务器不支持缓存，本地使用缓存
 */
public class NativeCacheInterceptor implements Interceptor {

    private int effectiveTime;

    public NativeCacheInterceptor(int effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        //服务器不支持缓存，本地使用缓存，执行拦截器
        Request request = chain.request();
        Response response = chain.proceed(request);
        return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                //cache for day
                // smax-age 文件的有效时间
                //max-stale 文件的存储时间
                .header("Cache-Control", "max-age=" + effectiveTime +  " ,max-stale=" + Integer.MIN_VALUE)
                .build();
    }
}
