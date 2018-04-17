package com.gpj.ontoucheventandscrollerdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by v-pigao on 4/17/2018.
 */

public class ScrollLayout  extends LinearLayout {

    private Scroller mScroller;
    public ScrollLayout(Context context) {
        this(context, null, 0);
    }

    public ScrollLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);

    }

    public ScrollLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    // 手指最后在view中的坐标
    private int mLastX;
    private int mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // 记录手指在view的坐标。
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()){ // 如果上次的调用没有执行完就取消
                    mScroller.abortAnimation();
                }
                // 更新手指此时坐标
                mLastX = x;
                mLastY = y;
                return true;

            case MotionEvent.ACTION_MOVE:
                // 计算手指此时的坐标和上次的坐标滑动的距离。
                int dy = y - mLastY;
                int dx = x - mLastX;

                // 更新手指此时坐标
                mLastX = x;
                mLastY = y;

                //滑动相对距离
                scrollBy(-dx, -dy);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                // XY 滑动回去
                mScroller.startScroll(getScrollX(),getScrollY(),-getScrollX(),-getScrollY(),1000);
                invalidate();
                return true;

        }
        return super.onTouchEvent(event);
    }

    /**
     * 这个方法在调用了invalidate()后被回调。
     */
    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){ // 计算新位置，并判断上一个滚动是否完成。
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate(); // 再次调用computeScroll。
        }
    }
}
