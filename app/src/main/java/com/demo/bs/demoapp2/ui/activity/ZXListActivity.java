package com.demo.bs.demoapp2.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.demo.bs.demoapp2.R;

import com.demo.bs.demoapp2.bean.ZXInfo;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.GlobalValue;
import com.demo.bs.demoapp2.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ZXListActivity extends BaseActivity {

    private ListView lv;
    private SimpleAdapter simpleAdapter;
    private List<HashMap<String, Object>> list;
    private List<ZXInfo> infos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setToolbar("健康咨询");
        initView();
        findViewById(R.id.btn_add).setVisibility(View.GONE);
        getData();

    }

    private void getData() {
        list.clear();
        infos.clear();
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "GetZXList") {
            @Override
            protected void onCallback(String json) {
                try {
                    Gson gson = new Gson();
                    infos= gson.fromJson(json, new TypeToken<List<ZXInfo>>() {
                    }.getType());
                    for (int i = 0; i < infos.size(); i++) {

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("type", "    " + infos.get(i).getNr()
                                +"\n\n发表日期:  "+infos.get(i).getDate()
                        );
                        list.add(map);
                    }
                    simpleAdapter.notifyDataSetChanged();
                } catch (Exception e) {

                }
            }
        };
        httpUtil.addParams("id", GlobalValue.getUserInfo().getId());
        httpUtil.sendGetRequest();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);
        list =new ArrayList<>();
        infos =new ArrayList<>();
        simpleAdapter =new SimpleAdapter(getApplicationContext(),list, R.layout.listitem_textitem,new String[]{"type"},new int[]{R.id.tv_content});
        lv.setAdapter(simpleAdapter);
    }

}
