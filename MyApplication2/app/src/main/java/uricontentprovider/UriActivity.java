package uricontentprovider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2016/11/10.
 */
public class UriActivity extends Activity {
    Button button;
    EditText name;
    EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uri);
        button = (Button) findViewById(R.id.btn_add);
        name = (EditText) findViewById(R.id.tv_name);
        number = (EditText) findViewById(R.id.tv_number);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addphone(UriActivity.this,name.getText().toString(),number.getText().toString());
            }
        });
        onread();
    }


    public void readpeople(Context context) {

        ContentResolver contentResolver = context.getContentResolver();//创建解析器
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);//创建一个游标并找到系统的表uri

        if (cursor.getCount() > 0) {//如果游标的大小大于0就做循环
            while (cursor.moveToNext()) {//游标调到下一行
                String rawcontentsId = "";//初始化一个rawcontents的表的id
                int colmuNmb = cursor.getColumnIndex(ContactsContract.Contacts._ID);//找到游标里面的每一项子项的id
                String id = cursor.getString(colmuNmb);//找到游标里面的每一项子项
                Cursor rawcontentsCursor = contentResolver.query(ContactsContract.RawContacts.CONTENT_URI,//创建下一个游标找寻里面的子项id
                        null, ContactsContract.RawContacts._ID + "=?", new String[]{id}, null);
                if (rawcontentsCursor.moveToFirst()) {//找到id之后光标调到第一行
                    rawcontentsId = rawcontentsCursor.getString(rawcontentsCursor.getColumnIndex(ContactsContract.RawContacts._ID));//找到rawcontents的每一项id

                }
                rawcontentsCursor.close();//找到之后进行关闭游标
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {//判断rawconents里面的联系人大于0
                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,//创建查找联系人数据的游标
                            null,
                            ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID + "+?",
                            new String[]{rawcontentsId}, null);


                            while (phoneCursor.moveToNext()) {//找到游标之后循环遍历
                        String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));//找到手机号码
                        String name = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));//找到对应的名字
                        Log.i("name", "" + name);
                        Log.i("number", "" + number);

                    }
                    phoneCursor.close();//关闭查找联系人游标
                }
            }
        }
        cursor.close();//关闭查找系统表

    }


    public long addphone(Context context, String name, String number) {//添加联系人数据
        ContentValues values = new ContentValues();//创建contentvalues添加数据
        Uri cotnenturi = context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);//拿到上下文中rawcotnents的表路径，并添加一条空数据
        long rawcontentsId = ContentUris.parseId(cotnenturi);//
        if (name != null) {
            values.clear();
            values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawcontentsId);//找到添加name的id
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);//找到添加的数据类型是name
            values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);//找到要添加的数据value对应的key
            context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);//插入数据
        }
        if (number != null) {
            values.clear();
            values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawcontentsId);//找到对应的id
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);//找到添加的数据类型是number
            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number);//找到要添加的数据value对应的key
            values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);//设置电话的类型为手机
            context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);//插入数据
        }
        return rawcontentsId;
    }

    public void onread() {
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_CONTACTS}, 1001);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1001) {
            readpeople(this);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
