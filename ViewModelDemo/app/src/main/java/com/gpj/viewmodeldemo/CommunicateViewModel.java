package com.gpj.viewmodeldemo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

public class CommunicateViewModel extends ViewModel {
    private static final String TAG = "CommunicateViewModel";
    private MutableLiveData<String> mNameLiveData;

    public LiveData<String> getName(){
        if (mNameLiveData == null) {
            mNameLiveData = new MutableLiveData<>();
        }
        return mNameLiveData;
    }

    public void setName(String name){
        if (mNameLiveData != null) {
            Log.d(TAG, "setName: " + name);
            mNameLiveData.setValue(name);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mNameLiveData = null;
    }
}
