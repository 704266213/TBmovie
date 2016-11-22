package com.golove.model;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-04-04 22:59
 * 修改备注：
 */
public class UserInfoModel extends ResultStateModel<UserInfoModel> {

    private String name;
    private int sex;
    private String headURL;

    public UserInfoModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHeadURL() {
        return headURL;
    }

    public void setHeadURL(String headURL) {
        this.headURL = headURL;
    }
}
