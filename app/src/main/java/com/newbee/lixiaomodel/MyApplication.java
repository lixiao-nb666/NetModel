package com.newbee.lixiaomodel;

import com.lixiao.build.mybase.appliction.BaseApplication;
import com.lixiao.down.config.XiaoGeDownLoaderConfig;
import com.lixiao.down.manager.XiaoGeDownLoaderManager;
import com.lixiao.oss.bean.OssConfigBean;
import com.lixiao.oss.config.OssConfig;
import com.lixiao.oss.manager.OssManager;
import com.newbee.lixiaomodel.service.DemoServiceDao;


/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/2/1 0001 11:03
 */
public class MyApplication extends BaseApplication {
    @Override
    protected void init() {
        OssConfigBean ossConfigBean=new OssConfigBean();
        ossConfigBean.setAccessKeyId("STS.NTGKdLEeg7Aevbe821xNRfT9B");
        ossConfigBean.setAccessKeySecret("FrUiheRqARvhCYoSTTpMz8iwjpGokjLWvNcLML6KL87g");
        ossConfigBean.setBucketName("inno-picture-marking");
        ossConfigBean.setEndpoint("oss-cn-shanghai");
        ossConfigBean.setOssUploadPath("lixiaotest/");
        ossConfigBean.setOssDownUrlHead("https://inno-picture-marking.oss-cn-shanghai.aliyuncs.com");
        ossConfigBean.setToken("CAIS+gF1q6Ft5B2yfSjIr5fyAN74qLpGgIOOdETU3DJkdMF+iZGSoDz2IHFOfXNsBu8ZvvU0lWpW7voTlqd2QoVGRErLVcp78olN/FtkimwwEJTng4YfgbiJREKxaXeiruKwDsz9SNTCAITPD3nPii50x5bjaDymRCbLGJaViJlhHL91N0vCGlggPtpNIRZ4o8I3LGbYMe3XUiTnmW3NFkFlyGEe4CFdkf3imJLDskGE3ACmm79O/tTLT8L6P5U2DvBWSMyo2eF6TK3F3RNL5gJCnKUM1/0eoG2b4Y7CUwgKsk/Ya7aF6LhuKgl+I68hAOtKrfzxhVuTTBk5UTedGoABfR5nikgY/cZ2Y2uckmz2dnEvSOpuYuR/0xrLPNi4q/zVrTB+gkfxGFWEvlGLI+9aMMMe1SwhDYg8dZbmVEhqHwGL7PukefaBEUoE3AZfQl5ImNueMG/T4VfjaRZP/HVcoLZvIqQ8sfQy6KWzMhietwst3vfkkSZTBkN8IYXmEiU=");
        OssConfig.getInstance().setBean(ossConfigBean);
        XiaoGeDownLoaderConfig.needDownEncoded=false;
        XiaoGeDownLoaderManager.getInstance().startService(getContext());
//        OssConfig.getInstance().setBean(MyOss.getOssConfigBean());
        OssManager.getInstance(getContext());
      demoServiceDao=new DemoServiceDao(getContext());
      demoServiceDao.startService();
    }
    DemoServiceDao demoServiceDao;
    @Override
    protected void needClear(String str) {
    }

    @Override
    protected void close() {
        XiaoGeDownLoaderManager.getInstance().stopService();
        OssManager.getInstance(getContext()).close();
        demoServiceDao.stopService();
    }
}
