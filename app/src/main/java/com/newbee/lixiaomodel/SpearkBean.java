package com.newbee.lixiaomodel;

import java.io.Serializable;

public class SpearkBean implements Serializable {
    private String picUrl;
    private long picLength;
    private long picWidth;
    private String language;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public long getPicLength() {
        return picLength;
    }

    public void setPicLength(long picLength) {
        this.picLength = picLength;
    }

    public long getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(long picWidth) {
        this.picWidth = picWidth;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "SpearkBean{" +
                "picUrl='" + picUrl + '\'' +
                ", picLength=" + picLength +
                ", picWidth=" + picWidth +
                ", language='" + language + '\'' +
                '}';
    }
}
