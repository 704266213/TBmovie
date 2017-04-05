package com.golove.request;

import com.golove.request.listerner.OnBuildRequestBodyListener;
import com.golove.request.listerner.OnBuildRequestListener;

import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Request;

/**
 * Created by shuhj on 2017/4/5.
 */

public class DefaultBuildRequest implements OnBuildRequestListener {

    private String baseUrl = "https://raw.githubusercontent.com/704266213/data/master/WebContent/data/";
    private CacheControl cacheControl;
    private String url;
    private String method;
    private OnBuildRequestBodyListener onBuildRequestBodyListener;

    public DefaultBuildRequest(String url, String method, OnBuildRequestBodyListener onBuildRequestBodyListener) {
        this.url = baseUrl + url;
        this.method = method;
        this.onBuildRequestBodyListener = onBuildRequestBodyListener;
        final CacheControl.Builder builder = new CacheControl.Builder();
        builder.maxAge(60, TimeUnit.SECONDS);//指示客户机可以接收生存期不大于指定时间的响应。
        builder.minFresh(10, TimeUnit.SECONDS);//指示客户机可以接收响应时间小于当前时间加上指定时间的响应。
        builder.maxStale(10 * 60, TimeUnit.SECONDS);
        cacheControl = builder.build();
    }

    @Override
    public Request buildRequest() {
        Request.Builder request = new Request.Builder()
                .url(url)
                .cacheControl(cacheControl);
        if (onBuildRequestBodyListener != null) {
            request.method(method, onBuildRequestBodyListener.buildRequestBody());
        } else {
            request.method(method, null);
        }
        return request.build();
    }
}
