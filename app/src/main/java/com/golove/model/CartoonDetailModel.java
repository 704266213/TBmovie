package com.golove.model;


/**
 * Created by shuhj on 2017/3/23.
 */

public class CartoonDetailModel extends CartoonModel {

    private int id;
    private int position;
    private String title;
    private String likes;
    private String description;
    private String cover_image_url;
    private String vertical_image_url;

    public CartoonDetailModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover_image_url() {
        return cover_image_url;
    }

    public void setCover_image_url(String cover_image_url) {
        this.cover_image_url = cover_image_url;
    }

    public String getVertical_image_url() {
        return vertical_image_url;
    }

    public void setVertical_image_url(String vertical_image_url) {
        this.vertical_image_url = vertical_image_url;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
