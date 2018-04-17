package com.gpj.ontoucheventandscrollerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by v-pigao on 4/17/2018.
 */

public class ScrollerPager extends ViewGroup {
    public ScrollerPager(Context context) {
        this(context, null, 0);
    }

    public ScrollerPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    public ScrollerPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        for(int i=0;i<childCount;i++){
            View childView = getChildAt(i);
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if(changed){
            int childCount = getChildCount();
            for(int i=0;i<childCount;i++){
                View childView = getChildAt(i);
                int childW = childView.getMeasuredWidth();

                // 把所有子view放在水平方向，依次排开。
                // left:  0, w, 2w, 3w..
                // top:   0...
                // right: w, 2w, 3w...
                // topL   h...
                childView.layout(i * childW, 0, childW * i + childW, childView.getMeasuredHeight());
            }
        }
    }

    // 手指最后在view中的坐标
    private int mLastX;
    private int mLastY;
    private Scroller mScroller;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // 记录手指在view的坐标。
        int x = (int) event.getRawX();

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()){ // 如果上次的调用没有执行完就取消
                    mScroller.abortAnimation();
                }
                // 更新手指此时坐标
                mLastX = x;
                return true;

            case MotionEvent.ACTION_MOVE:
                int dxMove = x - mLastX;
                scrollBy(-dxMove, 0);
                // 更新手指此时坐标
                mLastX = x;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int sonIndex = (getScrollX() + getWidth() / 2) / getWidth();

                // 如果滑动超过最后一页，就退回到最后一页。
                int childCount = getChildCount();
                if (sonIndex >= childCount)
                    sonIndex = childCount - 1;

                // 现在滑动的相对距离。
                int dx = sonIndex * getWidth() - getScrollX();
                // Y方向不变，X方向到目的地。
                mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
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
