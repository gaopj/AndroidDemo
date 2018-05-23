package com.gpj.linechartviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v-pigao on 5/23/2018.
 */

public class LineChartView extends View{

    private static int[] DEFAULT_GRADIENT_COLORS = {Color.BLUE, Color.GREEN};

    private int[] mDataList;
    private int mMax;
    private String[] mHorizontalAxis;
    private List<Dot> mDots = new ArrayList<>();
    private int mStep;
    private Rect mTextRect;
    private int mGap;
    private Path mPath;
    private Path mGradientPath;
    private int mSelectedDotIndex = -1;
    private int mSelectedDotColor;
    private int mNormalDotColor;
    private int mLineColor;

    private final int mRadius;
    private final int mClickRadius;

    private Paint mGradientPaint;
    private Paint mLinePaint;
    private Paint mDotPaint;

    private Paint mAxisPaint;

    public LineChartView(Context context) {
        this(context,null);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineChartView);
        mLineColor = typedArray.getColor(R.styleable.LineChartView_line_color, Color.BLACK);
        mNormalDotColor = typedArray.getColor(R.styleable.LineChartView_dot_normal_color, Color.BLACK);
        mSelectedDotColor = typedArray.getColor(R.styleable.LineChartView_dot_selected_color, Color.RED);
        typedArray.recycle();

        initPaint();

        mPath = new Path();
        mGradientPath = new Path();

        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        mClickRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        mTextRect = new Rect();
        mGap = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
    }

    private void initPaint() {
        mAxisPaint = new Paint();
        mAxisPaint.setAntiAlias(true);
        mAxisPaint.setTextSize(20);
        mAxisPaint.setTextAlign(Paint.Align.CENTER);

        mDotPaint = new Paint();
        mDotPaint.setAntiAlias(true);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(3);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(mLineColor);

        mGradientPaint = new Paint();
        mGradientPaint.setAntiAlias(true);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mLinePaint);
        canvas.drawPath(mGradientPath, mGradientPaint);
        for (int i = 0; i < mDots.size(); i++) {
            String axis = mHorizontalAxis[i];
            int x = getPaddingLeft() + i * mStep;
            int y = getHeight()-getPaddingBottom();
            canvas.drawText(axis, x, y, mAxisPaint);
            Dot dot = mDots.get(i);
            if (i == mSelectedDotIndex) {
                mDotPaint.setColor(mSelectedDotColor);
                canvas.drawText(String.valueOf(mDataList[i]), dot.x, dot.y - mRadius - mGap, mAxisPaint);
            } else {
                mDotPaint.setColor(mNormalDotColor);
            }
            canvas.drawCircle(dot.x, dot.y, mRadius, mDotPaint);
        }
    }

    public void setDataList(int[] dataList, int max) {
        mDataList = dataList;
        mMax= max;
    }

    public void setHorizontalAxis(String[] horizontalAxis) {
        mHorizontalAxis = horizontalAxis;
    }

    private class Dot {
        int x;
        int y;
        int value; // 点数据值
        int transformedValue; // 点数据值对应的像素高度
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mDots.clear();

        // 绘制区域宽高
        int width = w - getPaddingLeft() - getPaddingRight();
        int height = h - getPaddingTop() - getPaddingBottom();
        mStep = width / (mDataList.length - 1);

        mAxisPaint.getTextBounds(mHorizontalAxis[0], 0, mHorizontalAxis[0].length(), mTextRect);
        int barHeight = height - mTextRect.height() - mGap;
        float heightRatio = barHeight / mMax;

        for (int i = 0; i < mDataList.length; i++) {

            Dot dot = new Dot();
            dot.value = mDataList[i];
            dot.transformedValue = (int) (dot.value * heightRatio);

            dot.x = mStep * i + getPaddingLeft();
            dot.y = getPaddingTop() + barHeight - dot.transformedValue;

            // 第一个点时将路径移到该点
            if (i == 0) {
                mPath.moveTo(dot.x, dot.y);
                mGradientPath.moveTo(dot.x, dot.y);
            } else {
                // 不然路径连线到该点
                mPath.lineTo(dot.x, dot.y);
                mGradientPath.lineTo(dot.x, dot.y);
            }

            if (i == mDataList.length - 1) {
                int bottom = getPaddingTop() + barHeight;
                mGradientPath.lineTo(dot.x, bottom);

                Dot firstDot = mDots.get(0);
                mGradientPath.lineTo(firstDot.x, bottom);
                mGradientPath.lineTo(firstDot.x, firstDot.y);
            }
            mDots.add(dot);
        }

        Shader shader = new LinearGradient(0, 0, 0, getHeight(), DEFAULT_GRADIENT_COLORS, null, Shader.TileMode.CLAMP);
        mGradientPaint.setShader(shader);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mSelectedDotIndex = getClickDotIndex(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mSelectedDotIndex = -1;
                invalidate();
                break;
        }
        return true;
    }

    // 判断点击的是哪个点
    private int getClickDotIndex(float x, float y) {
        int index = -1;
        for (int i = 0; i < mDots.size(); i++) {
            Dot dot = mDots.get(i);
            int left = dot.x - mClickRadius;
            int top = dot.y - mClickRadius;
            int right = dot.x + mClickRadius;
            int bottom = dot.y + mClickRadius;
            if (x > left && x < right && y > top && y < bottom) {
                index = i;
                break;
            }
        }
        return index;
    }
}
