package com.gpj.contentproviderdemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gpj.contentproviderdemo.contentprovider.People;

public class MainActivity extends AppCompatActivity {
    private EditText nameText;
    private EditText ageText;
    private EditText heightText;
    private EditText idEntry;
    private TextView labelView;
    private TextView displayView;
    private Button add;
    private Button queryAll;
    private Button clear;
    private Button del;
    private Button query;
    private Button deleteAll;
    private Button update;
    private ContentResolver resolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver = this.getContentResolver();
        initView();
        initEvent();
    }

    private void initEvent() {

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(People.KEY_NAME, nameText.getText().toString());
                values.put(People.KEY_AGE, Integer.parseInt(ageText.getText().toString()));
                values.put(People.KEY_HEIGHT, Float.parseFloat(heightText.getText().toString()));
                Uri newUri = resolver.insert(People.CONTENT_URI, values);
                labelView.setText("添加成功，URI:" + newUri);
            }
        });

        queryAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = resolver.query(People.CONTENT_URI,
                        new String[]{People.KEY_ID, People.KEY_NAME, People.KEY_AGE, People.KEY_HEIGHT},
                        null, null, null);
                if (cursor == null) {
                    labelView.setText("数据库中没有数据");
                    return;
                }
                labelView.setText("数据库：" + String.valueOf(cursor.getCount()) + "条记录");
                String msg= "";
                if (cursor.moveToFirst()) {
                    do {
                        msg += "ID: " + cursor.getString(cursor.getColumnIndex(People.KEY_ID)) + ",";
                        msg += "姓名: " + cursor.getString(cursor.getColumnIndex(People.KEY_NAME)) + ",";
                        msg += "年龄: " + cursor.getInt(cursor.getColumnIndex(People.KEY_AGE)) + ",";
                        msg += "身高: " + cursor.getFloat(cursor.getColumnIndex(People.KEY_HEIGHT)) + ",";
                    } while (cursor.moveToNext());
                }
                displayView.setText(msg);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse(People.CONTENT_URI_STRING + "/" + idEntry.getText().toString());
                int result = resolver.delete(uri, null, null);
                String msg = "删除ID为" + idEntry.getText().toString() + "的数据" + (result > 0 ? "成功" : "失败");
                labelView.setText(msg);
            }
        });

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolver.delete(People.CONTENT_URI, null, null);
            }
        });



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(People.KEY_NAME, nameText.getText().toString());
                values.put(People.KEY_AGE, Integer.parseInt(ageText.getText().toString()));
                values.put(People.KEY_HEIGHT, Float.parseFloat(heightText.getText().toString()));
                Uri uri = Uri.parse(People.CONTENT_URI_STRING + "/" + idEntry.getText().toString());
                int result = resolver.update(uri, values, null, null);
                String msg = "更新ID为" + idEntry.getText().toString() + "的数据" + (result > 0 ? "成功" : "失败");
                labelView.setText(msg);
            }
        });
    }

    private void initView() {
        nameText = findViewById(R.id.nameText);
        ageText = findViewById(R.id.ageText);
        heightText = findViewById(R.id.heightText);
        idEntry = findViewById(R.id.idEntry);

        labelView = findViewById(R.id.labelView);
        displayView = findViewById(R.id.displayView);

        add = findViewById(R.id.add);
        queryAll = findViewById(R.id.queryAll);
        clear = findViewById(R.id.clear);
        deleteAll = findViewById(R.id.deleteAll);
        update = findViewById(R.id.update);

    }
}