package com.gpj.mediaplayerdemo.mediaplayer;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by v-pigao on 4/4/2018.
 */

public class MinimalDisplay implements IMediaDisplay {

    private SurfaceView surfaceView;

    public MinimalDisplay(SurfaceView surfaceView){
        this.surfaceView=surfaceView;
    }

    @Override
    public void onStart(IMediaPlayer player) {

    }

    @Override
    public void onPause(IMediaPlayer player) {

    }

    @Override
    public void onResume(IMediaPlayer player) {

    }

    @Override
    public void onComplete(IMediaPlayer player) {

    }

    @Override
    public View getDisplayView() {
        return surfaceView;
    }

    @Override
    public SurfaceHolder getHolder() {
        return surfaceView.getHolder();
    }
}
