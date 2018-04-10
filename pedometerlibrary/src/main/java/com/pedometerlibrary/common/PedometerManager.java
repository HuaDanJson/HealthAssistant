package com.pedometerlibrary.common;

import android.app.Application;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.pedometerlibrary.Pedometer;
import com.pedometerlibrary.receive.PedometerAlarmReceiver;
import com.pedometerlibrary.service.PedometerJobService;
import com.pedometerlibrary.service.PedometerService;
import com.pedometerlibrary.util.AlarmManagerUtil;
import com.pedometerlibrary.util.DateUtil;
import com.pedometerlibrary.util.IntentUtil;
import com.pedometerlibrary.util.SystemUtil;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2017/12/27 12:32
 * <p>
 * PedometerManager
 */

public class PedometerManager {
    private static final String TAG = Pedometer.class.getSimpleName();
    private static boolean isInitialized;
    private static PedometerManager manager;
    private Context context;
    private PedometerOptions options;

    private PedometerManager() {
    }

    /**
     * 获取记步器管理实例
     *
     * @return PedometerManager
     */
    public static synchronized PedometerManager getInstance() {
        if (manager == null) {
            manager = new PedometerManager();
        }
        return manager;
    }

    /**
     * 是否初始化记步器
     *
     * @return
     */
    public static boolean isIsInitialized() {
        return isInitialized;
    }

    /**
     * @return Context
     */
    public Context getContext() {
        return context;
    }

    /**
     * 记步器进程是否运行
     *
     * @return
     */
    public boolean isRunning() {
        String proessName = context.getPackageName() + ":pedometerservice";
        return SystemUtil.isProessRunning(context, proessName);
    }

    /**
     * 获取记步器参数
     *
     * @return
     */
    public PedometerOptions getOptions() {
        return options;
    }

    public void initialize(Context context, PedometerOptions options) {
        if (context == null) {
            throw new IllegalArgumentException("Context can not be null");
        }

        if (!(context instanceof Application)) {
            throw new RuntimeException("Context must be an application context");
        }

        if (options == null) {
            throw new RuntimeException("PedometerOptions can not be null");
        }

        if (!isInitialized) {
            isInitialized = true;
            this.context = context.getApplicationContext();
            this.options = options;
        }

        firstStart();
    }

    public void initialize(Context context, String action) {
        if (context == null) {
            throw new IllegalArgumentException("Context can not be null");
        }

        if (!(context instanceof Application)) {
            throw new RuntimeException("Context must be an application context");
        }

        if (action == null) {
            throw new RuntimeException("PedometerAction can not be null");
        }

        if (!isInitialized) {
            isInitialized = true;
            this.context = context.getApplicationContext();
            setAction(action);
        }

        firstStart();
    }

    /**
     * 绑定记步器务隐式意图
     *
     * @param action 记步器活动隐式意图
     */
    public void setAction(String action) {
        PedometerParam.setPedometerAction(context, action);
    }

    /**
     * 获取记步通知栏
     *
     * @return 记步器主题 EMPTY：空白主题、SIMPLE：简单主题、MINUTE：详细主题
     */
    public PedometerOptions.Notify getNotify() {
        if (!isInitialized) {
            throw new RuntimeException("Pedometer is uninitialized");
        }
        int notify = PedometerParam.getPedometerNotify(context);
        if (notify == PedometerConstants.PEDOMETER_NOTIFICATION_EMPTY) {
            return PedometerOptions.Notify.EMPTY;
        }
        if (notify == PedometerConstants.PEDOMETER_NOTIFICATION_SIMPLE) {
            return PedometerOptions.Notify.SIMPLE;
        }
        if (notify == PedometerConstants.PEDOMETER_NOTIFICATION_MINUTE) {
            return PedometerOptions.Notify.MINUTE;
        }
        return PedometerOptions.Notify.SIMPLE;
    }

