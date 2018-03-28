package com.gpj.remoteviewsdemo;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by v-pigao on 3/28/2018.
 */

public class Activity2 extends Activity {
    private static final String TAG = "Activity2-->";

    public static final String REMOTE_ACTION = "com.gpj.action_REMOTE";
    public static final String EXTRA_REMOTE_VIEWS = "extra_remoteViews";

    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_2);
        Log.d("gpj", TAG+"onCreate");
        sendBtn = findViewById(R.id.send);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_simulated_notification);
                remoteViews.setTextViewText(R.id.msg, "msg from process:" + Process.myPid());
                remoteViews.setImageViewResource(R.id.icon, R.drawable.ic_launcher_round);
                PendingIntent pendingIntent = PendingIntent.getActivity(Activity2.this,
                        0, new Intent(Activity2.this, Activity1.class), PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent openActivity2PendingIntent = PendingIntent.getActivity(
                        Activity2.this, 0, new Intent(Activity2.this, Activity2.class), PendingIntent.FLAG_UPDATE_CURRENT);
                remoteViews.setOnClickPendingIntent(R.id.item_holder, pendingIntent);
                remoteViews.setOnClickPendingIntent(R.id.open_activity2, openActivity2PendingIntent);
                Intent intent = new Intent(REMOTE_ACTION);
                intent.putExtra(EXTRA_REMOTE_VIEWS, remoteViews);
                sendBroadcast(intent);
            }
        });
    }

}
