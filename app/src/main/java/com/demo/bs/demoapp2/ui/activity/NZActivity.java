package com.demo.bs.demoapp2.ui.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.CDatePickerDialog;
import com.demo.bs.demoapp2.utils.GlobalValue;

import java.util.Calendar;

//检测提醒
public class NZActivity extends BaseActivity{
    /** Called when the activity is first created. */
    //private TextView tv = null;
    private Button btn_set = null;
    private Button btn_cel = null;
    private Calendar c = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nz);
        setToolbar("设置提醒通知");
        btn_set = (Button) this.findViewById(R.id.Button01);
        btn_cel = (Button) this.findViewById(R.id.Button02);
        c = Calendar.getInstance();
        btn_set.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {
                final EditText e =new EditText(NZActivity.this);
                new AlertDialog.Builder(NZActivity.this).setView(e).setTitle("输入提醒内容").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // TODO Auto-generated method stub
                        GlobalValue.TX =e.getText().toString();
                        new CDatePickerDialog(NZActivity.this, new CDatePickerDialog.OnDatePicked() {
                            @Override
                            public void callback(DatePicker view, final String year, final String month, String day) {
                                c.setTimeInMillis(System.currentTimeMillis());
                                int hour = c.get(Calendar.HOUR_OF_DAY);
                                int minute = c.get(Calendar.MINUTE);
                                new TimePickerDialog(NZActivity.this,new TimePickerDialog.OnTimeSetListener(){

                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        // TODO Auto-generated method stub
                                        c.setTimeInMillis(System.currentTimeMillis());
                                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        c.set(Calendar.MINUTE, minute);
                                        c.set(Calendar.SECOND, 0);
                                        c.set(Calendar.MILLISECOND, 0);
                                        c.set(Calendar.MONTH,Integer.parseInt(month)-1);
                                        c.set(Calendar.YEAR,Integer.parseInt(year));
                                        Intent intent = new Intent(NZActivity.this,AlamrReceiver.class);
                                        PendingIntent pi = PendingIntent.getBroadcast(NZActivity.this, 0, intent, 0);
                                        AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                                        am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);//设置闹钟
                                        am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), (10*1000), pi);//重复设置
                                    }

                                },hour,minute,true).show();
                            }
                        }).show();
                    }
                }).setNegativeButton("取消",null).show();




            }

        });
        btn_cel.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(NZActivity.this,AlamrReceiver.class);
                PendingIntent pi = PendingIntent.getBroadcast(NZActivity.this, 0, intent, 0);
                AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                am.cancel(pi);
               // tv.setText("闹钟取消");
            }

        });
    }
}