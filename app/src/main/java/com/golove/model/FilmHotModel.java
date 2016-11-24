package com.golove.model;

import java.util.List;

/**
 * Created by shuhj on 2016/11/24.
 */

public class FilmHotModel extends ResultStateModel<FilmHotModel> {

    private List<BannerModel> bannerModels;
    private List<FilmModel> filmModels;

    public FilmHotModel() {
    }

    public List<BannerModel> getBannerModels() {
        return bannerModels;
    }

    public void setBannerModels(List<BannerModel> bannerModels) {
        this.bannerModels = bannerModels;
    }

    public List<FilmModel> getFilmModels() {
        return filmModels;
    }

    public void setFilmModels(List<FilmModel> filmModels) {
        this.filmModels = filmModels;
    }
}
