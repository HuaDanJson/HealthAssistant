package com.demo.bs.demoapp2.ui.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.utils.GlobalValue;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Crazyzjw on 2017/4/4.
 */

public class AlamrReceiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Toast.makeText(context, "提醒时间到", Toast.LENGTH_LONG).show();
        showNotification(context);
    }
    protected void showNotification(Context  context) {
        NotificationManager notificationManager =(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification =new Notification();
        notification.icon = R.drawable.ic_launcher;
        notification.tickerText= GlobalValue.TX;
        notificationManager.cancel(0);
        notificationManager.notify(0, notification);
    }
}
