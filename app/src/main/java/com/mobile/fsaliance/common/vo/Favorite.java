package com.mobile.fsaliance.common.vo;


import java.io.Serializable;

/**
 * @author yuanxueyuan
 * @Description: 选品库
 * @date 2018/1/23  22:35
 */
public class Favorite implements Serializable{
    private String favoriteID;//选品库ID
    private String favoriteName;//选品库名称
    private int type;//选品库类型

    public String getFavoriteID() {
        return favoriteID;
    }

    public void setFavoriteID(String favoriteID) {
        this.favoriteID = favoriteID;
    }

    public String getFavoriteName() {
        return favoriteName;
    }

    public void setFavoriteName(String favoriteName) {
        this.favoriteName = favoriteName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "favoriteID='" + favoriteID + '\'' +
                ", favoriteName='" + favoriteName + '\'' +
                ", type=" + type +
                '}';
    }
}
