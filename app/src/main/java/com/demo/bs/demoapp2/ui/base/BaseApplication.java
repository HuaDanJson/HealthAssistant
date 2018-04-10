package com.demo.bs.demoapp2.ui.base;

import android.app.Application;

import com.demo.bs.demoapp2.DBBeanUtils.DBLuRuShuJvBeanUtils;
import com.demo.bs.demoapp2.DBBeanUtils.DBUserInfoBeanUtils;
import com.demo.bs.demoapp2.R;
import com.pedometerlibrary.Pedometer;
import com.pedometerlibrary.common.PedometerOptions;

/**
 * Created by Json on 2017/3/29.
 */

public class BaseApplication extends Application {

    private static BaseApplication INSTANCE;

    public static BaseApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        DBUserInfoBeanUtils.Init(getApplicationContext());
        DBLuRuShuJvBeanUtils.Init(getApplicationContext());
        initDefault();
    }

    /**
     * 默认记步器
     */
    private void initDefault() {
        Pedometer.initialize(this, new PedometerOptions.Builder()
                .setSamllIcon(R.mipmap.ic_launcher)
                .setLargeIcon(R.mipmap.ic_launcher)
                .setNotify(PedometerOptions.Notify.SIMPLE)
                .build());
    }

}
