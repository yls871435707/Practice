package com.example.administrator.myapplication.sharedperferencesdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2016/11/7.
 */
public class EdittextActivity extends Activity implements View.OnClickListener {
    EditText editText;
    ImageView iveixt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editext);
        editText = (EditText) findViewById(R.id.et_content);
        iveixt = (ImageView) findViewById(R.id.iv_exit);
        iveixt.setOnClickListener(this);
    }

    //对数据的添加
    public void sharePerferencesAdd() {
        String string = editText.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("memorandum", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("editText", string);
        editor.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_exit:
                sharePerferencesAdd();

                Intent intent = new Intent(EdittextActivity.this, SharedPerferencesActivity.class);
                startActivity(intent);
                break;
        }
    }
}
