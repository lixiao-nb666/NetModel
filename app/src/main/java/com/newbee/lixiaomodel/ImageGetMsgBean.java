package com.newbee.lixiaomodel;

import java.io.Serializable;

public class ImageGetMsgBean implements Serializable {

    private String code;
    private String msg;
    private ImageGetStrBean data;

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

    public ImageGetStrBean getData() {
        return data;
    }

    public void setData(ImageGetStrBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ImageGetMsgBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
