package com.nrmyw.launcher.util.systemkey.event.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultKeyCodeSEventBean implements Serializable {
    private List<KeyCodeSEventBean> list;

    public ResultKeyCodeSEventBean(){
        this.list=new ArrayList<>();
    }

    public List<KeyCodeSEventBean> getList() {
        return list;
    }

    public void setList(List<KeyCodeSEventBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ResultKeyCodeSEventBean{" +
                "list=" + list +
                '}';
    }

    public void add(KeyCodesEventType eventType,String eventStr){
        this.list.add(new KeyCodeSEventBean(eventStr,eventType));
    }
}
