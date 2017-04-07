package com.golove.model;

/**
 * Created by shuhj on 2017/4/7.
 */

public class ReviewModel {

    private int status;
    private int topic_id;
    private int likes_count;
    private long review_id;
    private long created_at;
    private boolean is_liked;
    private String content;
    private CartoonUserModel user;

    public ReviewModel() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public long getReview_id() {
        return review_id;
    }

    public void setReview_id(long review_id) {
        this.review_id = review_id;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public boolean is_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CartoonUserModel getUser() {
        return user;
    }

    public void setUser(CartoonUserModel user) {
        this.user = user;
    }
}
