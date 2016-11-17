package com.example.administrator.myapplication.AsyncTask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.administrator.myapplication.AsyncTask.adapter.MyAdapter;
import com.example.administrator.myapplication.AsyncTask.maodl.News;
import com.example.administrator.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/11/17.
 */
public class AsyncTaskDemo extends Activity {
    String[] buttonname={"startAsynctask"};
    LinearLayout linearLayout;
    ListView listView=null;
    List<News> lists=new ArrayList<News>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        linearLayout= (LinearLayout) findViewById(R.id.root_linearlayout);
        for(int i=0;i<buttonname.length;i++){
            Button button=new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setText(buttonname[i]);
            button.setId(100+i);
            button.setOnClickListener(onClickListener);
            linearLayout.addView(button);
        }

        listView=new ListView(this);
        listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.addView(listView);
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case 100:
                    startAsyncTask();
                    break;
            }
        }
    };
                                        //启动任务操作，进度参数，结果参数
    class MyAsyncTask extends AsyncTask<String,Integer,String>{
        @Override
        protected void onPreExecute() {
            //在myAsyncTask.execute调用后立即执行，一般用来在后台运行前对UI控件做一些标记
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {//执行UI操作
            try {
                JSONObject jsonObject= null;
                jsonObject = new JSONObject(s);
                Log.i("JSONObject",jsonObject.optString("msg"));
                JSONArray jsonArray=jsonObject.getJSONArray("newslist");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject obj=jsonArray.getJSONObject(i);
                    News news=new News();
                    news.setCtime(obj.getString("ctime"));
                    news.setTitle(obj.getString("title"));
                    news.setDescription(obj.getString("description"));
                    news.setPicUrl(obj.getString("picUrl"));
                    news.setUrl(obj.getString("url"));
                    lists.add(news);
                    MyAdapter myAdapter=new MyAdapter(AsyncTaskDemo.this,lists);
                    listView.setAdapter(myAdapter);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {//更新进度的
            super.onProgressUpdate(values);
        }

        @Override//所以的耗时操作都在此进行，不能进行UI控件操作
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder=new StringBuilder();
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestProperty("apikey","ee54bec3cb1205afbc0eff53efce9139");
                httpURLConnection.connect();
                if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));
                    String str;
                    if((str=bufferedReader.readLine())!=null){
                        stringBuilder.append(str);
                        return stringBuilder.toString();//返回数据给onPostExecute
                    }
                }else {
                    Log.i("Data======>",""+stringBuilder.toString());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;//返回数据给onPostExecute
        }
    }

    public void startAsyncTask(){
        MyAsyncTask myAsyncTask=new MyAsyncTask();
        String httpURL="http://apis.baidu.com/txapi/weixin/wxhot?num=20";
        myAsyncTask.execute(httpURL);
    }

    public void bufferImage(){
        News news;
        for (int i=0;i<lists.size();i++){
            news=new News();
            news.getPicUrl();
        }
        BmpToSd bmptosd=new BmpToSd();
        bmptosd.setIamgeURL();
    }
    public class BmpToSd extends Thread{
        MyHandler myHandler;
        String iamgeURL;
        public void setIamgeURL(String iamgeURL){
            this.iamgeURL=iamgeURL;
        }
        public void run(){
            try {
                URL url=new URL(iamgeURL);
                Bitmap bitmap= BitmapFactory.decodeStream(url.openStream());
                if(bitmap!=null){
                    myHandler.setBitmap(bitmap);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            myHandler.sendEmptyMessage(0);
        }
    }
    class  MyHandler extends Handler {
        Bitmap bitmap;
        public void setBitmap(Bitmap bitmap){
            this.bitmap=bitmap;
        }
        public void handleMessage(Message message){
           if (bitmap!=null){

           }
        }
    }
}
