package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.content.SharedPreferences;
import android.widget.CheckBox;


public class MainActivity extends AppCompatActivity {
    Button login,register;
    CheckBox checkBox;
    EditText account, password_lz;

    //    关联按键
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById (R.id.login);
        register = (Button) findViewById (R.id.quit);

        account = (EditText)findViewById(R.id.text_account1);
        password_lz = (EditText)findViewById(R.id.text_password1);

        checkBox=(CheckBox) findViewById(R.id.checkBox);

        // 设置登录按钮的点击事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建mysql对象
                mysql helper=new mysql(MainActivity.this);
                // 获取输入的密码和用户名
                String mima0 = password_lz.getText().toString();
                String yonghu0 = account.getText().toString();
                // 判断输入是否为空
                if (!mima0.isEmpty()&!yonghu0.isEmpty()) {
                    // 查询数据库中是否有该用户
                    User users = helper.query(yonghu0);
                    String mima = users.getpassword();
                    String yonghu = users.getusername();
                    // 判断密码和用户名是否正确
                    if (!mima.isEmpty() & !yonghu.isEmpty()) {
                        if (mima0.equals(mima) & yonghu0.equals(yonghu)) {
                            // 如果勾选了记住密码，则将用户名和密码存入SharedPreferences中
                            if (checkBox.isChecked()) {
                                SharedPreferences sp = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
                                SharedPreferences.Editor edt = sp.edit();
                                edt.putString("user", account.getText().toString());
                                edt.putString("password", password_lz.getText().toString());
                                edt.commit();
                            } else {
                                // 如果没有勾选，则将SharedPreferences中的用户名和密码清空
                                SharedPreferences sp = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
                                SharedPreferences.Editor edt = sp.edit();
                                edt.putString("user", null);
                                edt.putString("password", null);
                                edt.commit();
                            }
                            // 登录成功，跳转到微信界面
                            Toast toast = Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT);
                            toast.show();
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, chat_activity_main.class);
                            startActivity(intent);
                            finish();
                        } else if (!mima0.equals(mima) & yonghu0.equals(yonghu)) {
                            // 密码错误
                            Toast toast = Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT);
                            toast.show();
                        } else if (mima0.equals(mima) & !yonghu0.equals(yonghu)) {
                            // 用户名错误
                            Toast.makeText(MainActivity.this, "用户名错误", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // 找不到该用户，请先注册
                        Toast.makeText(MainActivity.this, "找不到用户名，请先注册", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                }
            }

        });

        //注册跳转页面
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent();
                intent.setClass(MainActivity.this,register.class);
                startActivity(intent);
                finish();
            }
        });
        getdata();

    }

    //获取数据
    private void getdata() {
        SharedPreferences sp=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        account.setText(sp.getString("user",""));
        password_lz.setText(sp.getString("password",""));
    }
}