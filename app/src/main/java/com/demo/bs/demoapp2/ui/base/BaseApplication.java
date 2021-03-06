package com.demo.bs.demoapp2.ui.base;

import android.app.Application;

import com.demo.bs.demoapp2.DBBeanUtils.DBLuRuShuJvBeanUtils;
import com.demo.bs.demoapp2.DBBeanUtils.DBUserInfoBeanUtils;
import com.demo.bs.demoapp2.DBBeanUtils.DBUserUtils;
import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.utils.ToastHelper;
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
        DBUserUtils.Init(this);
        ToastHelper.init(this);
    }

    /**
     * 默认记步器
     */
    private void initDefault() {
        Pedometer.initialize(this, new PedometerOptions.Builder()
                .setSamllIcon(R.mipmap.run_log)
                .setLargeIcon(R.mipmap.run_log)
                .setNotify(PedometerOptions.Notify.SIMPLE)
                .build());
    }

}
