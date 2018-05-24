package com.gpj.swipecardsviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by v-pigao on 5/24/2018.
 */

public class SwipeCardsView extends ViewGroup {
    private static final String TAG = "SwipeCards";

    private static final int MAX_DEGREE = 60;
    private static final float MAX_ALPHA_RANGE = 0.8f;
    private int mCenterX;
    private int mCenterY;

    private ViewDragHelper mViewDragHelper;

    // 每张图片竖直方向间隔
    private int mCardGap = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

    public SwipeCardsView(Context context) {
        this(context, null);
    }

    public SwipeCardsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mViewDragHelper = ViewDragHelper.create(this, mCallback);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 布局所有CardView
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int left = mCenterX - child.getMeasuredWidth() / 2;
            int top = mCenterY - child.getMeasuredHeight() / 2 + mCardGap * (getChildCount() - i);
            int right = left + child.getMeasuredWidth();
            int bottom = top + child.getMeasuredHeight();
            child.layout(left, top, right, bottom);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 测量所有CardView
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 控件中心位置
        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
    @Override
    public void computeScroll() {
        // continueSettling 内部调用了Scroller的computeScrollOffset 计算滚动偏移量，
        // 并完成滚动，动画结束返回false 没有返回true。
        if (mViewDragHelper.continueSettling(false)) {
            invalidate();
        }
    }

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            // 允许ViewDragHelper 拖动内部view
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            // 能在水平方向拖动
            // 使用ViewDragHelper 提供的child的left位置
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            // 能在竖直方向拖动
            // 使用ViewDragHelper 提供的child的top位置
            return top;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.d(TAG, "onViewPositionChanged: " + left);
            //计算位置改变后，与原来位置的中心点变化量
            int diffX = left + changedView.getWidth() / 2 - mCenterX;
            //计算变化量占changedView宽度的比值
            float ratio = diffX * 1.0f / getWidth();
            //计算对应的旋转角度
            float degree = MAX_DEGREE * ratio;
            //设置旋转
            changedView.setRotation(degree);
            //设置透明度
            float alpha = 1 - Math.abs(ratio) * MAX_ALPHA_RANGE;
            changedView.setAlpha(alpha);
        }

        @Override
        public void onViewReleased(final View releasedChild, float xvel, float yvel) {
            final int left = releasedChild.getLeft();
            final int right = releasedChild.getRight();
            if (left > mCenterX) {
                // 松开时left位置大于中心点X轴位置 往右飞
                animateToRight(releasedChild);
            } else if (right < mCenterX) {
                // 松开时right位置小于中心点X轴位置 往左飞
                animateToLeft(releasedChild);
            } else {
                // 松开时幅度不大，回到中间
                animateToCenter(releasedChild);
            }
        }
    };

    private void animateToCenter(View releasedChild) {
        //计算动画结束后左边位置
        int finalLeft = mCenterX - releasedChild.getWidth() / 2;
        int indexOfChild = indexOfChild(releasedChild);
        //计算动画结束后上边位置
        int finalTop = mCenterY - releasedChild.getHeight() / 2 + mCardGap * (getChildCount() - indexOfChild);
       // 平滑滚动releasedChild，smoothSlideViewTo内部会调用Scroller的startScroll方法
        mViewDragHelper.smoothSlideViewTo(releasedChild, finalLeft, finalTop);
        invalidate();
    }
    private void animateToRight(View releasedChild) {
        int finalLeft = getWidth() + releasedChild.getHeight();
        int finalTop = releasedChild.getTop();
        mViewDragHelper.smoothSlideViewTo(releasedChild, finalLeft, finalTop);
        invalidate();
    }

    private void animateToLeft(View releasedChild) {
        int finalLeft = -getWidth();
        int finalTop = 0;
        mViewDragHelper.smoothSlideViewTo(releasedChild, finalLeft, finalTop);
        invalidate();
    }
}
