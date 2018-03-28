package com.gpj.remoteviewsdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by v-pigao on 3/28/2018.
 */

public class MyAppWidgetProvider extends AppWidgetProvider{
    public static final String TAG = "MyAppWidgetProvider-->";
    public static final String CLICK_ACTION = "com.gpj.remoteviewsdemo.action.CLICK";

    public MyAppWidgetProvider() {
        super();
    }
    @Override
    public void onReceive(final Context context, Intent intent){
        super.onReceive(context, intent);
        String action = intent.getAction();
        Log.i("gpj",TAG+ "onReceive : action = " + action);

        switch (action){
            case CLICK_ACTION:
                Log.d("gpj",TAG+"clicked it");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap srcbBitmap = BitmapFactory.decodeResource(
                                context.getResources(), R.drawable.ic_launcher);
                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                        for (int i = 0; i < 37; i++) {
                            float degree = (i * 10) % 360;
                            RemoteViews remoteViews = new RemoteViews(context
                                    .getPackageName(), R.layout.widget);
                            remoteViews.setImageViewBitmap(R.id.imageView1,
                                    rotateBitmap(context, srcbBitmap, degree));
                            Intent intentClick = new Intent(context,MyAppWidgetProvider.class);
                            intentClick.setAction(CLICK_ACTION);
                            PendingIntent pendingIntent = PendingIntent
                                    .getBroadcast(context, 0, intentClick, 0);
                            remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
                            appWidgetManager.updateAppWidget(new ComponentName(
                                    context, MyAppWidgetProvider.class),remoteViews);
                            SystemClock.sleep(30);
                        }

                    }
                }).start();
                break;
        }
    }

    /**
     * 每次窗口小部件被点击更新都调用一次该方法
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.i("gpj",TAG+ "onUpdate");

        final int counter = appWidgetIds.length;
        Log.i("gpj",TAG+"counter = " + counter);
        for (int i = 0; i < counter; i++) {
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context, appWidgetManager, appWidgetId);
        }

    }

    /**
     * 窗口小部件更新
     *
     * @param context
     * @param appWidgeManger
     * @param appWidgetId
     */
    private void onWidgetUpdate(Context context,
                                AppWidgetManager appWidgeManger, int appWidgetId) {

        Log.i("gpj",TAG+"appWidgetId = " + appWidgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget);

        // "窗口小部件"点击事件发送的Intent广播,不能是隐式广播
        Intent intentClick = new Intent(context,MyAppWidgetProvider.class);
        intentClick.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intentClick, 0);
        remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
        appWidgeManger.updateAppWidget(appWidgetId, remoteViews);
    }

    private Bitmap rotateBitmap(Context context, Bitmap srcbBitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap tmpBitmap = Bitmap.createBitmap(srcbBitmap, 0, 0,
                srcbBitmap.getWidth(), srcbBitmap.getHeight(), matrix, true);
        return tmpBitmap;
    }
}
