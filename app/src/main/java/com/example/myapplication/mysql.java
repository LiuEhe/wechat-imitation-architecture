package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// 定义mysql类
public class mysql {
    // 定义数据库名、版本、表名、列名
    private static final  String DATABASE_NAME="data4";     //数据库名
    private static final int DATABASE_VERSION=1;        //版本号
    private static final String TABLE_NAME="user";      //表名
    private static final String ID="_id";               //id
    private static final String USERNAME="username";    //用户名
    private static final String PASSWORD="password";    //密码
    private DBOpenHelper helper;        //类变量
    private SQLiteDatabase db;          //类变量

    // 定义DBOpenHelper类
    private  class DBOpenHelper extends SQLiteOpenHelper{
        // 定义创建表的SQL语句
        private static final String CREATE_TABLE="create table "+TABLE_NAME+"("+USERNAME +" text not null, "+PASSWORD+ " text not null); ";
        public DBOpenHelper(Context context) {
            super(context,DATABASE_NAME ,null,DATABASE_VERSION );
        }

        // 创建表
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        // 更新表
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists "+TABLE_NAME);
            onCreate(db);
        }
    }

    // 构造函数
    public mysql(Context context){
        helper=new DBOpenHelper(context);
        db=helper.getWritableDatabase();
    }

    // 插入数据
    public void insert(User user){
        ContentValues values=new ContentValues();
        values.put(USERNAME,user.getusername());
        values.put(PASSWORD,user.getpassword());
        db.insert(TABLE_NAME,null,values);
    }

    // 查询数据
    public User query(String name){
        User user=new User();
        Cursor cursor=db.query(TABLE_NAME,new String[]{USERNAME,PASSWORD},"username=?",new String[]{name},null,null,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            user.setUsername(cursor.getString(0));
            user.setPassword(cursor.getString(1));
            return user;
        } else if (cursor.getCount()<=0) {
            user.setUsername("");
            user.setPassword("");
            return user;
        }

        cursor.close();
        return null;
    }
}

