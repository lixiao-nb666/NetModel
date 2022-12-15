package com.nrmyw.launcher.util.systemkey.event.bean;

import java.io.Serializable;

public class KeyCodeSEventBean implements Serializable {
    private String keyCodesStr;
    private KeyCodesEventType eventType;

    public KeyCodeSEventBean(String keyCodesStr, KeyCodesEventType eventType) {
        this.keyCodesStr = keyCodesStr;
        this.eventType = eventType;
    }

    public String getKeyCodesStr() {
        return keyCodesStr;
    }

    public void setKeyCodesStr(String keyCodesStr) {
        this.keyCodesStr = keyCodesStr;
    }

    public KeyCodesEventType getEventType() {
        return eventType;
    }

    public void setEventType(KeyCodesEventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "KeyCodeSEventBean{" +
                "keyCodesStr='" + keyCodesStr + '\'' +
                ", eventType=" + eventType +
                '}';
    }
}
