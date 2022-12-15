package com.nrmyw.launcher.util.systemkey;

import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class ActivityKeyDownListUtil {


    //二十下返回键
    public static List<Integer> retrunOkList(int numb) {
        List<Integer> needKeyCodes = new ArrayList<>();
        for (int i = 0; i < numb; i++) {
            needKeyCodes.add(KeyEvent.KEYCODE_BACK);
        }
        return needKeyCodes;
    }

    //右右下
    public static List<Integer> toSetApkList() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_RIGHT);
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_RIGHT);
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_DOWN);
        return needKeyCodes;
    }

    //左左下
    public static List<Integer> nowUpdateList() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_LEFT);
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_LEFT);
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_DOWN);
        return needKeyCodes;
    }

    //上上下
    public static List<Integer> toWifiSetApkList() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_UP);
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_UP);
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_DOWN);
        return needKeyCodes;
    }


    //上上返回
    public static List<Integer> toOldModelList() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_UP);
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_UP);
        needKeyCodes.add(KeyEvent.KEYCODE_BACK);
        return needKeyCodes;
    }

    //中间键
    public static List<Integer> queOk1() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_CENTER);
        return needKeyCodes;
    }

    //确定按钮
    public static List<Integer> queOk2() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_ENTER);
        return needKeyCodes;
    }

    //往左
    public static List<Integer> toLeftList() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_LEFT);

        return needKeyCodes;
    }

    //往右
    public static List<Integer> toRightList() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_RIGHT);

        return needKeyCodes;
    }
}
