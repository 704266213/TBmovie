package com.golove.request.listerner;

import okhttp3.Request;

/**
 * Created by shuhj on 2017/4/5.
 */

public interface OnBuildRequestListener {

    Request buildRequest();

}
