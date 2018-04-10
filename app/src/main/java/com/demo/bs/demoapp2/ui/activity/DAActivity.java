package com.demo.bs.demoapp2.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.bs.demoapp2.DBBean.DBUserInfoBean;
import com.demo.bs.demoapp2.DBBeanUtils.DBUserInfoBeanUtils;
import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.ui.base.BaseActivity;

import java.util.List;

public class DAActivity extends BaseActivity implements View.OnClickListener {
    private EditText edt_name;
    private EditText edt_sex;
    private EditText edt_sg;
    private EditText edt_tz;
    private EditText edt_ssy;
    private EditText edt_szy;
    private EditText edt_kfxt;
    private EditText edt_fhxt;
    private Button btn_submit;
    private EditText edt_gm;
    private EditText edt_zb;
    private EditText edt_old;
    private DBUserInfoBean dbUserInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_da);
        initView();
        setToolbar("个人档案");
    }


    private void initView() {
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_sex = (EditText) findViewById(R.id.edt_sex);
        edt_sg = (EditText) findViewById(R.id.edt_sg);
        edt_tz = (EditText) findViewById(R.id.edt_tz);
        edt_ssy = (EditText) findViewById(R.id.edt_ssy);
        edt_szy = (EditText) findViewById(R.id.edt_szy);
        edt_kfxt = (EditText) findViewById(R.id.edt_kfxt);
        edt_fhxt = (EditText) findViewById(R.id.edt_fhxt);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        edt_old = (EditText) findViewById(R.id.edt_old);

        btn_submit.setOnClickListener(this);
        edt_gm = (EditText) findViewById(R.id.edt_gm);
        edt_gm.setOnClickListener(this);
        edt_zb = (EditText) findViewById(R.id.edt_zb);
        edt_zb.setOnClickListener(this);
        edt_old.setOnClickListener(this);

        getdate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String name = edt_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "姓名", Toast.LENGTH_SHORT).show();
            return;
        }

        String sex = edt_sex.getText().toString().trim();
        if (TextUtils.isEmpty(sex)) {
            Toast.makeText(this, "性别", Toast.LENGTH_SHORT).show();
            return;
        }

        String sg = edt_sg.getText().toString().trim();
        if (TextUtils.isEmpty(sg)) {
            Toast.makeText(this, "身高", Toast.LENGTH_SHORT).show();
            return;
        }

        String tz = edt_tz.getText().toString().trim();
        if (TextUtils.isEmpty(tz)) {
            Toast.makeText(this, "体重", Toast.LENGTH_SHORT).show();
            return;
        }

        String ssy = edt_ssy.getText().toString().trim();
        if (TextUtils.isEmpty(ssy)) {
            Toast.makeText(this, "收缩压", Toast.LENGTH_SHORT).show();
            return;
        }

        String szy = edt_szy.getText().toString().trim();
        if (TextUtils.isEmpty(szy)) {
            Toast.makeText(this, "舒张压", Toast.LENGTH_SHORT).show();
            return;
        }

        String kfxt = edt_kfxt.getText().toString().trim();
        if (TextUtils.isEmpty(kfxt)) {
            Toast.makeText(this, "空腹血糖", Toast.LENGTH_SHORT).show();
            return;
        }

        String fhxt = edt_fhxt.getText().toString().trim();
        if (TextUtils.isEmpty(fhxt)) {
            Toast.makeText(this, "饭后血糖", Toast.LENGTH_SHORT).show();
            return;
        }
        String gm = edt_gm.getText().toString().trim();
        if (TextUtils.isEmpty(gm)) {
            Toast.makeText(this, "过敏药物", Toast.LENGTH_SHORT).show();
            return;
        }
        String zb = edt_zb.getText().toString().trim();
        if (TextUtils.isEmpty(zb)) {
            Toast.makeText(this, "历史疾病", Toast.LENGTH_SHORT).show();
            return;
        }

        String old = edt_old.getText().toString().trim();

        if (TextUtils.isEmpty(old)) {
            Toast.makeText(this, "年龄", Toast.LENGTH_SHORT).show();
            return;
        }

        //个人信息保存在本地数据库中 start
        DBUserInfoBean dbUserInfoBean = new DBUserInfoBean();
        dbUserInfoBean.setUserId(1);
        dbUserInfoBean.setName(name);
        dbUserInfoBean.setSex(sex);
        dbUserInfoBean.setNianLing(old);
        dbUserInfoBean.setShenGao(sg);
        dbUserInfoBean.setTiZhong(tz);
        dbUserInfoBean.setShouSuoYa(ssy);
        dbUserInfoBean.setShuZhangYa(szy);
        dbUserInfoBean.setKongFuXueTang(kfxt);
        dbUserInfoBean.setFanHouXueTang(fhxt);
        dbUserInfoBean.setGuoMinYaoWu(gm);
        dbUserInfoBean.setLiShiJiBing(zb);
        DBUserInfoBeanUtils.getInstance().insertOneData(dbUserInfoBean);
        Toast.makeText(getApplicationContext(), "本地数据库保存数据成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void getdate() {
        List<DBUserInfoBean> dbUserInfoBeanList = DBUserInfoBeanUtils.getInstance().queryAllData();
        if (dbUserInfoBeanList != null && dbUserInfoBeanList.size() > 0) {
            dbUserInfoBean = dbUserInfoBeanList.get(dbUserInfoBeanList.size() - 1);
            edt_fhxt.setText(dbUserInfoBean.getFanHouXueTang());
            edt_kfxt.setText(dbUserInfoBean.getKongFuXueTang());
            edt_name.setText(dbUserInfoBean.getName());
            edt_sex.setText(dbUserInfoBean.getSex());
            edt_ssy.setText(dbUserInfoBean.getShouSuoYa());
            edt_szy.setText(dbUserInfoBean.getShuZhangYa());
            edt_tz.setText(dbUserInfoBean.getTiZhong());
            edt_sg.setText(dbUserInfoBean.getShenGao());
            edt_gm.setText(dbUserInfoBean.getGuoMinYaoWu());
            edt_zb.setText(dbUserInfoBean.getLiShiJiBing());
            edt_old.setText(dbUserInfoBean.getNianLing());
        }
    }
}
