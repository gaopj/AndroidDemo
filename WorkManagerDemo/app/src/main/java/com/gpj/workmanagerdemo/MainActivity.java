package com.gpj.workmanagerdemo;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;
import androidx.work.Worker;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneTimeWorkRequest workerRequest = new OneTimeWorkRequest
                .Builder(PullWorker.class)
                .setInputData(new Data.Builder().putBoolean("canWork",true).build())
                .build();

        OneTimeWorkRequest workerRequest2 = new OneTimeWorkRequest
                .Builder(PullWorker.class)
                .setInputData(new Data.Builder().putBoolean("canWork",true).build())
                .build();

        OneTimeWorkRequest workerRequest3 = new OneTimeWorkRequest
                .Builder(PullWorker.class)
                .setInputData(new Data.Builder().putBoolean("canWork",true).build())
                .build();


        Log.d(TAG,"worker id: "+workerRequest.getId());
        WorkManager.getInstance().getStatusById(workerRequest.getId()).observe(this, new Observer<WorkStatus>() {
            @Override
            public void onChanged(@Nullable WorkStatus workStatus) {
                if(workStatus!=null){
                    Log.d(TAG,"worker output: "+workStatus.getState()+"-->"+workStatus.getOutputData().getString("key_pulled_result","nul"));
                }
            }
        });


       WorkContinuation continuation = WorkManager.getInstance().beginWith(workerRequest);
        continuation.then(workerRequest2,workerRequest3).enqueue();

    }
}
