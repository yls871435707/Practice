package com.example.administrator.myapplication.inteaddress;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
    String[] dates={"inetaddress","Http","gethttp","get","post"};
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
                case 103:
                    break;
                case 104:
                    new Thread(){
                        @Override
                        public void run() {
                            demoPost();
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
            URL url=new URL("http://apis.baidu.com/apistore/weatherservice/citylist");//获取接口连接
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
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void getHttp(){
        try {
            String httpUrl="http://apis.baidu.com/apistore/weatherservice/citylist";
            URL url=new URL(httpUrl+"?"+"cityname="+ URLEncoder.encode("重庆","utf-8"));
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setRequestProperty("apikey","ee54bec3cb1205afbc0eff53efce9139");
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode()==200){
                StringBuilder stringBuiler=new StringBuilder();
                InputStream inputStream=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"utf-8");
                BufferedReader buffereReader=new BufferedReader(inputStreamReader);

                String str;
                if((str=buffereReader.readLine())!=null){
                    stringBuiler.append(str);
                }
                Log.i("Date======>",stringBuiler.toString());
                String date=stringBuiler.toString();

                try {
                    JSONObject jsonObject=new JSONObject(date);
                    JSONArray retData=jsonObject.getJSONArray("retData");
                    int errNum=jsonObject.optInt("errNum");
                    String errMsg=jsonObject.optString("errMsg");
                    Log.i("errNum",""+errNum);
                    Log.i("errMsg",errMsg);
                    for(int i=0;i<retData.length();i++){
                        JSONObject obj=retData.getJSONObject(i);
                        String province_cn=obj.optString("province_cn");
                       String district_cn=obj.optString("district_cn");
                        String name_cn=obj.optString("name_cn");
                        String name_en=obj.optString("name_en");
                        String area_id=obj.optString("area_id");

                        Log.i("province_cn",""+province_cn);
                        Log.i("district_cn",district_cn);
                        Log.i("name_cn",name_cn);
                        Log.i("name_en",name_en);
                        Log.i("area_id",area_id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Log.i("返回码异常",""+httpURLConnection.getResponseCode());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getUrl(){
        try {
            String httpUrl="http://apis.baidu.com/apistore/weatherservice/recentweathers";

            URL url=new URL(httpUrl+"?"+"cityname="+URLEncoder.encode("重庆","utf-8")+"cityid="+"101040200");

            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setRequestProperty("apikey","ee54bec3cb1205afbc0eff53efce9139");
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode()==200){
                StringBuilder stringBuilder=new StringBuilder();
                InputStream inputStream=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"utf-8");
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                String str;
                if ((str=bufferedReader.readLine())!=null){
                    stringBuilder.append(str);
                }


            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String params="{\n" +
            "\"params\": [\n" +
            "    {\n" +
            "      \"username\":\"test\",\n" +
            "      \"cmdid\":\"1000\",\n" +
            "      \"logid\": \"12345\",\n" +
            "      \"appid\": \"您的apikey\",\n" +
            "      \"clientip\":\"10.23.34.5\",\n" +
            "      \"type\":\"st_groupverify\",\n" +
            "      \"groupid\": \"0\",\n" +
            "      \"versionnum\": \"1.0.0.1\",\n" +
            "    }\n" +
            "  ],\n" +
            "  \"jsonrpc\": \"2.0\",\n" +
            "  \"method\": \"Delete\",\n" +
            "  \"id\":12\n" +
            "}";

    public void demoPost(){
        try {
            URL url=new URL("http://apis.baidu.com/idl_baidu/faceverifyservice/face_deleteuser");//添加url
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();//用HttpURLConnection类接收url打开的连接
            httpURLConnection.setDoInput(true);//设置允许输入
            httpURLConnection.setDoOutput(true);//设置允许输出
            httpURLConnection.setRequestMethod("POST");//要请求的类型
            httpURLConnection.setReadTimeout(5000);//等待的分钟数
            httpURLConnection.setRequestProperty("apikey","ee54bec3cb1205afbc0eff53efce9139");//apikey 自己的请求码
            OutputStream outputStream=httpURLConnection.getOutputStream();//获得输入流
            outputStream.write(params.getBytes("utf-8"));//将请求参数（bodyParams）写入到输入流中
            outputStream.flush();//刷新输出流
            outputStream.close();//关闭输出流
            httpURLConnection.connect();//调用链接方法，建立本次http请求的链接
if(httpURLConnection.getResponseCode()==200){//返回码为200
    StringBuilder stringBuilder=new StringBuilder();//一个可变的字符序列。
    InputStream inputStream=httpURLConnection.getInputStream();//打开输入流
    InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"utf-8");//设置读取的链接文本的格式  是字节流通向字符流的桥梁
    BufferedReader bufferedReader=new BufferedReader(inputStreamReader);//缓冲各个字符，从而实现字符、数组和行的高效读取
    String str;
    if((str=bufferedReader.readLine())!=null){
        stringBuilder.append(str);
    }
    Log.i("date=====>",stringBuilder.toString());//打印获取的返回信息
}else {
    Log.i("返回码异常",""+httpURLConnection.getResponseCode());
}

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
