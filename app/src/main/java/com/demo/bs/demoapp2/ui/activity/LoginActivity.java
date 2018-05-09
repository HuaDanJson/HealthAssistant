package com.demo.bs.demoapp2.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.demo.bs.demoapp2.DBBean.DBUser;
import com.demo.bs.demoapp2.DBBeanUtils.DBUserUtils;
import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.ToastHelper;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText account;
    private EditText password;
    private Button btn_register;
    private Button btn_login;
    private RadioButton rbt_admin;
    private RadioButton rbt_use;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setToolbar("登陆");
        getGetWritePermission();
    }

    public void getGetWritePermission() {
        if (Build.VERSION.SDK_INT > 21) {
            String callPhone = Manifest.permission.CAMERA;
            String writestorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            String readstorage = Manifest.permission.READ_EXTERNAL_STORAGE;
            String readsound = Manifest.permission.RECORD_AUDIO;
            String[] permissions = new String[]{callPhone, writestorage, readstorage, readsound};
            int selfPermission = ActivityCompat.checkSelfPermission(this, callPhone);
            int selfwrite = ActivityCompat.checkSelfPermission(this, writestorage);
            int selfread = ActivityCompat.checkSelfPermission(this, readstorage);
            int selfsound = ActivityCompat.checkSelfPermission(this, readsound);
            if (selfPermission != PackageManager.PERMISSION_GRANTED || selfwrite != PackageManager.PERMISSION_GRANTED || selfread != PackageManager.PERMISSION_GRANTED || selfsound != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    private void initView() {
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        rbt_admin = (RadioButton) findViewById(R.id.rbt_admin);
        rbt_use = (RadioButton) findViewById(R.id.rbt_use);
        rbt_use.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                go(RegisterActivity.class);
                break;
            case R.id.btn_login:
                submit();
                break;
            default:
                break;
        }
    }

    private void submit() {
        String accountString = account.getText().toString().trim();
        if (TextUtils.isEmpty(accountString)) {
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String passwordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(passwordString)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        DBUser dbUser = DBUserUtils.getInstance().queryOneData(accountString);
        if (dbUser == null) {
            ToastHelper.showShortMessage("无效的用户");
        } else if (passwordString.equals(dbUser.getPassword())) {
            go(MainActivity.class);
            finish();
        } else if (!(passwordString.equals(dbUser.getPassword()))) {
            ToastHelper.showShortMessage("密码错误");
        }
    }
}

