package com.example.administrator.myapplication.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2016/11/14.
 */
public class ServiceDemoActivity extends Activity {
    LinearLayout servicedemo;
    String[] servicename = {"create service", "stop service", "bind service","unbind service"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicedemo);
        servicedemo = (LinearLayout) findViewById(R.id.service_root);
        for (int i = 0; i < servicename.length; i++) {
            Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setText(servicename[i]);
            button.setId(100 + i);
            button.setOnClickListener(clickListener);
            servicedemo.addView(button);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case 100:
                    create();
                    break;
                case 101:
                    stop();
                    break;
                case 102:
                    createBind();
                    break;
                case 103:
                    unBind();
                    break;
            }
        }
    };
    Intent intent;

    public void create() {
        intent = new Intent(ServiceDemoActivity.this, MyService.class);
        startService(intent);
    }

    public void stop() {
        stopService(intent);
    }

    public void createBind(){//绑定service
        Intent intent=new Intent(this,MyService.class);
                   //intent对象   绑定回调的对象   创建模式
        this.bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }
    public void unBind(){//取消绑定service
        //取消绑定不会回调onServiceDisconnected方法
        this.unbindService(connection);
    }

    ServiceConnection connection=new ServiceConnection() {
        @Override//service的绑定回调    具体组件名称已连接的服务   Binder对象
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("ServiceConnection","onServiceConnected");
          MyService.MyBinder myBinder= (MyService.MyBinder) iBinder;//通过MyService类拿到MyBinder对象并进行强转
           myBinder.play();
           //用拿到的对象调用paly();
        }

        @Override//service kill掉时的回调方法
                                         //具体组件名称已连接的服务
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
