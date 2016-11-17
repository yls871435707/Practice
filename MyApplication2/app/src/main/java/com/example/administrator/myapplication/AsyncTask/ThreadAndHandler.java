package com.example.administrator.myapplication.AsyncTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.AsyncTask.adapter.MyAdapter;
import com.example.administrator.myapplication.AsyncTask.maodl.News;

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
public class ThreadAndHandler extends Activity {
    String[] buttonname={"addDate"};
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
                    new Thread(){
                        @Override
                        public void run() {
                            loadData();
                        }
                    }.start();
                    break;
            }
        }
    };


    public void loadData(){
        StringBuilder stringBuilder=new StringBuilder();
        try {
            URL url=new URL("http://apis.baidu.com/txapi/weixin/wxhot?"+"num=20");
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("apikey","ee54bec3cb1205afbc0eff53efce9139");
            httpURLConnection.setConnectTimeout(1000);
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));
                String str;
                if((str=bufferedReader.readLine())!=null){
                    stringBuilder.append(str);
                }
            }else {
                Log.i("Data======>",""+stringBuilder.toString());
            }

            JSONObject jsonObject=new JSONObject(stringBuilder.toString());
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
            }


handler.sendEmptyMessage(0);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MyAdapter myAdapter=new MyAdapter(ThreadAndHandler.this,lists);
            listView.setAdapter(myAdapter);
        }
    };
}
