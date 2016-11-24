package com.golove.model;

/**
 * Created by shuhj on 2016/11/24.
 */

public class FilmModel {

    private String filmName;
    private String filmUrl;
    private String filmScore;
    private String filmDesc;
    private String filmActor;

    public FilmModel() {
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmUrl() {
        return filmUrl;
    }

    public void setFilmUrl(String filmUrl) {
        this.filmUrl = filmUrl;
    }

    public String getFilmScore() {
        return filmScore;
    }

    public void setFilmScore(String filmScore) {
        this.filmScore = filmScore;
    }

    public String getFilmDesc() {
        return filmDesc;
    }

    public void setFilmDesc(String filmDesc) {
        this.filmDesc = filmDesc;
    }

    public String getFilmActor() {
        return filmActor;
    }

    public void setFilmActor(String filmActor) {
        this.filmActor = filmActor;
    }
}
