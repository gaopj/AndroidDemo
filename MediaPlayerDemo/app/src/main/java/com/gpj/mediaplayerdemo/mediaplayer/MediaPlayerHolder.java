package com.gpj.mediaplayerdemo.mediaplayer;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.io.IOException;

/**
 * Created by v-pigao on 4/4/2018.
 */

public class MediaPlayerHolder implements IMediaPlayer{

    private MediaPlayer player;
    private String source;
    private IMediaDisplay display;

    private boolean isVideoSizeMeasured=false;  //视频宽高是否已获取，且不为0
    private boolean isMediaPrepared=false;      //视频资源是否准备完成
    private boolean isSurfaceCreated=false;     //Surface是否被创建
    private boolean isUserWantToPlay=false;     //使用者是否打算播放
    private boolean isResumed=false;            //是否在Resume状态

    private boolean mIsCrop=false;

    private IMediaPlayListener mPlayListener;

    private int currentVideoWidth;              //当前视频宽度
    private int currentVideoHeight;             //当前视频高度


    private MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener;
    private MediaPlayer.OnCompletionListener mOnCompletionListener;
    private MediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private MediaPlayer.OnPreparedListener mOnPreparedListener;
    private MediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener;
    private MediaPlayer.OnErrorListener mOnErrorListener;
    private SurfaceHolder.Callback mSurfaceHolderCallback;

