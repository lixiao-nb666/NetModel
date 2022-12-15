package com.newbee.lixiaomodel;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.lixiao.build.gson.MyGson;
import com.lixiao.build.mybase.activity.BaseCompatActivity;
import com.lixiao.http.okhttp.DataCallBack;
import com.lixiao.http.okhttp.OkHttpManager;
import com.lixiao.oss.bean.OssConfigBean;
import com.lixiao.oss.config.OssConfig;
import com.lixiao.oss.event.OssServiceEventSubscriptionSubject;
import com.lixiao.oss.listen.OssServiceUploadListenObserver;
import com.lixiao.oss.listen.OssServiceUploadListenSubscriptionSubject;
import com.newbee.lixiaomodel.service.event.NewBeeTtsSubscriptionSubject;
import com.nrmyw.launcher.R;
import com.nrmyw.launcher.glide.MyGlide;
import com.nrmyw.launcher.util.LiXiaoBaiDuBoardCastUtil;
import com.nrmyw.launcher.util.UrlToFilePathUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/3/9 0009 15:35
 */
public class TestUploadActivity extends BaseCompatActivity {
    private TextView downProgressTV;




    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_test_down;
    }

    @Override
    public void initView() {
        downProgressTV=findViewById(R.id.tv_down_progress);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initControl() {
//        basehandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                String filePath="/storage/emulated/0/tencent/qq_images/-6f30e1afbf255881.jpg";
//                String fileName="-6f30e1afbf255881.jpg";
//                OssServiceEventSubscriptionSubject.getInstence().upload(fileName,filePath);
////                OssServiceEventSubscriptionSubject.getInstence().upload("lixiaoDemoTest3_9.mp4","/storage/emulated/0/lixiaodown/lixiaomodel/2021-01-15%2013475911.mov");
//            }
//        },5000);

    }

    @Override
    public void closeActivity() {

    }






    @Override
    public void viewIsShow() {

       String filePath = UrlToFilePathUtil.uriToFilePath(getIntent(),context);
        Log.i(tag,"----kankanfilePath:"+filePath);


        NewBeeTtsSubscriptionSubject.getInstence().ttsImagePath(filePath);
        finish();
    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {

    }
}
