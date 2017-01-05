package com.golove.model;

import java.util.List;

/**
 * Created by shuhj on 2017/1/3.
 */

public class ReleaseFilmInCinemaModel extends ResultStateModel<List<ReleaseFilmInCinemaModel>> {

    private String releaseDate;
    private List<CinemaModel> cinemaList;

    public ReleaseFilmInCinemaModel() {
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<CinemaModel> getCinemaList() {
        return cinemaList;
    }

    public void setCinemaList(List<CinemaModel> cinemaList) {
        this.cinemaList = cinemaList;
    }
}
