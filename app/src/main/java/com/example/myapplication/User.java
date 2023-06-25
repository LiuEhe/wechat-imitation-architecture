package com.example.myapplication;

import java.util.Set;

public class User {
    private String strusername; // 用户名
    private String strpassword; // 密码

    public User(){ // 无参构造函数

    }

    public User(String username,String password){ // 有参构造函数
        this.strusername=username; // 初始化用户名
        this.strpassword=password; // 初始化密码
    }

    public void setUsername(String username) { // 设置用户名
        this.strusername = username; // 更新用户名
    }

    public void setPassword(String password){ // 设置密码
        this.strpassword=password; // 更新密码
    }

    public String getusername(){ // 获取用户名
        return  strusername; // 返回用户名
    }

    public String getpassword(){ // 获取密码
        return strpassword; // 返回密码
    }
}

