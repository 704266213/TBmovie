package com.golove.param;

import okhttp3.RequestBody;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-04-04 23:21
 * 修改备注：
 */
public class BuildBannerParam implements BuildParamListener {

    private BuildBannerParamListener buildBannerParamListener;

    public BuildBannerParam(BuildBannerParamListener buildBannerParamListener) {
        this.buildBannerParamListener = buildBannerParamListener;
    }


    public RequestBody bulidParam() {
        return buildBannerParamListener.buildBannerParamListener();
    }


}
