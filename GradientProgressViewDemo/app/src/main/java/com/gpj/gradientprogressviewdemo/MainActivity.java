package com.gpj.gradientprogressviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private GradientProgressView mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = (GradientProgressView) findViewById(R.id.progress);
//        mProgress.setProgress(100);
    }

    public void onStartAnimation(View view) {
//        mProgress.startAnimation();
        mProgress.startAnimation(100);
    }
}
