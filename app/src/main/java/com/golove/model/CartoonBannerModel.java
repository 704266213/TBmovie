package com.golove.model;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2017-03-03 22:39
 * 修改备注：
 */

public class CartoonBannerModel {

    private int target_id;
    private String pic;
    private int type;
    private String target_title;
    private String target_web_url;
    private int id;
    private String request_id;
    private int chapter_count;

    public CartoonBannerModel() {
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTarget_title() {
        return target_title;
    }

    public void setTarget_title(String target_title) {
        this.target_title = target_title;
    }

    public String getTarget_web_url() {
        return target_web_url;
    }

    public void setTarget_web_url(String target_web_url) {
        this.target_web_url = target_web_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getChapter_count() {
        return chapter_count;
    }

    public void setChapter_count(int chapter_count) {
        this.chapter_count = chapter_count;
    }
}
