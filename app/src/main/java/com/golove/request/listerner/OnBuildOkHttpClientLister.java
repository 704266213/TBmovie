package com.golove.request.listerner;

import okhttp3.OkHttpClient;

/**
 * Created by shuhj on 2017/4/5.
 */

public interface OnBuildOkHttpClientLister {

    OkHttpClient onCreateOkHttpClient();

}
