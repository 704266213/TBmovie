package com.golove.model;

import java.util.List;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-12-12 19:55
 * 修改备注：
 */

public class CinemaModel extends ResultStateModel<List<CinemaModel>> {


    private String cinemaName;
    private String cinemaLocal;
    private String cinemaDistance;
    private int lastedCinema;
    private float cinemaMinPrice;
    private List<CinemaInfo> cinemaInfos;
    private List<HallModel> hallInfos;

    public CinemaModel() {
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaLocal() {
        return cinemaLocal;
    }

    public void setCinemaLocal(String cinemaLocal) {
        this.cinemaLocal = cinemaLocal;
    }

    public String getCinemaDistance() {
        return cinemaDistance;
    }

    public void setCinemaDistance(String cinemaDistance) {
        this.cinemaDistance = cinemaDistance;
    }

    public int getLastedCinema() {
        return lastedCinema;
    }

    public void setLastedCinema(int lastedCinema) {
        this.lastedCinema = lastedCinema;
    }

    public float getCinemaMinPrice() {
        return cinemaMinPrice;
    }

    public void setCinemaMinPrice(float cinemaMinPrice) {
        this.cinemaMinPrice = cinemaMinPrice;
    }

    public List<CinemaInfo> getCinemaInfos() {
        return cinemaInfos;
    }

    public void setCinemaInfos(List<CinemaInfo> cinemaInfos) {
        this.cinemaInfos = cinemaInfos;
    }

    public List<HallModel> getHallInfos() {
        return hallInfos;
    }

    public void setHallInfos(List<HallModel> hallInfos) {
        this.hallInfos = hallInfos;
    }
}
