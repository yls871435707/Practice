package com.example.administrator.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.myapplication.R;


/**
 * Created by Administrator on 2016/11/14.
 */
public class MyService extends Service {
    MyBinder myBinder = new MyBinder();
               //
    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }

        public void play() {
            MyService.this.playMusic();
        }
    }
                //播放音乐的方法
    public void playMusic() {
        MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.yskl);
        mediaPlayer.start();
        Log.i("MyService", "mediaPlayer");
    }

    @Nullable
    @Override//绑定一个service
    public IBinder onBind(Intent intent) {
        Log.i("MyService", "onBind");

        return myBinder;
    }

    @Override//解除绑定
    public boolean onUnbind(Intent intent) {
        Log.i("MyService", "onUnbind");

        return super.onUnbind(intent);
    }

    @Override//重新绑定
    public void onRebind(Intent intent) {
        Log.i("MyService", "onRebind");

        super.onRebind(intent);
    }


    @Override//创建service
    public void onCreate() {
        super.onCreate();
        Log.i("MyService", "onCreate");
    }

    @Override//开启一个service可以重复创建
    //                         intent对象     标记//可以在销毁的时候重启服务     每次service的id
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MyService", "onStartCommand");
        flags=START_STICKY;
        //playMusic();
        return START_STICKY;
        //返回的三种模式
        //START_REDELIVER_INTENT kill掉重新开启的时候会从新调用服务
        //START_STICKY kill掉会重新创建
        //START_NOT_STICKY kill掉不会重新创建
    }

    @Override//销毁service
    public void onDestroy() {
        Log.i("MyService", "onDestroy");

        super.onDestroy();
    }


}
