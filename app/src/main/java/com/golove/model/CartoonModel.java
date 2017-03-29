package com.golove.model;

import java.util.List;

/**
 * Created by shuhj on 2017/3/23.
 */

public class CartoonModel extends ResultStateModel<CartoonModel> {


    private List<CartoonDetailModel> cartoons;
    private List<CartoonBannerModel> banners;
    private List<CartoonTabModel> tabs;

    public CartoonModel() {
    }

    public List<CartoonDetailModel> getCartoons() {
        return cartoons;
    }

    public void setCartoons(List<CartoonDetailModel> cartoons) {
        this.cartoons = cartoons;
    }

    public List<CartoonBannerModel> getBanners() {
        return banners;
    }

    public void setBanners(List<CartoonBannerModel> banners) {
        this.banners = banners;
    }

    public List<CartoonTabModel> getTabs() {
        return tabs;
    }

    public void setTabs(List<CartoonTabModel> tabs) {
        this.tabs = tabs;
    }
}
