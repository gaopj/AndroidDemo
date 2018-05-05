package com.gpj.commandpatterndemo;

import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.gpj.commandpatterndemo.brush.CircleBrush;
import com.gpj.commandpatterndemo.brush.IBrush;
import com.gpj.commandpatterndemo.brush.NormalBrush;
import com.gpj.commandpatterndemo.draw.DrawPath;

public class MainActivity extends AppCompatActivity {

    private DrawCanvas mDrawCanvas; // 绘制画布
    private DrawPath mPaths; // 绘制路径
    private Paint mPaint; // 画笔对象
    private IBrush mBrush; // 触笔对象

    private Button undoBtn;
    private Button redoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPaint = new Paint();
        mPaint.setColor(0xFFFFFFFF);
        mPaint.setStrokeWidth(3);

        mBrush = new NormalBrush();

        mDrawCanvas = findViewById(R.id.canvas);
        mDrawCanvas.setOnTouchListener(new DrawTouchListener());

        undoBtn = findViewById(R.id.undo_btn);
        undoBtn.setEnabled(false);

        redoBtn = findViewById(R.id.redo_btn);
        redoBtn.setEnabled(false);

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.red_btn:
                mPaint = new Paint();
                mPaint.setStrokeWidth(3);
                mPaint.setColor(0xFFFF0000);
                break;
            case R.id.green_btn:
                mPaint = new Paint();
                mPaint.setStrokeWidth(3);
                mPaint.setColor(0xFF00FF00);
                break;
            case R.id.blue_btn:
                mPaint = new Paint();
                mPaint.setStrokeWidth(3);
                mPaint.setColor(0xFF0000FF);
                break;
            case R.id.undo_btn:
                mDrawCanvas.undo();
                if(!mDrawCanvas.canUndo()){
                    undoBtn.setEnabled(false);
                }
                redoBtn.setEnabled(true);
                break;
            case R.id.redo_btn:
                mDrawCanvas.redo();
                if(!mDrawCanvas.canRedo()){
                    redoBtn.setEnabled(false);
                }
                undoBtn.setEnabled(true);
                break;
            case R.id.normal_btn:
                mBrush = new NormalBrush();
                break;
            case R.id.circle_btn:
                mBrush = new CircleBrush();
                break;
        }
    }


    private class DrawTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mPaths = new DrawPath();
                    mPaths.paint = mPaint;
                    mPaths.path = new Path();
                    mBrush.down(mPaths.path,event.getX(),event.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    mBrush.move(mPaths.path,event.getX(),event.getY());
                    break;
                case MotionEvent.ACTION_UP:
                    mBrush.up(mPaths.path,event.getX(),event.getY());
                    mDrawCanvas.add(mPaths);
                    mDrawCanvas.isDrawing = true;
                    undoBtn.setEnabled(true);
                    redoBtn.setEnabled(false);
                    break;
            }

            return true;
        }
    }
}
