package com.gpj.mediaplayerdemo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gpj.mediaplayerdemo.mediaplayer.MediaPlayerException;
import com.gpj.mediaplayerdemo.mediaplayer.MediaPlayerHolder;
import com.gpj.mediaplayerdemo.mediaplayer.MinimalDisplay;

public class MainActivity extends Activity {
    private EditText mEditAddress;
    private SurfaceView mPlayerView;
    private MediaPlayerHolder player;

    private String url = "http://v3-hs.ixigua.com/13a6e36171696785a78f7aa68c8c80b9/5ac49234/video/m/2206d7f9dbbf3514d7f856b01910f048cd81142b98000005811497c0ab/";

    private Button mPlayBtn;
    private Button mPauseBtn;
    private Button mTypeBtn;
    private Button mForWard;
    private Button mBackWard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPlayer();
    }

    private void initView(){
        mEditAddress=  findViewById(R.id.mEditAddress);
        mEditAddress.setText(url);
        mPlayerView=  findViewById(R.id.mPlayerView);

        mPlayBtn = findViewById(R.id.mPlay);
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUrl=mEditAddress.getText().toString();
                if(mUrl.length()>0){
                    Log.d("gpj","播放->"+mUrl);
                    try {
                        player.setSource(mUrl);
                        player.play();
                    } catch (MediaPlayerException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mPauseBtn = findViewById(R.id.mPause);
        mPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.isPlaying()){
                    player.pause();
                }else{
                    try {
                        player.play();
                    } catch (MediaPlayerException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mTypeBtn = findViewById(R.id.mType);
        mTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setCrop(!player.isCrop());
            }
        });

        mForWard = findViewById(R.id.mForWard);
        mForWard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.forward();
            }
        });

        mBackWard = findViewById(R.id.mBackWard);
        mBackWard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.backWard();
            }
        });
    }

    private void initPlayer(){
        player=new MediaPlayerHolder();
        player.setDisplay(new MinimalDisplay(mPlayerView));
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.onDestroy();
    }
}
