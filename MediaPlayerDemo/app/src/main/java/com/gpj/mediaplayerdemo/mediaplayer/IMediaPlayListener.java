package com.gpj.mediaplayerdemo.mediaplayer;

/**
 * Created by v-pigao on 4/4/2018.
 */

public interface IMediaPlayListener {
    void onStart(IMediaPlayer player);
    void onPause(IMediaPlayer player);
    void onResume(IMediaPlayer player);
    void onComplete(IMediaPlayer player);
}
