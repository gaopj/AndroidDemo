package com.gpj.remoteviewsdemo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity-->";

    private static int mNotifyId = 0;

    private static final String channelName = "my_package_channel";
    private static final String channelId = "my_package_channel_1"; // The user-visible name of the channel.
    private static final String channelDescription = "my_package_first_channel"; // The user-visible description of the channel.


    NotificationManager notificationManager;

    private Button mDefaultNotifyBtn;
    private Button mCustomNotifyBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(notificationManager==null){
            notificationManager  = (NotificationManager) MainActivity.this.getSystemService(NOTIFICATION_SERVICE);
        }

        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = notificationManager.getNotificationChannel(channelId);
            if (mChannel == null) {
                mChannel = new NotificationChannel(channelId, channelName, importance);
                mChannel.setDescription(channelDescription);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(mChannel);
            }
        }
        initView();
        initEvent();
    }

    void initView(){
        mDefaultNotifyBtn = findViewById(R.id.default_notify_btn);
        mCustomNotifyBtn = findViewById(R.id.custom_notify_btn);
    }

    void initEvent(){
        mDefaultNotifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotifyId++;
                Intent intent = new Intent(MainActivity.this,Activity1.class);
                intent.putExtra("sid", "" + mNotifyId);
                PendingIntent pendingIntent = PendingIntent
                        .getActivity(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                Notification notification = new NotificationCompat.Builder(MainActivity.this,channelId)
                        .setLargeIcon(BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.ic_launcher))
                        .setSmallIcon(R.drawable.config_button)
                        .setTicker("I'm coming")
                        .setContentTitle("this is title")
                        .setContentText("this is contentText")
                        .setWhen(System.currentTimeMillis()+5*1000)
                        .setAutoCancel(true) // 设置这个标志当用户单击面板就可以让通知将自动取消
                        .setOngoing(false) // 设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                        .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND) //向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合。
                        .setContentIntent(pendingIntent)
                        .build();
                notificationManager.notify(mNotifyId, notification);
            }
        });

        mCustomNotifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotifyId++;
                Intent intentToActivity1 = new Intent(MainActivity.this,Activity1.class);
                Intent intentToMainActivity = new Intent(MainActivity.this,MainActivity.class);
                intentToActivity1.putExtra("sid", "" + mNotifyId);
                PendingIntent pendingIntent = PendingIntent
                        .getActivity(MainActivity.this,0,intentToActivity1,PendingIntent.FLAG_UPDATE_CURRENT);

                PendingIntent remoteIntent = PendingIntent
                        .getActivity(MainActivity.this,0,intentToMainActivity,PendingIntent.FLAG_UPDATE_CURRENT);


                RemoteViews remoteView = new RemoteViews(getPackageName(),R.layout.layout_notification);
                remoteView.setTextViewText(R.id.msg,"remoteTxt");
                remoteView.setImageViewResource(R.id.icon,R.drawable.ic_launcher_round);
                remoteView.setOnClickPendingIntent(R.id.open_intent, remoteIntent);
                Notification notification = new NotificationCompat.Builder(MainActivity.this,channelId)
                        .setSmallIcon(R.drawable.config_button)
                        .setWhen(System.currentTimeMillis()+5*1000)
                        .setAutoCancel(true) // 设置这个标志当用户单击面板就可以让通知将自动取消
                        .setOngoing(false) // 设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                        .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND) //向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合。
                        .setContentIntent(pendingIntent)
                        .setCustomContentView(remoteView)
                        .build();

                notificationManager.notify(mNotifyId, notification);
            }
        });
    }


}
