package com.demo.bs.demoapp2.ui.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.stepcounter.StepCounterActivity;
import com.demo.bs.demoapp2.ui.activity.ChangePwdActivity;
import com.demo.bs.demoapp2.ui.activity.DAActivity;
import com.demo.bs.demoapp2.ui.activity.JBTZActivity;
import com.demo.bs.demoapp2.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminMainActivity extends BaseActivity {


    private ListView lv;
    private SimpleAdapter simpleAdapter;
    private List<HashMap<String,Object>> list;
    private String[] type = { "健康指数","数据录入","作息监测","计步器","个人档案","健康资讯","修改密码"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setToolbar("主页");
        for (int i=0;i<type.length;i++){
            HashMap <String,Object> hashMap =new HashMap<>();
            hashMap.put("type",type[i]);
            list.add(hashMap);
        }

        simpleAdapter.notifyDataSetChanged();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        break;
                    case 1:
                        go(JBTZActivity.class);
                        break;
                    case 2:

                        break;
                    case 3:
                        Intent intent = new Intent();
                        intent.setClass(AdminMainActivity.this, StepCounterActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("run", true);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 4:
                        go(DAActivity.class);
                        break;
                    case 5:

                        break;
                    case 6:
                        go(ChangePwdActivity.class);
                        break;
                }
            }
        });
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);
        list =new ArrayList<>();
        simpleAdapter =new SimpleAdapter(getApplicationContext(),list,R.layout.listitem_main,new String[]{"type"},new int[]{R.id.tv_content});
        lv.setAdapter(simpleAdapter);

    }
}
