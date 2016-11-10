package BroadcastReceiver;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2016/11/10.
 */
public class BroadcastReceiverActivity extends Activity {
    Button found;
    Button close;
    Button send;
    MyBroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcastreceiver);
        found = (Button) findViewById(R.id.btn_found);
        close = (Button) findViewById(R.id.btn_close);
        send = (Button) findViewById(R.id.btn_send);
        found.setOnClickListener(onClickListener);
        close.setOnClickListener(onClickListener);
        send.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_found:
                    createfound();
                    break;
                case R.id.btn_close:
                    createclose();
                    break;
                case R.id.btn_send:
                    createsend();
                    break;
            }
        }
    };

    public void createfound() {//创建动态广播
        IntentFilter intentFilter = new IntentFilter();//intentfilter过滤器
        intentFilter.addAction("789789");//添加动作
        this.registerReceiver(broadcastReceiver, intentFilter);
        Toast.makeText(this, "创建成功", Toast.LENGTH_SHORT);
    }

    public void createclose() {//关闭广播
        this.unregisterReceiver(broadcastReceiver);
    }

    public void createsend() {//发送广播
        Intent intent = new Intent();
        intent.putExtra("id", 100);
        intent.setAction("789789");
        sendBroadcast(intent);
    }
}
