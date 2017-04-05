package com.golove.model;

import java.util.List;

/**
 * Created by shuhj on 2017/4/5.
 */

public class ComicModel extends ResultStateModel<ComicModel> {

    private String title;
    private String description;
    private String[] category;
    private int comics_count;
    private int comments_count;
    private int likes_count;
    private int fav_count;
    private boolean is_favourite;
    private CartoonUserModel user;
    private List<CartoonChapterModel> comics;

    public ComicModel() {
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

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public int getComics_count() {
        return comics_count;
    }

    public void setComics_count(int comics_count) {
        this.comics_count = comics_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getFav_count() {
        return fav_count;
    }

    public void setFav_count(int fav_count) {
        this.fav_count = fav_count;
    }

    public boolean is_favourite() {
        return is_favourite;
    }

    public void setIs_favourite(boolean is_favourite) {
        this.is_favourite = is_favourite;
    }

    public CartoonUserModel getUser() {
        return user;
    }

    public void setUser(CartoonUserModel user) {
        this.user = user;
    }

    public List<CartoonChapterModel> getComics() {
        return comics;
    }

    public void setComics(List<CartoonChapterModel> comics) {
        this.comics = comics;
    }
}
