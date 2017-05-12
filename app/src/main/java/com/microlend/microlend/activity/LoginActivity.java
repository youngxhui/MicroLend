package com.microlend.microlend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.microlend.microlend.R;
import com.microlend.microlend.model.User;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private EditText username,password;
    private Button login;
    private TextView regist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username= (EditText) findViewById(R.id.lg_uname);
        password= (EditText) findViewById(R.id.lg_pwd);
        login= (Button) findViewById(R.id.bt_lg);
        regist= (TextView) findViewById(R.id.reg);
        regist.setOnClickListener(this);
        login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bt_lg:
                String passw=null;

                List<User> user= DataSupport.select("password").where("username=?",username.getText()
                        .toString()).find(User.class);

                Log.w(TAG, "onClick: size"+user.size() );
                for (User u:user
                     ) {

                    passw=u.getPassword();
                    MainActivity.userID=u.getId();
                }

                if (password.getText().toString().equals(passw)){

                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("name",username.getText().toString());
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "登录失败，请重新尝试", Toast.LENGTH_LONG).show();
                    username.setText("");
                    password.setText("");
                }


                break;
            case R.id.reg:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            default:
                break;
        }
    }
}
