package com.gpj.breakpointdownloaddemo;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gpj.breakpointdownloaddemo.downloader.DbHelper;
import com.gpj.breakpointdownloaddemo.downloader.DownLoaderManager;
import com.gpj.breakpointdownloaddemo.downloader.FileInfo;

public class MainActivity extends AppCompatActivity implements DownLoaderManager.OnProgressListener {

    private NumberProgressBar pb;//进度条
    private DownLoaderManager downLoader = null;
    private FileInfo info;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(this);

        pb =  findViewById(R.id.pb);
        final Button start =  findViewById(R.id.start);//开始下载
        final Button restart =  findViewById(R.id.restart);//重新下载
        final DbHelper helper = new DbHelper(this);
        downLoader = DownLoaderManager.getInstance(helper, this);
        info = new FileInfo("gpj.apk", "http://downloadz.dewmobile.net/Official/Kuaiya482.apk");
        downLoader.addTask(info);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downLoader.getCurrentState(info.getUrl())) {
                    downLoader.stop(info.getUrl());
                    start.setText("开始下载");
                } else {
                    downLoader.start(info.getUrl());
                    start.setText("暂停下载");
                }
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoader.restart(info.getUrl());
                start.setText("暂停下载");
            }
        });
    }

    @Override
    public void updateProgress(final int max, final int progress) {
        pb.setMax(max);
        pb.setProgress(progress);
    }

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}