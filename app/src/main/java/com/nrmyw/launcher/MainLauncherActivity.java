package com.nrmyw.launcher;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lixiao.build.mybase.activity.BaseCompatActivity;
import com.lixiao.build.util.systemapp.PackageManagerUtil;
import com.lixiao.build.util.systemapp.StartOtherApkUtil;
import com.lixiao.build.util.systemapp.adapter.SystemAppsListAdapter;
import com.lixiao.build.util.systemapp.bean.ResultSystemAppInfoBean;
import com.lixiao.build.util.systemapp.bean.SystemAppInfoBean;
import com.lixiao.build.util.systemapp.observer.PackageManagerObserver;
import com.lixiao.build.util.systemapp.observer.PackageManagerSubscriptionSubject;
import com.lixiao.build.util.systemapp.observer.PackageManagerType;
import com.nrmyw.launcher.adapter.MainLauncherIconAdapter;
import com.nrmyw.launcher.util.LiXiaoBaiDuBoardCastUtil;
import com.nrmyw.launcher.util.UrlToFilePathUtil;
import com.nrmyw.launcher.util.systemkey.ActivityKeyDownListUtil;
import com.nrmyw.launcher.util.systemkey.KeyCodesEventUtilListen;
import com.nrmyw.launcher.util.systemkey.SystemKeyEventUtil;
import com.nrmyw.launcher.util.systemkey.event.bean.KeyCodesEventType;
import com.nrmyw.launcher.view.CardScaleHelper;

public class MainLauncherActivity extends BaseCompatActivity {

    private final int init_list = 1;
    private final int set_tv = 2;


    private PackageManagerObserver packageManagerObserver = new PackageManagerObserver() {
        @Override
        public void update(PackageManagerType eventBs, Object object) {
            switch (eventBs) {
                case GET_SYSTEM_APPS:
                    Message message = new Message();
                    message.what = init_list;
                    message.obj = object;
                    viewHandler.sendMessageDelayed(message, 0);
                    break;
                case ERR:
                    break;
            }
        }
    };


    private Handler viewHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case init_list:

                    ResultSystemAppInfoBean resultSystemAppInfoBean = (ResultSystemAppInfoBean) msg.obj;
                    Log.i(tag, "-----------kankanlist:" + resultSystemAppInfoBean);
                    adapter.setData(resultSystemAppInfoBean.getAppList());
//                    viewHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mCardScaleHelper.setCurrentItemPos(6);
//                        }
//                    },500);
//                    mCardScaleHelper.setCurrentItemPos(0);
                    break;
                case set_tv:
                   /* SystemAppInfoBean appInfoBean= (SystemAppInfoBean) msg.obj;
                    titleTV.setText("nrmyw("+appInfoBean.getName()+")");*/

                    break;
            }
        }
    };
    private RecyclerView appLV;
    private CardScaleHelper mCardScaleHelper;
    private MainLauncherIconAdapter adapter;
    private MainLauncherIconAdapter.ItemClick itemChick = new MainLauncherIconAdapter.ItemClick() {
        @Override
        public void nowSelect(SystemAppInfoBean appInfoBean) {
            if (null != appInfoBean) {
//                Message message = new Message();
//                message.what = set_tv;
//                message.obj = appInfoBean;
//                viewHandler.sendMessageDelayed(message, 0);
                StartOtherApkUtil.getInstance().doStartApk(context,appInfoBean.getPakeageName(),appInfoBean.getIndexActivityClass());
            }
        }
    };

    private TextView titleTV,otherTV;

    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_launcher;
    }

    @Override
    public void initView() {
        appLV = findViewById(R.id.lv);
        titleTV = findViewById(R.id.tv_title);
        otherTV=findViewById(R.id.tv_other);
        view.post(new Runnable() {
            @Override
            public void run() {
                int  w = view.getMeasuredWidth();
                int  h = view.getMeasuredHeight();
                Log.i(tag, "----------kankanWandH:" + w + "---" + h);
                float tvSize=w/27;
                titleTV.setTextSize(tvSize);
                titleTV.setVisibility(View.VISIBLE);
                otherTV.setTextSize((float) (tvSize/2.6));
                otherTV.setVisibility(View.VISIBLE);
                initRecyclerView(w,h);
                PackageManagerSubscriptionSubject.getInstance().addObserver(packageManagerObserver);
                PackageManagerUtil.getInstance().toGetSystemApps();
            }
        });
    }


    @Override
    public void initData() {

    }


    private void initRecyclerView(int w,int h) {
        // mRecyclerView绑定scale效果
        mCardScaleHelper = new CardScaleHelper();
        mCardScaleHelper.attachToRecyclerView(appLV);


        adapter = new MainLauncherIconAdapter(context, itemChick,w,h,mCardScaleHelper);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        appLV.setLayoutManager(linearLayoutManager);
        appLV.setAdapter(adapter);



    }

    protected SystemKeyEventUtil keyEventUtil = new SystemKeyEventUtil();
    @Override
    public void initControl() {

        keyEventUtil.setListen(keyCodesEventUtilListen);
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.LEFT, ActivityKeyDownListUtil.toLeftList());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.RIGHT, ActivityKeyDownListUtil.toRightList());

        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.QUE, ActivityKeyDownListUtil.queOk1());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.QUE, ActivityKeyDownListUtil.queOk2());

    }

    @Override
    public void closeActivity() {
        viewHandler.removeCallbacksAndMessages(null);
        PackageManagerSubscriptionSubject.getInstance().removeObserver(packageManagerObserver);
    }


    @Override
    public void viewIsShow() {
        keyEventUtil.setKeyStartTime();
    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {

    }

    /**
     * 截获按键事件.发给ScanGunKeyEventHelper
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.i(tag,"kankannowCanDoEvent1:"+event.getKeyCode());
        if (keyEventUtil == null || event == null){
            Log.i(tag,"kankannowCanDoEvent11:"+event.getKeyCode());
            return false;
        }


        if (keyEventUtil.setKeyCode(event)) {
            Log.i(tag,"kankannowCanDoEvent111:"+event.getKeyCode());
            return true;
        } else {
            Log.i(tag,"kankannowCanDoEvent1111:"+event.getKeyCode());
            return super.dispatchKeyEvent(event);
        }
    }


    private KeyCodesEventUtilListen keyCodesEventUtilListen = new KeyCodesEventUtilListen() {
        @Override
        public void nowCanDoEvent(KeyCodesEventType eventType) {
            Log.i(tag,"kankannowCanDoEvent:"+eventType);
            switch (eventType) {
                case LEFT:
                    int leftIndex=mCardScaleHelper.getCurrentItemPos()-1;
                    if(leftIndex<0){
                        leftIndex=0;
                    }

                    appLV.smoothScrollToPosition(leftIndex);
                    break;
                case RIGHT:
                    int rightIndex=mCardScaleHelper.getCurrentItemPos()+1;
                    if(rightIndex>=adapter.getItemCount()){
                        rightIndex=adapter.getItemCount()-1;
                    }

                    mCardScaleHelper.setCurrentItemPos(rightIndex);
                    break;
                case QUE:
                    adapter.setItemSelectData(mCardScaleHelper.getCurrentItemPos());
                    break;
            }

        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(tag,"kankannowCanDoEvent2:"+event.getKeyCode());
        if (keyEventUtil.setKeyCode(event)) {
            Log.i(tag,"kankannowCanDoEvent22:"+event.getKeyCode());
          return  true ;
        }
        return super.onKeyDown(keyCode, event);
    }
}
