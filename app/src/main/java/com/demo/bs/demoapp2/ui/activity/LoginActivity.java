package com.demo.bs.demoapp2.ui.activity;

import android.os.Bundle;
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

