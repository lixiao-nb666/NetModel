package com.newbee.lixiaomodel.service;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.lixiao.build.gson.MyGson;
import com.lixiao.build.mybase.service.BaseService;
import com.lixiao.http.okhttp.DataCallBack;
import com.lixiao.http.okhttp.OkHttpManager;
import com.lixiao.oss.bean.OssConfigBean;
import com.lixiao.oss.config.OssConfig;
import com.lixiao.oss.event.OssServiceEventSubscriptionSubject;
import com.lixiao.oss.listen.OssServiceUploadListenObserver;
import com.lixiao.oss.listen.OssServiceUploadListenSubscriptionSubject;
import com.newbee.lixiaomodel.ImageGetMsgBean;
import com.newbee.lixiaomodel.ImageGetOssConfigBean;
import com.newbee.lixiaomodel.ImageGetOssMsgBean;
import com.newbee.lixiaomodel.ImageGetStrBean;
import com.newbee.lixiaomodel.ImageStrBean;
import com.newbee.lixiaomodel.service.event.NewBeeTtsObserver;
import com.newbee.lixiaomodel.service.event.NewBeeTtsSubscriptionSubject;
import com.nrmyw.launcher.glide.MyGlide;
import com.nrmyw.launcher.util.LiXiaoBaiDuBoardCastUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DemoService extends BaseService {

    NewBeeTtsObserver newBeeTtsObserver=new NewBeeTtsObserver() {
        @Override
        public void ttsImagePath(String str) {
            filePath=str;
            StartTime=System.currentTimeMillis();
            readFilePath();
        }
    };

    private void readFilePath(){
        if(!TextUtils.isEmpty(filePath)){
            String url="https://marking.picture.innocn.com/api/file/getOssSTSArgs";

            OkHttpManager.getAsync(url,dataCallBack,1);

//        MyGlide.getInstance().setBitMap(context,imageView,filePath);
            MyGlide.getInstance().getBitmapWAndH(getBaseContext(),filePath,getBitmapImp);
        }else {
//            finish();
        }
    }

    private    String filePath;
    private ImageView imageView;
    int w;int h;
    private long StartTime;
    MyGlide.GetBitmapImp getBitmapImp=new MyGlide.GetBitmapImp() {
        @Override
        public void getBitmap(Bitmap bitmap) {

        }

        @Override
        public void getBitMapWAndH(int a, int b) {
            w=a;
            h=b;
            toGetSpeark();
            Log.i(tag,"----kankanfilePath:"+w+"-------"+h);
        }
    };

    private void toGetSpeark(){
        if(0==w||0==h){
            return;
        }
        if(TextUtils.isEmpty(fileUrl)){
            return;
        }
        getSpeark();
    }


    private void getSpeark(){
        String url="https://marking.picture.innocn.com/api/file/markPic";
        Map<String,Object> map=new HashMap<>();
        map.put("picUrl",fileUrl);
        map.put("picLength",w);
        map.put("picWidth",h);
        map.put("language","en");
        String jsonStr= MyGson.getInstance().toGsonStr(map);
        OkHttpManager.postAsync_json(url,jsonStr,dataCallBack,2);

    }

    private DataCallBack dataCallBack=new DataCallBack() {
        @Override
        public void requestFailure(String request, IOException e, int netbs) {
            Log.i(tag,"----kankanfilePath111:"+e.toString());


        }

        @Override
        public void requestSuccess(String result, int netbs) throws Exception {
            Log.i(tag,"----kankanfilePath222:"+result);
            switch (netbs){
                case 1:
                    uploadNet(result);
                    break;
                case 2:
                    getNetSpearkStr(result);

                    break;
            }




        }

        private void getNetSpearkStr(String result){
            ImageGetMsgBean msgBean= MyGson.getInstance().fromJson(result,ImageGetMsgBean.class);
            if(null!=msgBean&&"0".equals(msgBean.getCode())){
                ImageGetStrBean strBean=msgBean.getData();
                StringBuilder sb=new StringBuilder();
                for(int i=0;i<strBean.getList().size();i++){
                    ImageStrBean bean=strBean.getList().get(i);
                    sb.append(bean.getValue());
                    if(i<strBean.getList().size()-1){

                        sb.append(" or ");
                    }

                }
                long nowTime=System.currentTimeMillis();
                Log.i("lixiao","kankanjieguo:"+sb.toString()+"--countTime:"+(nowTime-StartTime));
                LiXiaoBaiDuBoardCastUtil.sendNewBeeBaiDuTtsStr(getBaseContext(),sb.toString());
            }
        }

        private void uploadNet(String result){
            ImageGetOssMsgBean msgBean= MyGson.getInstance().fromJson(result, ImageGetOssMsgBean.class);
            if(null!=msgBean){
                switch (msgBean.getCode()){
                    case "0":
                        break;
                }
                ImageGetOssConfigBean imageGetOssConfigBean=msgBean.getData();
                OssConfigBean ossConfigBean=new OssConfigBean();
                ossConfigBean.setAccessKeyId(imageGetOssConfigBean.getAccessKeyId());
                ossConfigBean.setAccessKeySecret(imageGetOssConfigBean.getAccessKeySecret());
                ossConfigBean.setEndpoint("oss-cn-shanghai.aliyuncs.com");
                ossConfigBean.setBucketName(imageGetOssConfigBean.getBucket());
                ossConfigBean.setToken(imageGetOssConfigBean.getSecurityToken());
                ossConfigBean.setOssDownUrlHead("https://"+imageGetOssConfigBean.getBucket()+".oss-cn-shanghai.aliyuncs.com");
                ossConfigBean.setOssUploadPath("lixiao/");
                OssConfig.getInstance().setBean(ossConfigBean);
                Log.i("lixiao","kankanOssConfig111:"+imageGetOssConfigBean);
                Log.i("lixiao","kankanOssConfig222:"+ossConfigBean);
                File file=new File(filePath);
                String fileName=file.getName();

                OssServiceEventSubscriptionSubject.getInstence().upload(fileName,filePath);
//                Config.OSS_ACCESS_KEY_ID= imageGetOssConfigBean.getAccessKeyId();
//                Config.OSS_ACCESS_KEY_SECRET=imageGetOssConfigBean.getAccessKeySecret();
//                Config.token=imageGetOssConfigBean.getSecurityToken();
//
//                UploadHelper.uploadImage(filePath);
            }else {
//                showToast("识别失败："+msgBean.toString());
            }
        }
    };

    private String fileUrl;
    private OssServiceUploadListenObserver ossServiceUploadListenObserver=new OssServiceUploadListenObserver() {
        @Override
        public void uploadOk(String uploadFilePath, String fileName) {
            fileUrl=OssConfig.getInstance().getBean().getOssDownUrlHead()+File.separator+OssConfig.getInstance().getBean().getOssUploadPath()+fileName;

            Log.i("lixiaoaaaaaaa","uploadOk:"+fileUrl);

            toGetSpeark();

//            https://inno-picture-marking.oss-cn-shanghai.aliyuncs.com/lixiao/wx_camera_1665406791188.jpg
//            https://inno-picture-markingoss-cn-shanghai.aliyuncs.com/lixiao/wx_camera_1665406791188.jpg
//            https://inno-picture-marking.oss-cn-shanghai.aliyuncs.com/lixiao/wx_camera_1665222935130.jpg
        }


        @Override
        public void uploadProgress(String uploadFilePath, int progress) {
            Log.i("lixiao","uploadProgress:"+uploadFilePath+"------"+progress);
        }

        @Override
        public void uploadErr(String uploadFilePath, String errStr) {
            Log.i("lixiao","uploadErr:"+uploadFilePath+"----"+errStr);
        }
    };

    @Override
    public void init() {
        OssServiceUploadListenSubscriptionSubject.getInstence().attach(ossServiceUploadListenObserver);
        NewBeeTtsSubscriptionSubject.getInstence().attach(newBeeTtsObserver);
    }

    @Override
    public void start(Intent intent, int flags, int startId) {

    }

    @Override
    public void close() {
        OssServiceUploadListenSubscriptionSubject.getInstence().detach(ossServiceUploadListenObserver);
        NewBeeTtsSubscriptionSubject.getInstence().detach(newBeeTtsObserver);
    }
}
