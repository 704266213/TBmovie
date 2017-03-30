package com.golove.model;

import java.util.List;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2017-03-03 22:31
 * 修改备注：
 */

public class PerfectCartoonModel extends ResultStateModel<PerfectCartoonModel> {

    private String surface_image;
    private List<CartoonInfoModel> topics;

    public PerfectCartoonModel() {
    }

    public String getSurface_image() {
        return surface_image;
    }

    public void setSurface_image(String surface_image) {
        this.surface_image = surface_image;
    }

    public List<CartoonInfoModel> getTopics() {
        return topics;
    }

    public void setTopics(List<CartoonInfoModel> topics) {
        this.topics = topics;
    }
}
