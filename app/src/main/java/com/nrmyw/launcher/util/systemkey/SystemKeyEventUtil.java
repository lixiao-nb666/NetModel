package com.nrmyw.launcher.util.systemkey;

import android.util.Log;
import android.view.KeyEvent;


import com.nrmyw.launcher.util.systemkey.event.bean.KeyCodesEventType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemKeyEventUtil {

    private final long keyMustBetweenTime = 88;
    private long keyStartTime = 0;
    private Map<Integer, Long> keyQueTimes = new HashMap<>();
    private KeyCodesEventUtil keyCodesEventUtil;


    public void close() {
        keyQueTimes.clear();
        keyStartTime = 0;
        if (keyCodesEventUtil != null) {
            keyCodesEventUtil.close();
        }
    }

    public void setListen(KeyCodesEventUtilListen listen) {
        keyCodesEventUtil = new KeyCodesEventUtil(listen);
    }

    public void setKeyCodesToDoEvent(KeyCodesEventType eventType, List<Integer> keyCodes) {
        keyCodesEventUtil.setKeyCodesToDoEvent(eventType, keyCodes);
    }


    public void setKeyStartTime() {
        keyStartTime = System.currentTimeMillis();
    }

    public void clearStartTime() {
        keyStartTime = 0;
    }

    private final String tag = getClass().getName() + ">>>>";

    public boolean setKeyCode(KeyEvent keyEvent) {
        Log.i(tag,"made shenme gui:1");
        if (keyCodesEventUtil == null) {
            //不需要处理，就直接返回，用系统的自定义处理就可以了
            return false;
        }
        Log.i(tag,"made shenme gui:2");
        if (keyStartTime == 0 || System.currentTimeMillis() - keyStartTime < 500) {
            //如果还没初始化开始时间，或者当前时间比开始时间小于1秒直接返回过滤掉事件
            return false;
        }
        Log.i(tag,"made shenme gui:3");
        if (keyEvent.getAction() != KeyEvent.ACTION_DOWN) {
            //不是KEYDOWN事件，直接不做处理
            return false;
        }
        int nowKeyCode = keyEvent.getKeyCode();
        Log.i(tag,"made shenme gui:4");
        if (keyQueTimes.get(nowKeyCode) != null) {
            long lastTime = keyQueTimes.get(nowKeyCode);
            Log.i(tag,"made shenme gui:5");
            if (System.currentTimeMillis() - lastTime < keyMustBetweenTime) {
                //这次的点击小于，小于上次的必须间隔的时间，直接返回不做处理
                Log.i(tag,"made shenme gui:6");
                return keyCodesEventUtil.checkIsBackToDo(nowKeyCode);
            }
        }
        Log.i(tag,"made shenme gui:7");
        keyQueTimes.put(nowKeyCode, System.currentTimeMillis());
        return keyCodesEventUtil.setKeyCode(nowKeyCode);
    }
}
