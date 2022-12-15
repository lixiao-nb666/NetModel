package com.newbee.lixiaomodel.service.event;

import java.util.ArrayList;
import java.util.List;

public class NewBeeTtsSubscriptionSubject implements NewBeeTtsSubject {

    private List<NewBeeTtsObserver> observers;
    private static NewBeeTtsSubscriptionSubject subscriptionSubject;

    private NewBeeTtsSubscriptionSubject() {
        observers = new ArrayList<>();
    }

    public static NewBeeTtsSubscriptionSubject getInstence() {
        if (subscriptionSubject == null) {
            synchronized (NewBeeTtsSubscriptionSubject.class) {
                if (subscriptionSubject == null)
                    subscriptionSubject = new NewBeeTtsSubscriptionSubject();
            }
        }
        return subscriptionSubject;
    }

    @Override
    public void attach(NewBeeTtsObserver observer) {

        observers.add(observer);

    }

    @Override
    public void detach(NewBeeTtsObserver observer) {
        observers.remove(observer);
    }


    @Override
    public void ttsImagePath(String filePath) {
        for(NewBeeTtsObserver observer:observers){
            observer.ttsImagePath(filePath);
        }
    }


}
