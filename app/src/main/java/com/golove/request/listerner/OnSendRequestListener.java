package com.golove.request.listerner;

import okhttp3.Callback;
import okhttp3.OkHttpClient;

/**
 * Created by shuhj on 2017/4/5.
 */

public interface OnSendRequestListener {


    void sendRequest(OkHttpClient okHttpClient, Callback callback);

}
