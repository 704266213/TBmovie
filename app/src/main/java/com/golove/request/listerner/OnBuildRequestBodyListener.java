package com.golove.request.listerner;

import okhttp3.RequestBody;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-04-04 20:56
 * 修改备注：
 */
public interface OnBuildRequestBodyListener {

     RequestBody buildRequestBody();
}
