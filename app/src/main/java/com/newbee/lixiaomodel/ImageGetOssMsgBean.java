package com.newbee.lixiaomodel;

import java.io.Serializable;

public class ImageGetOssMsgBean implements Serializable {

    private String code;
    private String msg;
    private ImageGetOssConfigBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ImageGetOssConfigBean getData() {
        return data;
    }

    public void setData(ImageGetOssConfigBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ImageGetOssMsgBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
