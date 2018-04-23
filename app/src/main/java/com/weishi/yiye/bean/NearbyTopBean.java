package com.weishi.yiye.bean;

/**
 * Created by zym on 2018/1/12.
 */

public class NearbyTopBean {
    public NearbyTopBean(Integer imgUrl, String title) {
        this.imgUrl = imgUrl;
        this.title = title;
    }

    @Override
    public String toString() {
        return "NearbyTopBean{" +
                "imgUrl=" + imgUrl +
                ", title='" + title + '\'' +
                '}';
    }

    private Integer imgUrl;
    private String title;

    public Integer getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Integer imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
