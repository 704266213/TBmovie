package com.golove.model;

import java.util.List;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2017-03-03 21:56
 * 修改备注：
 */

public class CartoonList extends ResultStateModel<List<CartoonList>>{

    private int cartoonType;
    private String cartoonTitle;
    private List<CartoonDetailModel> data;


    public CartoonList() {
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

    public List<CartoonDetailModel> getData() {
        return data;
    }

    public void setData(List<CartoonDetailModel> data) {
        this.data = data;
    }
}
