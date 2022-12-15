package com.newbee.lixiaomodel.service;

import android.content.Context;

import com.lixiao.build.mybase.service.BaseServiceDao;

public class DemoServiceDao extends BaseServiceDao {
    public DemoServiceDao(Context context) {
        super(context);
    }

    @Override
    protected Class getSsCls() {
        return DemoService.class;
    }
}
