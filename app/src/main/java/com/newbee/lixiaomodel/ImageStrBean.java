package com.newbee.lixiaomodel;

import java.io.Serializable;

public class ImageStrBean implements Serializable {
    private String value;
    private double confidence;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "ImageStrBean{" +
                "value='" + value + '\'' +
                ", confidence=" + confidence +
                '}';
    }
}
