package com.gpj.mediaplayerdemo.mediaplayer;

import android.view.SurfaceHolder;
import android.view.View;

/**
 * Created by v-pigao on 4/4/2018.
 */

public interface IMediaPlayer {

    /**
     * 设置资源
     * @param url 资源路径
     * @throws MediaPlayerException
     */
    void setSource(String url) throws MediaPlayerException;

    /**
     * 设置显示视频的载体
     * @param display 视频播放的载体及相关界面
     */
    void setDisplay(IMediaDisplay display);

    /**
     * 播放视频
     * @throws MediaPlayerException
     */
    void play() throws MediaPlayerException;

    /**
     * 暂停视频
     */
    void pause();


    void onPause();
    void onResume();
    void onDestroy();
}
