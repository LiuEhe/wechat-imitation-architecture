package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class register extends AppCompatActivity {
    Button regist;
    Button back;
    EditText password;
    EditText account;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        regist=(Button)findViewById(R.id.reg);
        back=(Button)findViewById(R.id.back);
        password=(EditText) findViewById(R.id.mima);
        account=(EditText) findViewById(R.id.yonghu);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pd = password.getText().toString();
                String ue = account.getText().toString();

                if (!pd.isEmpty() & !ue.isEmpty()) {
                    mysql helper = new mysql(register.this);
                    User user = new User(ue, pd);
                    helper.insert(user);
                    Toast.makeText(register.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(register.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(pd.isEmpty()&!ue.isEmpty()){
                    Toast.makeText(register.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                }
                else if (!pd.isEmpty()&ue.isEmpty()){
                    Toast.makeText(register.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(register.this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
