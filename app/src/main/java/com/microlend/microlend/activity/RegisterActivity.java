package com.microlend.microlend.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.microlend.microlend.R;
import com.microlend.microlend.model.User;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText user, pasw, repasw;
    private Button regist;
    private TextView login;
    private String username = null;
    private String pwd = null, pwd2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        user = (EditText) findViewById(R.id.rg_uname);
        pasw = (EditText) findViewById(R.id.rg_pwd);
        repasw = (EditText) findViewById(R.id.rg_pwd2);
        regist = (Button) findViewById(R.id.rg_btn);
        login = (TextView) findViewById(R.id.rg);
        login.setOnClickListener(this);
        regist.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rg_btn:
                checkinform();
                User users = new User();

                users.setUsername(user.getText().toString());
                users.setPassword(pasw.getText().toString());
                if (!users.save()) {
                    Toast.makeText(RegisterActivity.this, "注册失败，请重新尝试", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }
                break;
            case R.id.rg:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    public boolean checkinform() {
        username = user.getText().toString();
        pwd = pasw.getText().toString();
        pwd2 = repasw.getText().toString();

        if (TextUtils.isEmpty(username)) {
            user.setError("请输入账号");
            return false;
        } else if (TextUtils.isEmpty(pwd)) {
            pasw.setError("请输入密码");
            return false;
        } else if (TextUtils.isEmpty(pwd2)) {
            repasw.setError("请输入密码");
            return false;
        }
        if (!pwd.equals(pwd2)) {
            Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            pasw.setText("");
            repasw.setText("");
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
