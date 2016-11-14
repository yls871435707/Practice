package com.example.administrator.myapplication.sqlliterdemo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2016/11/8.
 */
public class MySQLiterDateActivity extends Activity {
    Button button;
    Button button1;
    Button button2;
    Button button3;
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqldate);
        button = (Button) findViewById(R.id.bt_found);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button.setOnClickListener(onClickListener);
        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
        button3.setOnClickListener(onClickListener);
        button4.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_found:
                    foundSQL();
                    break;
                case R.id.button1:
                    addSQL();
                    break;
                case R.id.button2:
                    updateSQL();
                    break;
                case R.id.button3:
                    deleteSQL();
                    break;
                case R.id.button4:
                    inquireSQL();
                    break;
            }
        }
    };
    SQLiteDatabase sqLiteDatabas;

    public void foundSQL() {
        MySQLiterDate sqLiterDate = new MySQLiterDate(this, "Contact.db", null, 1);
        sqLiteDatabas = sqLiterDate.getWritableDatabase();
    }

    int i = 1;

    public void addSQL() {
        //INSERT INTO 表名称 VALUES (值1, 值2,....)
        String add = "insert into Student values(2,'李四','123456789')";
        if (sqLiteDatabas != null) {
            //sqLiteDatabas.execSQL(add);
            ContentValues values = new ContentValues();
            values.put("id", i++);
            values.put("name", "张山");
            values.put("phone","14579634185");
            sqLiteDatabas.insert("Student", null, values);
        } else if (sqLiteDatabas == null) {
            foundSQL();
        }

    }

    public void updateSQL() {
        //UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
        String update = "update Student set name = '张三' where id = 2";
        if (sqLiteDatabas != null) {
            //sqLiteDatabas.execSQL(update);
            ContentValues values = new ContentValues();
            values.put("name", "李四");
            sqLiteDatabas.update("Student", values, "id=?", new String[]{"2"});
        } else if (sqLiteDatabas == null) {
            foundSQL();
        }
    }


    public void deleteSQL() {
        //DELETE FROM 表名称 WHERE 列名称 = 值
        String delete = "delete from Student where id = 2";
        if (sqLiteDatabas != null) {
            //sqLiteDatabas.execSQL(delete);
            sqLiteDatabas.delete("Student", "id=?", new String[]{"1"});
        } else if (sqLiteDatabas == null) {
            foundSQL();
        }
    }

    public void inquireSQL() {

        if (sqLiteDatabas == null) {
            foundSQL();
        }
            //SELECT 列名称 FROM 表名称
            Cursor cursor = sqLiteDatabas.rawQuery("select * from Student", null);

            if (!cursor.isFirst()) {
                cursor.isBeforeFirst();
//                int num = cursor.getColumnCount();
//                for (int i = 0; i < num; i++) {
//                    String name = cursor.getColumnName(i);
//                    String  = cursor.getString(i);
//                    Log.i("name========", name);
//                    Log.i("id========", id);
//                }
            }
            while (cursor.moveToNext()) {
                int num = cursor.getColumnCount();
                for (int i = 0; i < num; i++) {
                    String columnNamevalues = cursor.getColumnName(i);
                    String values = cursor.getString(i);
                    Log.i("columnNamevalues===", columnNamevalues);
                    Log.i("values========", values);
                }
            }
            cursor.close();
    }
}
