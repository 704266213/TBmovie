package com.golove.request;


import com.golove.request.listerner.OnBuildRequestListener;
import com.golove.request.listerner.OnSendRequestListener;

import okhttp3.Callback;
import okhttp3.OkHttpClient;

/**
 * Created by shuhj on 2017/4/5.
 */

public class DeaultSendRequest implements OnSendRequestListener {

    private OnBuildRequestListener onBuildRequestListener;

    public DeaultSendRequest(OnBuildRequestListener onBuildRequestListener) {
        this.onBuildRequestListener = onBuildRequestListener;
    }

    public void sendRequest(Callback callback) {
        OkHttpClient okHttpClient = new DefaultOkHttpClient().onCreateOkHttpClient();
        okHttpClient.newCall(onBuildRequestListener.buildRequest()).enqueue(callback);
    }

    @Override
    public void sendRequest(OkHttpClient okHttpClient, Callback callback) {
        if (okHttpClient == null) {
            sendRequest(callback);
        }
        okHttpClient.newCall(onBuildRequestListener.buildRequest()).enqueue(callback);
    }
}
