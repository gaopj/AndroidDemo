package com.gpj.httpdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gpj.httpdemo.okHttp.OkHttpActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    Button button1;
    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useHttpUrlConnectionPostThread();
            }
        });
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OkHttpActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    /**
     * HttpUrlConnection POST请求网络
     */
    private void useHttpUrlConnectionPostThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                useHttpUrlConnectionPost("http://ip.taobao.com/service/getIpInfo.php");
            }
        }).start();
    }
    private void useHttpUrlConnectionPost(String url) {
        InputStream mInputStream = null;
        HttpURLConnection mHttpURLConnection = HttpURLConnManager.getHttpURLConnection(url);
        try {
            Map<String,String> postParams = new HashMap<>();
            //要传递的参数
            postParams.put("ip", "59.108.54.37");
            HttpURLConnManager.postParams(mHttpURLConnection.getOutputStream(), postParams);
            mHttpURLConnection.connect();
            mInputStream = mHttpURLConnection.getInputStream();
            int code = mHttpURLConnection.getResponseCode();
            String respose = HttpURLConnManager.converStreamToString(mInputStream);
            Log.d(TAG, "请求状态码:" + code + "\n请求结果:\n" + respose);
            mInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
