package com.example.administrator.myapplication.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.myapplication.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2016/11/16.
 */
public class MySocket extends Activity {
    Button socket;
    Button socketpost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socketdemo);
        socket= (Button) findViewById(R.id.btn_socket);
        socketpost= (Button) findViewById(R.id.btn_socketpost);
        socket.setOnClickListener(onClickListener);
        socketpost.setOnClickListener(onClickListener);

    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_socket:
                    new Thread(){
                        @Override
                        public void run() {
                            socketDemo();
                        }
                    }.start();
                    break;
                case R.id.btn_socketpost:
                    new  Thread(){
                        @Override
                        public void run() {
                            socketPost();
                        }
                    }.start();
                    break;
            }
        }
    };
String date="name=张三&age=14&tel=123456789";
    public void socketDemo(){
        try {
            Socket socket=new Socket("10.0.2.2",21800);
            socket.setSoTimeout(5000);
            StringBuilder stringBuilder=new StringBuilder();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream(),"gbk"));
            OutputStream outputStream=socket.getOutputStream();
            outputStream.write(date.getBytes("gbk"));
            outputStream.flush();
            outputStream.close();
            String str;
            if((str=bufferedReader.readLine())!=null){
                stringBuilder.append(str);
            }
            Log.i("socketDemo",stringBuilder.toString());
            bufferedReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void socketPost(){
        try {
            Socket socket=new Socket("10.0.2.2",21800);
            socket.setSoTimeout(5000);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
