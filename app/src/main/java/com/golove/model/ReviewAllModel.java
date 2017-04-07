package com.golove.model;

import java.util.List;

/**
 * Created by shuhj on 2017/4/7.
 */

public class ReviewAllModel extends ResultStateModel<ReviewAllModel> {

    private int since;
    private int total;
    private List<ReviewModel> reviews;

    public ReviewAllModel() {
    }

    public int getSince() {
        return since;
    }

    public void setSince(int since) {
        this.since = since;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ReviewModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewModel> reviews) {
        this.reviews = reviews;
    }
}
