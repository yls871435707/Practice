package com.example.administrator.myapplication.InputsteamandOutputsteam;

import android.app.Activity;
import android.os.Bundle;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/11/8.
 */
public class ReserveActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void openIntPut() {
        try {
            InputStream inputStream = this.openFileInput("nihao");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void openOuttPut() {
        try {
            OutputStream outputStream=this.openFileOutput("nihao",MODE_APPEND);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