    public  MediaPlayerHolder(){
        mOnBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                log("onBufferingUpdate()-> percent: "+percent);
            }
        };

        mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                display.onComplete(MediaPlayerHolder.this);
                if(mPlayListener!=null){
                    mPlayListener.onComplete(MediaPlayerHolder.this);
                }
            }
        };

        mOnVideoSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                log("视频大小被改变->"+width+"/"+height);
                if(width>0&&height>0){
                    MediaPlayerHolder.this.currentVideoWidth=width;
                    MediaPlayerHolder.this.currentVideoHeight=height;
                    tryResetSurfaceSize(display.getDisplayView(),width,height);
                    isVideoSizeMeasured=true;
                    playStart();
                }
            }
        };

        mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                log("视频准备完成");
                isMediaPrepared=true;
                playStart();
            }
        };

        mOnSeekCompleteListener = new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                log("onSeekComplete()");
            }
        };

        mOnErrorListener = new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                log("onError()");
                return false;
            }
        };

        mSurfaceHolderCallback = new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if(display!=null&&holder==display.getHolder()){
                    isSurfaceCreated=true;
                    //此举保证以下操作下，不会黑屏。（或许还是会有手机黑屏）
                    //暂停，然后切入后台，再切到前台，保持暂停状态
                    if(player!=null){
                        player.setDisplay(holder);
                        //不加此句360f4不会黑屏、小米note1会黑屏，其他机型未测
                        player.seekTo(player.getCurrentPosition());
                    }
                    log("surface被创建");
                    playStart();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                log("surface大小改变");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if(display!=null&&holder==display.getHolder()){
                    log("surface被销毁");
                    isSurfaceCreated=false;
                }
            }
        };
    }

    @Override
    public void setSource(String url) throws MediaPlayerException {
        this.source=url;
        createPlayerIfNeed();
        isMediaPrepared=false;
        isVideoSizeMeasured=false;
        currentVideoWidth=0;
        currentVideoHeight=0;
        player.reset();
        try {
            player.setDataSource(url);
            player.prepareAsync();
            log("异步准备视频");
        } catch (IOException e) {
            throw new MediaPlayerException("set source error",e);
        }
    }

    @Override
    public void setDisplay(IMediaDisplay display) {
        if(this.display!=null&&this.display.getHolder()!=null){
            this.display.getHolder().removeCallback(mSurfaceHolderCallback);
        }
        this.display=display;
        this.display.getHolder().addCallback(mSurfaceHolderCallback);
    }

    @Override
    public void play() throws MediaPlayerException {
        if(!checkPlay()){
            throw new MediaPlayerException("Please setSource");
        }
        createPlayerIfNeed();
        isUserWantToPlay=true;
        playStart();
    }

    @Override
    public void pause() {
        isUserWantToPlay=false;
        playPause();
    }

    @Override
    public void onPause() {
        isResumed=false;
        playPause();
    }

    @Override
    public void onResume() {
        isResumed=true;
        playStart();
    }

    @Override
    public void onDestroy() {
        if(player!=null){
            player.release();
        }
    }

    private void createPlayerIfNeed(){
        if(null==player){
            player=new android.media.MediaPlayer();
            player.setScreenOnWhilePlaying(true);
            player.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
            player.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
            player.setOnCompletionListener(mOnCompletionListener);
            player.setOnPreparedListener(mOnPreparedListener);
            player.setOnSeekCompleteListener(mOnSeekCompleteListener);
            player.setOnErrorListener(mOnErrorListener);
        }
    }

    //根据设置和视频尺寸，调整视频播放区域的大小
    private void tryResetSurfaceSize(final View view, int videoWidth, int videoHeight){
        ViewGroup parent= (ViewGroup) view.getParent();
        int width=parent.getWidth();
        int height=parent.getHeight();
        if(width>0&&height>0){
            final FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) view.getLayoutParams();
            if(mIsCrop){
                float scaleVideo=videoWidth/(float)videoHeight;
                float scaleSurface=width/(float)height;
                if(scaleVideo<scaleSurface){
                    params.width=width;
                    params.height= (int) (width/scaleVideo);
                    params.setMargins(0,(height-params.height)/2,0,(height-params.height)/2);
                }else{
                    params.height=height;
                    params.width= (int) (height*scaleVideo);
                    params.setMargins((width-params.width)/2,0,(width-params.width)/2,0);
                }
            }else{
                if(videoWidth>width||videoHeight>height){
                    float scaleVideo=videoWidth/(float)videoHeight;
                    float scaleSurface=width/height;
                    if(scaleVideo>scaleSurface){
                        params.width=width;
                        params.height= (int) (width/scaleVideo);
                        params.setMargins(0,(height-params.height)/2,0,(height-params.height)/2);
                    }else{
                        params.height=height;
                        params.width= (int) (height*scaleVideo);
                        params.setMargins((width-params.width)/2,0,(width-params.width)/2,0);
                    }
                }
            }
            view.setLayoutParams(params);
        }
    }

    private void playStart(){
        if(isVideoSizeMeasured&&isMediaPrepared&&isSurfaceCreated&&isUserWantToPlay&&isResumed){
            player.setDisplay(display.getHolder());
            player.start();
            log("视频开始播放");
            display.onStart(this);
            if(mPlayListener!=null){
                mPlayListener.onStart(this);
            }
        }
    }

    private void log(String content){
        Log.d("MediaPlayer",content);
    }

    private boolean checkPlay(){
        if(source==null|| source.length()==0){
            return false;
        }
        return true;
    }

    private void playPause(){
        if(player!=null&&player.isPlaying()){
            player.pause();
            display.onPause(this);
            if(mPlayListener!=null){
                mPlayListener.onPause(this);
            }
        }
    }

    /**
     * 视频状态
     * @return 视频是否正在播放
     */
    public boolean isPlaying(){
        return player!=null&&player.isPlaying();
    }

    /**
     * 设置是否裁剪视频，若裁剪，则视频按照DisplayView的父布局大小显示。
     * 若不裁剪，视频居中于DisplayView的父布局显示
     * @param isCrop 是否裁剪视频
     */
    public void setCrop(boolean isCrop){
        this.mIsCrop=isCrop;
        if(display!=null&&currentVideoWidth>0&&currentVideoHeight>0){
            tryResetSurfaceSize(display.getDisplayView(),currentVideoWidth,currentVideoHeight);
        }
    }

    public boolean isCrop(){
        return mIsCrop;
    }

    public void forward(){
        if(player != null){
            int position = player.getCurrentPosition();
            player.seekTo(position + 2000);
        }
    }

    public void backWard(){
        if(player != null){
            int position = player.getCurrentPosition();
            if(position > 2000){
                position-=2000;
            }else{
                position = 0;
            }
            player.seekTo(position);
        }
    }
}
