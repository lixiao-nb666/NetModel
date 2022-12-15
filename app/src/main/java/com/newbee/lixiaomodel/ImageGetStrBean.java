package com.newbee.lixiaomodel;

import java.io.Serializable;
import java.util.List;

public class ImageGetStrBean implements Serializable {
    private String markingPicUrl;
    private String language;
    private List<ImageStrBean> list;
    private List<ImageStrBean> originList;

    public String getMarkingPicUrl() {
        return markingPicUrl;
    }

    public void setMarkingPicUrl(String markingPicUrl) {
        this.markingPicUrl = markingPicUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<ImageStrBean> getList() {
        return list;
    }

    public void setList(List<ImageStrBean> list) {
        this.list = list;
    }

    public List<ImageStrBean> getOriginList() {
        return originList;
    }

    public void setOriginList(List<ImageStrBean> originList) {
        this.originList = originList;
    }

    @Override
    public String toString() {
        return "ImageGetStrBean{" +
                "markingPicUrl='" + markingPicUrl + '\'' +
                ", language='" + language + '\'' +
                ", list=" + list +
                ", originList=" + originList +
                '}';
    }
}
