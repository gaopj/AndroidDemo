package com.gpj.commandpatterndemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gpj.commandpatterndemo.draw.DrawInvoker;
import com.gpj.commandpatterndemo.draw.DrawPath;

/**
 * Created by v-pigao on 5/5/2018.
 */

public class DrawCanvas extends SurfaceView implements SurfaceHolder.Callback {

    public volatile boolean  isDrawing; // 是否可以绘制
    public volatile boolean  isRunning; // 是否可以运行

    private Bitmap mBitmap; //绘制到的位图对象
    private DrawInvoker mDrawInvoker; // 绘制命令请求对象
    private DrawThread mThread; // 绘制线程



    public DrawCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDrawInvoker = new DrawInvoker();
        mThread = new DrawThread();
        getHolder().addCallback(this);
    }

    /**
     * 增加一条路径
     * @param path
     */
    public void add(DrawPath path){
        mDrawInvoker.add(path);
    }

    /**
     * 重做上一步撤销的绘制
     */
    public void redo(){
        mDrawInvoker.redo();
    }

    /**
     * 撤销上一步的绘制
     */
    public void undo(){
        mDrawInvoker.undo();
    }


    /**
     * 是否可以重做
     * @return
     */
    public boolean canRedo(){
        return mDrawInvoker.canRedo();
    }

    /**
     * 是否可以撤销
     * @return
     */
    public boolean canUndo(){
        return mDrawInvoker.canUndo();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning =true;
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        isRunning = false;
        while (retry){
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class DrawThread extends Thread{

        @Override
        public void run() {
            Canvas canvas = null;
            while (isRunning){
                if(isDrawing){
                    try {
                        canvas = getHolder().lockCanvas();
                        if(mBitmap ==null){
                            mBitmap = Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888);
                        }
                        Canvas c = new Canvas(mBitmap);
                        c.drawColor(0, PorterDuff.Mode.CLEAR);

                        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                        mDrawInvoker.execute(c);
                        canvas.drawBitmap(mBitmap,0,0,null);
                    }finally {
                        getHolder().unlockCanvasAndPost(canvas);
                    }

                    isDrawing = false;
                }
            }
        }
    }
}
