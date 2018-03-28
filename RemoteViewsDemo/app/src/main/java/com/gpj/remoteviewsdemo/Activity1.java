package com.gpj.remoteviewsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by v-pigao on 3/28/2018.
 */

public class Activity1 extends Activity {
    private static final String TAG = "DemoActivity_1";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_1);
        textView=findViewById(R.id.text);
        textView.setText(TAG+getIntent().getStringExtra("sid"));
    }

}
