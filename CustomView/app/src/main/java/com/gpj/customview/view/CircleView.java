package com.gpj.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.gpj.customview.R;

/**
 * Created by v-pigao on 3/27/2018.
 */

public class CircleView extends View {

    private int mColor = Color.BLUE;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context) {
        super(context);
        init();
    }

    // 只在xml 中配置的调用该构造函数
    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mColor  = a.getColor(R.styleable.CircleView_circle_color,Color.YELLOW);
        a.recycle();
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

   // AttributeSet attrs： 从xml中定义的参数
   // int defStyleAttr ：主题中优先级最高的属性
   // int defStyleRes  ： 优先级次之的内置于View的style
   // Xml直接定义 > xml中style引用 > defStyleAttr > defStyleRes > theme直接定义
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        mPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode==MeasureSpec.AT_MOST)
            setMeasuredDimension(200,200);
        else if(widthSpecMode == MeasureSpec.AT_MOST )
            setMeasuredDimension(200,heightSpecSize);
        else if(heightSpecMode == MeasureSpec.AT_MOST )
            setMeasuredDimension(widthSpecSize,200);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        int width = getWidth()-paddingLeft-paddingRight;
        int height = getHeight()-paddingBottom-paddingTop;
        int radius = Math.min(width,height)/2;
        canvas.drawCircle(paddingLeft+width/2,paddingTop+height/2,radius,mPaint);
    }
}
