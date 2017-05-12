package com.microlend.microlend.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.microlend.microlend.R;
import com.microlend.microlend.model.User;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ResetActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username,password,newpwd;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        username= (EditText) findViewById(R.id.tv_resuname);
        password= (EditText) findViewById(R.id.tv_pwd);
        newpwd= (EditText) findViewById(R.id.tv_respwd);
        submit= (Button) findViewById(R.id.button);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                if (checkinform()){
                    String passw=null;
                    List<User> user= DataSupport.select("password").where("username=?",username.getText().toString()).find(User.class);
                    for (User u:user
                            ) {
                        passw=u.getPassword();
                        if (password.getText().toString().equals(passw)){
                            u.setPassword(newpwd.getText().toString());
                            u.save();
                            Toast.makeText(ResetActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ResetActivity.this,MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(ResetActivity.this, "修改失败，请重新尝试", Toast.LENGTH_LONG).show();
                            username.setText("");
                            password.setText("");
                            newpwd.setText("");
                        }
                    }

                }
                break;
            default:
                break;
        }
    }
    public boolean checkinform() {

        if (TextUtils.isEmpty(username.getText().toString())) {
            username.setError("请输入账号");
            return false;
        } else if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("请输入密码");
            return false;
        }else if (TextUtils.isEmpty(newpwd.getText().toString())) {
            newpwd.setError("请输入密码");
            return false;
        }

        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(ResetActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
