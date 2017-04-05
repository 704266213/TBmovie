package com.golove.model;

/**
 * Created by shuhj on 2017/4/5.
 */

public class CartoonUserModel {

    private int id;
    private int grade;
    private int pub_feed;
    private String avatar_url;
    private String nickname;
    private String reg_type;

    public CartoonUserModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getPub_feed() {
        return pub_feed;
    }

    public void setPub_feed(int pub_feed) {
        this.pub_feed = pub_feed;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReg_type() {
        return reg_type;
    }

    public void setReg_type(String reg_type) {
        this.reg_type = reg_type;
    }
}
