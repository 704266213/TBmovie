package com.golove.model;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-04-04 21:21
 * 修改备注：
 */
public class BannerModel extends ResultStateModel {

    private String webUrl;
    private String bannerImageUrl;

    public BannerModel() {
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }
}
