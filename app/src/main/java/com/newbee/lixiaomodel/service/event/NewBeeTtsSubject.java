package com.newbee.lixiaomodel.service.event;


public interface NewBeeTtsSubject {
    /**
     * 增加订阅者
     *
     * @param observer
     */
    public void attach(NewBeeTtsObserver observer);

    /**
     * 删除订阅者
     *
     * @param observer
     */
    public void detach(NewBeeTtsObserver observer);


    public void ttsImagePath(String filePath);
}
