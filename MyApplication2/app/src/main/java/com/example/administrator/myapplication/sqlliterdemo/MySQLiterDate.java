package com.example.administrator.myapplication.sqlliterdemo;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/11/8.
 */
public class MySQLiterDate extends SQLiteOpenHelper {


    public MySQLiterDate(Context context,//上下文
                         String name,//数据库名字
                         SQLiteDatabase.CursorFactory factory,//数据库工厂
                         int version) {//版本号
        super(context, name, factory, version);
    }

    public MySQLiterDate(Context context,
                         String name,
                         SQLiteDatabase.CursorFactory factory,
                         int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    String string = "create table Student(id integer primary key autoincrement,name varchar(200),phone varchar(200))";

    @Override//           数据库对象
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(string);
    }

    @Override//                数据库对象                旧版本   新版本
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
