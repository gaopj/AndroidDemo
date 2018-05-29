package com.gpj.workmanagerdemo;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Data;
import androidx.work.Worker;

/**
 * Created by v-pigao on 5/29/2018.
 */

public class PullWorker extends Worker {

    @NonNull
    @Override
    public WorkerResult doWork() {
        Log.d(MainActivity.TAG,getId()+"  start");
        boolean canWork = getInputData().getBoolean("canWork",false);
        if(canWork){
            String pullResult = getInfo();
            try {
                Thread.sleep(5000);
                Data output = new Data.Builder().putString("key_pulled_result", pullResult).build();
                setOutputData(output);
                return WorkerResult.SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                return WorkerResult.FAILURE;
            }

        }else {
            return WorkerResult.FAILURE;
        }
    }

    String getInfo(){
        return "gpj-->>gogogo";
    }
}
