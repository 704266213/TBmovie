package com.golove.model;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2017-03-03 22:32
 * 修改备注：
 */

public class CartoonInfoModel {
    
    private int id;
    private int label_id;
    private int order;
    private int comics_count;
    private long updated_at;
    private long created_at;
    private String update_status;
    private String title;
    private String description;
    private String update_day;
    private String cover_image_url;
    private String vertical_image_url;
    private String discover_image_url;
    private String[] category;

    public CartoonInfoModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLabel_id() {
        return label_id;
    }

    public void setLabel_id(int label_id) {
        this.label_id = label_id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getComics_count() {
        return comics_count;
    }

    public void setComics_count(int comics_count) {
        this.comics_count = comics_count;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_status() {
        return update_status;
    }

    public void setUpdate_status(String update_status) {
        this.update_status = update_status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdate_day() {
        return update_day;
    }

    public void setUpdate_day(String update_day) {
        this.update_day = update_day;
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

    public String getDiscover_image_url() {
        return discover_image_url;
    }

    public void setDiscover_image_url(String discover_image_url) {
        this.discover_image_url = discover_image_url;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }
}
