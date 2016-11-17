package com.example.administrator.myapplication.service.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2016/11/14.
 */
public class ServiceTestActivity extends Activity implements View.OnClickListener {
    Button bind;
    Button invoke;
    TextView textView;
    MyService myService=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicetest);
        bind = (Button) findViewById(R.id.bind_servicetest);
        invoke = (Button) findViewById(R.id.invoke_servicetest);
        textView = (TextView) findViewById(R.id.set_string);
        bind.setOnClickListener(this);
        invoke.setOnClickListener(this);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            myService = (MyService) MyService.MyServiceImpl.Stub.asInterface(service);
            bind.setEnabled(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_servicetest:
                bindService(new Intent("com.example.administrator.myapplication.service.aidl"),serviceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.invoke_servicetest:

                break;

        }
    }


}
