package com.gpj.lifecycledemo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

public class LifecycleTest implements LifecycleObserver {
    public static final String TAG = "LifecycleTest";
    private Lifecycle lifecycle;

    public LifecycleTest(Lifecycle lifecycle){
        lifecycle.addObserver(this);
        this.lifecycle = lifecycle;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create(){
        Log.d(TAG,"LifecycleTest create");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start(){
        Log.d(TAG,"LifecycleTest start");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume(){
        Log.d(TAG,"LifecycleTest resume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void pause(){
        Log.d(TAG,"LifecycleTest pause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop(){
        Log.d(TAG,"LifecycleTest stop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy(){
        Log.d(TAG,"LifecycleTest destroy");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void any(){
        Log.d(TAG,"LifecycleTest any");
    }

    public void nowState(){
        Log.d(TAG,"LifecycleTest nowState: "+lifecycle.getCurrentState());
    }
}
