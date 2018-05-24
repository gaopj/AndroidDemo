package com.gpj.gradientprogressviewdemo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by v-pigao on 5/24/2018.
 */

public class GradientProgressView extends View {

    private static final int[] DEFAULT_COLORS = {Color.BLUE, Color.GREEN,Color.BLUE};
    private static final float[] DEFAULT_POSITION = {0.25f,0.75f,1};
    private static final int DEFAULT_ANIMATION_DURATION = 3000;
    private static int[] mGradientColors = DEFAULT_COLORS;
    private static float[] mGradientPos = DEFAULT_POSITION;

    private Paint mGradientCirclePaint = new Paint(); // 进度渐变圈
    private Paint mBackgroundCirclePaint = new Paint(); // 背景圈
    private Paint mTextPaint = new Paint();

    private RectF mRectF = new RectF();
    private Rect mTextBound = new Rect();
    private int mCx;
    private int mCy;

    private int mProgress = 0;
    private int mDuration = DEFAULT_ANIMATION_DURATION;

    public GradientProgressView(Context context) {
        this(context, null);
    }

    public GradientProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(mRectF, 0, 360, false, mBackgroundCirclePaint);

        float startAngle = - 90;

        //ObjectAnimator实现
        float sweepAngle = mProgress * 1.0f / 100 * 360;
        String progressString = String.valueOf(mProgress);
        mTextPaint.getTextBounds(progressString, 0, progressString.length(), mTextBound);
        float x = mCx - mTextBound.width() / 2;
        float y = mCy + mTextBound.height() / 2;
        canvas.drawText(progressString, x, y, mTextPaint);
        //绘制进度
        canvas.drawArc(mRectF, startAngle, sweepAngle, false, mGradientCirclePaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        initRect(w, h);
        mCx = w / 2;
        mCy = h / 2;
        initCirclePaint();
    }

    private void initRect(int w, int h) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = w - getPaddingRight();
        int bottom = h - getPaddingBottom();
        mRectF.set(left, top, right, bottom);
    }

    // 初始化画笔
    private void initCirclePaint() {
        Shader shader = new SweepGradient(mCx, mCy, mGradientColors,mGradientPos);
        mGradientCirclePaint.setShader(shader);
        mGradientCirclePaint.setStyle(Paint.Style.STROKE);
        mGradientCirclePaint.setStrokeWidth(30);
        mGradientCirclePaint.setAntiAlias(true);
        mGradientCirclePaint.setStrokeCap(Paint.Cap.ROUND); // 圆角触笔风格

        mBackgroundCirclePaint.setStyle(Paint.Style.STROKE);
        mBackgroundCirclePaint.setStrokeWidth(30);
        mBackgroundCirclePaint.setAntiAlias(true);
        mBackgroundCirclePaint.setColor(Color.LTGRAY);
    }

    /**
     * 供属性动画使用
     */
    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    public void startAnimation(int degree) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "progress", 0, degree);
        objectAnimator.setDuration(mDuration);
        objectAnimator.start();
    }

    public static int[] getGradientColors() {
        return mGradientColors;
    }

    public static void setGradientColors(int[] mGradientColors) {
        GradientProgressView.mGradientColors = mGradientColors;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }
}
