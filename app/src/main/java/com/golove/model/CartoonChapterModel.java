package com.golove.model;

/**
 * Created by shuhj on 2017/4/5.
 */

public class CartoonChapterModel {

    private int id;
    private int topic_id;
    private int serial_no;
    private int push_flag;
    private int comments_count;
    private long updated_at;
    private int likes_count;
    private int created_at;
    private int storyboard_cnt;
    private String title;
    private String cover_image_url;
    private String url;
    private String status;
    private boolean has_pay;
    private boolean is_free;

    public CartoonChapterModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public int getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(int serial_no) {
        this.serial_no = serial_no;
    }

    public int getPush_flag() {
        return push_flag;
    }

    public void setPush_flag(int push_flag) {
        this.push_flag = push_flag;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getStoryboard_cnt() {
        return storyboard_cnt;
    }

    public void setStoryboard_cnt(int storyboard_cnt) {
        this.storyboard_cnt = storyboard_cnt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_image_url() {
        return cover_image_url;
    }

    public void setCover_image_url(String cover_image_url) {
        this.cover_image_url = cover_image_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isHas_pay() {
        return has_pay;
    }

    public void setHas_pay(boolean has_pay) {
        this.has_pay = has_pay;
    }

    public boolean is_free() {
        return is_free;
    }

    public void setIs_free(boolean is_free) {
        this.is_free = is_free;
    }
}
