package bitmapdemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.administrator.myapplication.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/11/7.
 */
public class BitmapDemo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permission();
    }
    public void createBitmap() {
        //获取本地的图片资源文件
        //Log.i("Environment=========", ""+Environment.getExternalStorageDirectory());
        //Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/timg.jpg");
        //Log.i("bitmap", "bitmap=======" + bitmap);


//        BitmapFactory.Options options=new BitmapFactory.Options();
//        options.inJustDecodeBounds=true;
//        Log.i("outHeight",""+options.outHeight);
//        Log.i("outHeight",""+options.outWidth);
        //获取main里面的资源
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Log.i("bitmap1", "bitmap1=======" + bitmap1);
        //获取assets里面的静态资源
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("ic_launcher.png");
            Bitmap bitmap2 = BitmapFactory.decodeStream(inputStream);
            Log.i("bitmap2", "bitmap2=======" + bitmap2);
        } catch (IOException e) {
            e.printStackTrace();//打印堆栈信息
        }
    }
    //获取动态权限
    public void permission() {
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    , 1001);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.i("requestCode", "requestCode=======" + requestCode);
        createBitmap();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
