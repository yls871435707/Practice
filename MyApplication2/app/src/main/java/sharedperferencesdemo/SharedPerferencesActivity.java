package sharedperferencesdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2016/11/7.
 */
public class SharedPerferencesActivity extends Activity {

    ImageView ivmenu;
    ImageView ivsousuo;
    ImageView ivadd;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedperferences);
        ivmenu = (ImageView) findViewById(R.id.iv_menu);
        ivsousuo = (ImageView) findViewById(R.id.iv_sousuo);
        ivadd = (ImageView) findViewById(R.id.iv_add);
        listView = (ListView) findViewById(R.id.list_item);
        ivadd.setOnClickListener(onClickListener);
    }

    //对数据的查找
    public void sharedPerferencesFind() {
        SharedPreferences sharedPreferences = getSharedPreferences("memorandum",Context.MODE_WORLD_READABLE);
        sharedPreferences.getString("memorandum", "未找到");
        Log.i("memorandum",""+sharedPreferences.getAll());
    }


    //对数据的删除
    public void sharedPerferencesRemove() {
SharedPreferences sharedPreferences=getSharedPreferences("memorandum", Context.MODE_WORLD_WRITEABLE);
        sharedPreferences.getString("memorandum", "未找到");
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_add:
                    Intent intent = new Intent(SharedPerferencesActivity.this, EdittextActivity.class);
                    startActivityForResult(intent,1001);
                    break;
                case R.id.iv_menu:
                    break;
                case R.id.iv_sousuo:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

