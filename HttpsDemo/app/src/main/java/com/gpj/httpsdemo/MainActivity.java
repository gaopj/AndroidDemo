package com.gpj.httpsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int result = Https.checkCertificate("https://www.baidu.com");
                Log.d("jpg","-->"+(result==0?"RESULT_SAFE":"RESULT_WRONG"));
            }
        }).start();

    }
}
