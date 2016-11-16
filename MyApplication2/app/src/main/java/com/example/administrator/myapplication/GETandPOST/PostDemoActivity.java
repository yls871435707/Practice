package com.example.administrator.myapplication.GETandPOST;

import android.annotation.TargetApi;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/11/16.
 */
public class PostDemoActivity extends Activity {
    Button button;
    String url;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdemo);
        button= (Button) findViewById(R.id.btn_post);
        final Map<String,String> map=new ArrayMap<String, String>();
        map.put("tel","18716341029");
        map.put("password","19831004");
            url="http://211.149.190.90/api/uploads";
StringBuffer stringBuffer=new StringBuffer();

button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        new Thread(){
            @Override
            public void run() {
               userRegisterPort(url,map);
            }
        }.start();
    }
});
    }
                            //post提交form-data类型的数据name=value
    public void userRegisterPort(String httpUrl, Map<String,String> map){
        StringBuilder stringBuilder=new StringBuilder();
        String end = "\r\n";//结束符【换行】
        String twoHyphens = "--";//分隔符开头字符
        String boundary = "SJDASJODAODASSD";
        URL url = null;
        try {
            url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();//打开连接对象
            connection.setDoOutput(true);//设置允许输出
            connection.setUseCaches(false);//设置不缓存
            connection.setConnectTimeout(10000); // 连接超时为10秒
            connection.setRequestMethod("POST");//设置请求方式
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);// 设置请求数据类型并设置boundary部分；
            connection.connect();//开启连接
            DataOutputStream ds = new DataOutputStream(connection.getOutputStream());//获得输出流的对象
            Set<Map.Entry<String, String>> paramEntrySet = map.entrySet();//将map转换成set集合
            Iterator paramIterator = paramEntrySet.iterator();//set集合的迭代器
            while (paramIterator.hasNext()) {//循环取出key和value
                Map.Entry<String, String> entry = (Map.Entry<String, String>) paramIterator.next();
                String key = entry.getKey();//取得key值
                String value = entry.getValue();//取得value的值
                ds.writeBytes(twoHyphens + boundary + end);//--SJDASJODAODASSD
                ds.writeBytes("Content-Disposition: form-data; " + "name=\"" + key + "\"" + end + end + value);//Content-Disposition: form-data; name="key"
                ds.writeBytes(end);
                ds.writeBytes(twoHyphens + boundary + twoHyphens + end);//--SJDASJODAODASSD
                ds.flush();//刷新数据
            }
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {//获得响应状态码
                InputStream inputStream=connection.getInputStream();
                InputStreamReader inputstreamReader=new InputStreamReader(inputStream,"utf-8");
                BufferedReader bufferedReader=new BufferedReader(inputstreamReader);
                String str;
                if((str=bufferedReader.readLine())!=null){
                    stringBuilder.append(str);
                }
                Log.i("Post", "result===========>" + stringBuilder.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            String status=jsonObject.optString("status","");
            String message = jsonObject.optString("message", "");
            String token = jsonObject.optString("token","" );
            String uid = jsonObject.optString("uid","" );
            String tel=jsonObject.optString("tel","");
            String username=jsonObject.optString("username","");
            Log.i("Post", "message=" + message);
            Log.i("Post", "status=" + status);
            Log.i("Post", "token=" + token);
            Log.i("Post", "uid=" + uid);
            Log.i("Post", "tel=" + tel);
            Log.i("Post", "username=" + username);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void uploadImage(String httpUrl, final Map<String, String> map, final InputStream inputStream) {
        // TODO Auto-generated method stub
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader in = null;
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(10000); // 连接超时为10秒
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);// 设置请求数据类型并设置boundary部分；
            connection.connect();
            //获取输出流
            DataOutputStream ds = new DataOutputStream(
                    connection.getOutputStream());
            Set<Map.Entry<String, String>> paramEntrySet = map.entrySet();
            Iterator paramIterator = paramEntrySet.iterator();
            while (paramIterator.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) paramIterator.next();
                String key = entry.getKey();
                String value = entry.getValue();
                ds.writeBytes(twoHyphens + boundary + end);
                ds.writeBytes("Content-Disposition: form-data; " + "name=\"" + key + "\"" + end + end + value);
                ds.writeBytes(end);
                ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
                ds.flush();
            }
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; " + "name=\"file" + "\";filename=\"" + "image1.png" + "\"" + end);
            ds.writeBytes("Content-type:application/octet-stream");
            ds.writeBytes(end);
            // * 取得文件的FileInputStream *//*
//			FileInputStream fStream = new FileInputStream(
//					file.getAbsolutePath());
            // * 设置每次写入1024bytes *//*
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            // * 从文件读取数据至缓冲区 *//*
            while ((length = inputStream.read(buffer)) != -1) {
                // * 将资料写入DataOutputStream中 *//*
                ds.write(buffer, 0, length);
                ds.flush();//刷新数据
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            ds.flush();
            ds.close();
            inputStream.close();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                char[] buf = new char[1024];
                int len = -1;
                while ((len = in.read(buf, 0, buf.length)) != -1) {
                    stringBuilder.append(buf, 0, len);
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.i("Upload", "result===========>" + stringBuilder.toString());
        try {
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            String message = jsonObject.optString("message", "");
            Log.i("Upload", "message=" + message);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
