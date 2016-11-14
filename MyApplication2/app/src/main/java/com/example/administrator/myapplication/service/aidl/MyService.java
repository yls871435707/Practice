package com.example.administrator.myapplication.service.aidl;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2016/11/14.
 */
public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyServiceImpl();
    }


    public class MyServiceImpl extends IMyService.Stub {

        @Override
        public void playMusic() throws RemoteException {
            MediaPlayer mediaPlayer = MediaPlayer.create(MyService.this, R.raw.yskl);
            mediaPlayer.start();
        }

        @Override
        public String getValue() throws RemoteException {
            return "Android is very powerful";
        }
    }

}
