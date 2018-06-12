package com.gpj.livedatademo;

import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.lang.ref.WeakReference;

public class MyLiveData extends LiveData<Integer> {
    private static final String TAG = "LiveData";
    private static MyLiveData sData;
    private WeakReference<Context> mContextWeakReference;

    public static MyLiveData getInstance(Context context){
        if (sData == null){
            sData = new MyLiveData(context);
        }
        return sData;
    }

    private MyLiveData(Context context){
        mContextWeakReference = new WeakReference<>(context);
    }

    @Override
    protected void onActive() {
        super.onActive();
        registerReceiver();

        Log.d(TAG, "MyLiveData: onActive()" );
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        unregisterReceiver();
        Log.d(TAG, "MyLiveData: onInactive()" );
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        mContextWeakReference.get().registerReceiver(mReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        mContextWeakReference.get().unregisterReceiver(mReceiver);
    }


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "action = " + action);
            if (WifiManager.RSSI_CHANGED_ACTION.equals(action)) {
                int wifiRssi = intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, -200);
                int wifiLevel = WifiManager.calculateSignalLevel(
                        wifiRssi, 4);
                sData.setValue(wifiLevel);
            }
        }
    };
}