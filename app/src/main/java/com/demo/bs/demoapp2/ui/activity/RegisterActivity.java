package com.demo.bs.demoapp2.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.bs.demoapp2.DBBean.DBUser;
import com.demo.bs.demoapp2.DBBeanUtils.DBUserUtils;
import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.ui.base.BaseActivity;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText account;
    private EditText password;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setToolbar("注册");
    }


    private void initView() {
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                submit();
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
            DBUser dbUser2 = new DBUser();
            dbUser2.setUserNameAsId(accountString);
            dbUser2.setPassword(passwordString);
            DBUserUtils.getInstance().insertOneData(dbUser2);
            finish();
        } else {
            Toast.makeText(this, "此用户名已注册", Toast.LENGTH_SHORT).show();
        }
    }
}