    /**
     * 设置记步器通知栏
     *
     * @param notify 记步器主题 EMPTY：空白主题、SIMPLE：简单主题、MINUTE：详细主题
     */
    public void setNotify(PedometerOptions.Notify notify) {
        if (!isInitialized) {
            throw new RuntimeException("Pedometer is uninitialized");
        }
        Intent intent = new Intent(PedometerService.ACTION_THEME);
        if (notify != null) {
            if (notify == PedometerOptions.Notify.EMPTY) {
                intent.putExtra("notify", PedometerConstants.PEDOMETER_NOTIFICATION_EMPTY);
            } else if (notify == PedometerOptions.Notify.SIMPLE) {
                intent.putExtra("notify", PedometerConstants.PEDOMETER_NOTIFICATION_SIMPLE);
            } else {
                intent.putExtra("notify", PedometerConstants.PEDOMETER_NOTIFICATION_MINUTE);
            }
            context.sendBroadcast(intent);
        }
    }

    /**
     * 是否开启记步器通知栏
     *
     * @return
     */
    public boolean isNotifyEnabled() {
        return getNotify() != PedometerOptions.Notify.EMPTY;
    }

    /**
     * 设置零点警告
     */
    public void setAlarmClock() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                PedometerConstants.DEFAULT_REQUEST_CODE,
                new Intent(PedometerAlarmReceiver.ACTION_MIDNIGHT_ALARM_CLOCK),
                PendingIntent.FLAG_UPDATE_CURRENT);
        long alarmTime = DateUtil.getDateMidnightTime(DateUtil.getTomorrowDate());
        AlarmManagerUtil.setAlarm(context, alarmTime, pendingIntent);
    }

    /**
     * 设置自动重启程序调度
     */
    public void setRebootJobScheduler() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            long executeTime = 10 * 1000;
            if (jobScheduler != null) {
                ComponentName componentName = new ComponentName(context.getPackageName(), PedometerJobService.class.getName());
                JobInfo jobInfo = new JobInfo.Builder(PedometerJobService.JOB_REBOOT_ID, componentName)
                        .setMinimumLatency(executeTime)
                        .setOverrideDeadline(executeTime)
                        .setPersisted(true)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setRequiresCharging(true)
                        .setRequiresDeviceIdle(true)
                        .build();
                int request = jobScheduler.schedule(jobInfo);
                if (request > 0) {
                    Log.v(TAG, "JobScheduler：PedometerJobService.JOB_REBOOT_ID");
                    return;
                }
                Log.v(TAG, "JobScheduler：Not supported");
            }
        }
    }

    /**
     * 设置午夜12点程序调度
     */
    public void setMidnightJobScheduler() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            long tomorrowZeroTime = DateUtil.getDateMidnightTime(DateUtil.getTomorrowDate());
            long executeTime = tomorrowZeroTime - DateUtil.getSystemTime();
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            if (jobScheduler != null) {
                ComponentName componentName = new ComponentName(context.getPackageName(), PedometerJobService.class.getName());
                JobInfo jobInfo = new JobInfo.Builder(PedometerJobService.JOB_MIDNIGHT_ID, componentName)
                        .setMinimumLatency(executeTime)
                        .setOverrideDeadline(executeTime)
                        .setPersisted(true)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setRequiresCharging(true)
                        .setRequiresDeviceIdle(true)
                        .build();
                int request = jobScheduler.schedule(jobInfo);
                if (request > 0) {
                    Log.v(TAG, "JobScheduler：PedometerJobService.JOB_REBOOT_ID");
                    return;
                }
            }
        }
        Log.v(TAG, "JobScheduler：Not supported");
    }

    /**
     * 第一次启动记步器
     */
    public void firstStart() {
        if (!isRunning()) {
            setAction(PedometerService.ACTION);
            setRebootJobScheduler();
            setMidnightJobScheduler();
            setAlarmClock();
            start();
        }
    }

    /**
     * 启动记步器
     */
    public void start() {
        Intent intent = new Intent(PedometerParam.getPedometerAction(context));
        context.startService(IntentUtil.createExplicitFromImplicitIntent(context, intent));
    }

}
