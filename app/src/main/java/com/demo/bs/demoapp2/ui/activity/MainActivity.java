package com.demo.bs.demoapp2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.constants.AppConstant;
import com.demo.bs.demoapp2.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ListView lv;
    private SimpleAdapter simpleAdapter;
    private List<HashMap<String, Object>> list;
    private String[] type = {"健康趋势", "健康数据", "个人档案", "健康资讯", " 健康指数", "计步器"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setToolbar("主页");
        for (int i = 0; i < type.length; i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("type", type[i]);
            list.add(hashMap);
        }

        simpleAdapter.notifyDataSetChanged();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        //健康趋势
                        go(ZSActivity.class);
                        break;
                    case 1:
                        //基本特征
                        go(JBTZActivity.class);
                        break;
//                    case 2:
//                        //检测提醒
////                        Intent intent = new Intent();
////                        intent.setClass(MainActivity.this, com.loonggg.alarmmanager.clock.ClockMainActivity.class);
////                        startActivity(intent);
////                        go(NZActivity.class);
//                        break;
                    case 2:
                        //个人档案
                        go(DAActivity.class);
                        break;
                    case 3:
                        //健康咨询
                        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                        intent.putExtra(AppConstant.IntentKey.INTENT_TO_WEBVIEW_ACTIVITY_WITH_URL, "http://www.cnjkzxw.com/");
                        startActivity(intent);
                        break;
                    case 4:
                        //健康指数
                        go(JianKangZhiShuActivity.class);
                        break;

                    case 5:
                        //计步器
                        go(PedometerActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);
        list = new ArrayList<>();
        simpleAdapter = new SimpleAdapter(getApplicationContext(), list, R.layout.listitem_main, new String[]{"type"}, new int[]{R.id.tv_content});
        lv.setAdapter(simpleAdapter);

    }
}
