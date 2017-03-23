package com.golove.model;

import java.util.List;

/**
 * Created by shuhj on 2017/3/23.
 */

public class CartoonModel extends ResultStateModel<List<CartoonDetailModel>> {

    private int cartoonType;
    private String cartoonTitle;
    private int cartoonLayout;

    public CartoonModel() {
    }

    public int getCartoonType() {
        return cartoonType;
    }

    public void setCartoonType(int cartoonType) {
        this.cartoonType = cartoonType;
    }

    public String getCartoonTitle() {
        return cartoonTitle;
    }

    public void setCartoonTitle(String cartoonTitle) {
        this.cartoonTitle = cartoonTitle;
    }

    public int getCartoonLayout() {
        return cartoonLayout;
    }

    public void setCartoonLayout(int cartoonLayout) {
        this.cartoonLayout = cartoonLayout;
    }
}
