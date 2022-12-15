package com.nrmyw.launcher.util.systemkey;

import android.text.TextUtils;
import android.view.KeyEvent;


import com.nrmyw.launcher.util.systemkey.event.bean.KeyCodeSEventBean;
import com.nrmyw.launcher.util.systemkey.event.bean.KeyCodesEventType;
import com.nrmyw.launcher.util.systemkey.event.bean.ResultKeyCodeSEventBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

 class KeyCodesEventUtil {
     private final String tag=getClass().getName()+">>>>>";
    /**
     * 间隔多少时间之类有效
     */
    private final long betweenTime = 2000;
    private Map<Integer, ResultKeyCodeSEventBean> strsBeanMap=new HashMap<>();
    private final String connectStr = "-";
    private StringBuilder nowDownKeyCodeStr = new StringBuilder();
    private long lastDownTime = 0;
    private KeyCodesEventUtilListen listen;
     /**
      * 是否存在返回按钮在组合按键中
      */
    private boolean isExistBack=false;


    public KeyCodesEventUtil(KeyCodesEventUtilListen listen){
        this.listen=listen;
    }



    public void close(){
        strsBeanMap.clear();
        nowDownKeyCodeStr=null;
        listen=null;
    }



    /**
     * 设置按键组合，去触发对应的事件
     */
    public void setKeyCodesToDoEvent(KeyCodesEventType eventType, List<Integer> keyCodes){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<keyCodes.size();i++){
            int keyCode=keyCodes.get(i);
            if(!isExistBack&&keyCode== KeyEvent.KEYCODE_BACK){
                isExistBack=true;
            }

            sb.append( keyCode+ connectStr);
        }
        int lastKeyCode=keyCodes.get(keyCodes.size()-1);
        ResultKeyCodeSEventBean keyCodesStrsBean=strsBeanMap.get(lastKeyCode);
        if(keyCodesStrsBean==null){
            keyCodesStrsBean=new ResultKeyCodeSEventBean();
        }
        keyCodesStrsBean.add(eventType,sb.toString());
        strsBeanMap.put(lastKeyCode,keyCodesStrsBean);
    }





    public boolean setKeyCode(int keyCode){
        long nowTime = System.currentTimeMillis();
        if (nowTime - lastDownTime > betweenTime) {
            nowDownKeyCodeStr = new StringBuilder();
        }
        lastDownTime = nowTime;
        nowDownKeyCodeStr.append(keyCode + connectStr);
        ResultKeyCodeSEventBean keyCodesStrsBean=strsBeanMap.get(keyCode);

        if(check(keyCodesStrsBean)){
            return true;
        }else {
                return checkIsBackToDo(keyCode);
        }

    }


    public boolean checkIsBackToDo(int keyCode){
        if(isExistBack&&keyCode== KeyEvent.KEYCODE_BACK){
            return true;
        }else {
            return false;
        }
    }

    private boolean check(  ResultKeyCodeSEventBean keyCodesStrsBean) {
        if(keyCodesStrsBean==null||keyCodesStrsBean.getList()==null||keyCodesStrsBean.getList().size()==0){
            return false ;
        }

        for(int i=0;i<keyCodesStrsBean.getList().size();i++){
            KeyCodeSEventBean eventBean=keyCodesStrsBean.getList().get(i);
            if(listen!=null&&eventBean!=null
                    &&eventBean.getEventType()!=null
                    && checkStr(eventBean.getKeyCodesStr(),nowDownKeyCodeStr.toString())){

            listen.nowCanDoEvent(eventBean.getEventType());
                return true;
            }
        }


        return false ;
    }


    private boolean checkStr(String str, String checkStr) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(checkStr)) {
            return false;
        }
        int strLenght = str.length();
        int checkLenght = checkStr.length();
        if (checkLenght >= strLenght && checkStr.substring(checkLenght - strLenght).equals(str)) {
            //如果要检查的那个字符串的长度大于输入进来的那个字符串，并且后面的截取出来的那个串刚好一样就等于生效
            return true;
        } else {
            return false;
        }
    }


}
