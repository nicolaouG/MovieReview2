package com.gnicolaou.user.moviereview.model;

/**
 * Created by user on 6/2/2018.
 */

public class SqlData {

    private int id;
    private String title;
    private String review;

    public SqlData(int id, String title, String review) {
        this.id = id;
        this.title = title;
        this.review = review;
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
