package com.example.administrator.myapplication.AsyncTask.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.AsyncTask.maodl.News;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */
public class MyAdapter extends BaseAdapter {
    Context context;
    List<News> lists;
    LayoutInflater layoutInflater;
    public MyAdapter(Context context, List<News> lists){
        this.context=context;
        this.lists=lists;
        this.layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHondler viewHondler=null;
        if (view==null){
            view=layoutInflater.inflate(R.layout.listview_news_item,null);
            viewHondler=new ViewHondler();
            viewHondler.ctime= (TextView) view.findViewById(R.id.tv_time);
            viewHondler.title= (TextView) view.findViewById(R.id.tv_title);
            viewHondler.description= (TextView) view.findViewById(R.id.tv_description);
            viewHondler.picUrl= (ImageView) view.findViewById(R.id.iv_image);
            view.setTag(viewHondler);
        } else {
            viewHondler= (ViewHondler) view.getTag();
        }
        News news=lists.get(i);
        viewHondler.ctime.setText(news.getCtime());
        viewHondler.title.setText(news.getTitle());
        viewHondler.description.setText(news.getDescription());
        MyHandler myHandler=new MyHandler();
        myHandler.setViewHondler(viewHondler);
        MyThread myThread=new MyThread();
        myThread.setMyHandler(myHandler);
        myThread.setIamgeURL(news.getPicUrl());
        myThread.start();
        return view;
    }
    class MyThread extends Thread{
        String iamgeURL;
        MyHandler myHandler;
        public void setMyHandler(MyHandler myHandler){
            this.myHandler=myHandler;
        }
            public void setIamgeURL(String iamgeURL){
            this.iamgeURL=iamgeURL;
        }
        public void run(){//耗时操作在线程
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
    class  MyHandler extends Handler{
        ViewHondler viewHondler;
        Bitmap bitmap;
        public void setBitmap(Bitmap bitmap){
            this.bitmap=bitmap;
        }
        public void setViewHondler(ViewHondler viewHondler){
            this.viewHondler=viewHondler;
        }
        public void handleMessage(Message message){
            if(bitmap!=null){
                viewHondler.picUrl.setImageBitmap(bitmap);
            }
        }
    }
    class ViewHondler{
        TextView ctime;
        TextView title;
        TextView description;
        ImageView picUrl;
        TextView url;
    }
}
