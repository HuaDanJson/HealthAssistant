package com.pedometerlibrary.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.pedometerlibrary.common.PedometerManager;
import com.pedometerlibrary.util.DateUtil;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2018/3/20 11:45
 * <p>
 * PedometerAlarmReceiver
 */
public class PedometerAlarmReceiver extends BroadcastReceiver {
    /**
     * 零点闹钟
     */
    public static final String ACTION_MIDNIGHT_ALARM_CLOCK = "com.pedometerlibrary.receive.PedometerAlarmReceiver.ACTION_MIDNIGHT_ALARM_CLOCK";
    /**
     * 零点工作
     */
    public static final String ACTION_MIDNIGHT_JOB_SCHEDULER = "com.pedometerlibrary.receive.PedometerAlarmReceiver.ACTION_MIDNIGHT_JOB_SCHEDULER";
    private static final String TAG = PedometerAlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_MIDNIGHT_ALARM_CLOCK.equals(action)) {
            if (DateUtil.isMidnightTime(DateUtil.getSystemTime())) {

            }
            executeZeroClockTask();
            Log.d(TAG, "PedometerAlarmReceiver：ACTION_MIDNIGHT_ALARM_CLOCK");
        } else if (ACTION_MIDNIGHT_JOB_SCHEDULER.equals(action)) {
            executeRebootPedometerTask();
            Log.d(TAG, "PedometerAlarmReceiver：ACTION_MIDNIGHT_JOB_SCHEDULER");
        }
    }

    /**
     * 执行零点钟闹钟任务
     */
    private void executeZeroClockTask() {
        PedometerManager.getInstance().setAlarmClock();
        PedometerManager.getInstance().start();
    }

    /**
     * 执行零点工作任务
     */
    private void executeRebootPedometerTask() {
        PedometerManager.getInstance().start();
    }
}
