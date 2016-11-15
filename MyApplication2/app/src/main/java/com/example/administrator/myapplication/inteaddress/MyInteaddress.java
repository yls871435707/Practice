package com.example.administrator.myapplication.inteaddress;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2016/11/15.
 */
public class MyInteaddress extends Activity {
    LinearLayout linearLayout;
    String[] dates={"inetaddress","Http","gethttp"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inteaddress);
        linearLayout= (LinearLayout) findViewById(R.id.root_linearlayout);
        for(int i=0;i<dates.length;i++){
            Button button=new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setText(dates[i]);
            button.setId(100+i);
            button.setOnClickListener(onClickListener);
            linearLayout.addView(button);
        }
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case 100:
                    new Thread(){
                        @Override
                        public void run() {
                            demoInetaddress();
                        }
                    }.start();
                    break;
                case 101:
                    new Thread(){
                        @Override
                        public void run() {
                            demoHttp();
                        }
                    }.start();
                    break;
                case 102:
                    new Thread(){
                        @Override
                        public void run() {
                            getHttp();
                        }
                    }.start();
                    break;
            }
        }
    };
    public void demoInetaddress(){//执行网络请求必须开启一个支线程
        try {
            InetAddress inetAddress=InetAddress.getByName("www.baidu.com");//在给定主机名（域名）的情况下确定主机的 IP 地址
            Log.i("inetAddress",inetAddress.getHostAddress());//返回 IP 地址字符串
            Log.i("inetAddress",""+inetAddress.isReachable(1000));//测试是否可以达到该地址。返回为boolean
        } catch (UnknownHostException e) {//未知主机异常
            e.printStackTrace();
        }catch (IOException e){//输入输出流异常
            e.printStackTrace();
        }
    }

    public void demoHttp(){
        try {
            URL url=new URL("http://apis.baidu.com/tianyiweather/basicforecast/weatherapi");//获取接口连接
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();//用HttpURLConnection类接收url打开的连接
            httpURLConnection.connect();//调用链接方法，建立本次http请求的链接
            if (httpURLConnection.getResponseCode() ==200){//返回码确认是否打开链接成功
                InputStream inputStream=httpURLConnection.getInputStream();//输入流去打开
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"utf-8");//设置读取的链接文本的格式  是字节流通向字符流的桥梁
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);//缓冲各个字符，从而实现字符、数组和行的高效读取
                StringBuilder stringBuilder=new StringBuilder();//一个可变的字符序列。
                String str;//定义一个String 用来接收bufferedReader.readLine()（读取一行字符）
                while ((str=bufferedReader.readLine())!=null){
                    stringBuilder.append(str);
                }
                Log.i("str",stringBuilder.toString());
                try {
                    String josn=stringBuilder.toString();
                    JSONObject josnObject=new JSONObject(josn);//Josn类取值
                    String errNum=josnObject.getString("errNum");
                    String errMsg=josnObject.getString("errMsg");
                    Log.i("errNum",errNum);
                    Log.i("errMsg",errMsg);
                }catch (JSONException e){//Josn找不到的异常
                    e.printStackTrace();
                }
            }else {
                Log.i("HttpURLConnection",""+httpURLConnection.getResponseCode());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void getHttp(){
        try {
            String httpUrl="http://apis.baidu.com/apistore/weatherservice/citylist";
            URL url=new URL(httpUrl+"?"+"cityname"+ URLEncoder.encode("重庆","utf-8"));
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setRequestProperty("apikey","ee54bec3cb1205afbc0eff53efce9139");
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode()==200){
                InputStream inputStream=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"utf-8");
                BufferedReader buffereReader=new BufferedReader(inputStreamReader);
                StringBuilder stringBuiler=new StringBuilder();
                String str;
                if((str=buffereReader.readLine())!=null){
                    stringBuiler.append(str);
                }
                Log.i("Date======>",""+stringBuiler.toString());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
