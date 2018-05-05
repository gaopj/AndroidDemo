package com.gpj.commandpatterndemo.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

/**
 * Created by v-pigao on 5/5/2018.
 */

public class DrawPath implements  IDraw{
    public static final String TAG = "Draw";

    public Path path; // 需要绘制的路径
    public Paint paint; // 绘制画笔

    @Override
    public void draw(Canvas canvas) {
        Log.d(TAG,"draw");
        canvas.drawPath(path,paint);
    }

    @Override
    public void undo() {
        Log.d(TAG,"undo");
    }
}
