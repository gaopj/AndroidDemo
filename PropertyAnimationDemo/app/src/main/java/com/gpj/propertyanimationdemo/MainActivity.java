package com.gpj.propertyanimationdemo;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity-->";

    private ImageView img1;
    private TextView txt2;
    private ImageView img3;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();

    }

    void initView(){
        img1=findViewById(R.id.img1);
        txt2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);
        btn = findViewById(R.id.btn);
    }

    void initEvent(){


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(img1,"translationY",img1.getHeight()).start();
            }
        });

        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValueAnimator colorAnim = ObjectAnimator.ofInt(txt2,"textColor",0xFFFF8080,0XFF8080FF);
                colorAnim.setDuration(3000);
                colorAnim.setEvaluator(new ArgbEvaluator());
                colorAnim.setRepeatCount(ValueAnimator.INFINITE);
                colorAnim.setRepeatMode(ValueAnimator.REVERSE);
                colorAnim.start();
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AnimatorSet set = new AnimatorSet();
                set.playTogether(
                        ObjectAnimator.ofFloat(img3,"rotationX",0,360)
                        ,ObjectAnimator.ofFloat(img3,"rotationY",0,180)
                        ,ObjectAnimator.ofFloat(img3,"rotation",0,90)
                        ,ObjectAnimator.ofFloat(img3,"translationX",0,90)
                        ,ObjectAnimator.ofFloat(img3,"translationY",0,90)
                        ,ObjectAnimator.ofFloat(img3,"scaleX",0,1.5f)
                        ,ObjectAnimator.ofFloat(img3,"scaleY",0,0.5F)
                        ,ObjectAnimator.ofFloat(img3,"alpha",1,0.25f,1)
                );
                set.setDuration(5 * 1000).start();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimate(v, 50, 300);
            }
        });
    }

    private void showAnimate(final View target, final int start, final int end){
        Interpolator overshootInterpolator = new AnticipateOvershootInterpolator();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(1,100);
        valueAnimator.setInterpolator(overshootInterpolator);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator mEvaluator = new IntEvaluator();
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue  = (Integer)animation.getAnimatedValue(); //当前进度0-100
                Log.d("gpj", TAG+"current value: " + currentValue);

                float fraction = animation.getAnimatedFraction();// 当前进度 0-1
                target.getLayoutParams().width = mEvaluator.evaluate(fraction,start,end);
                target.requestLayout();

            }
        });

        valueAnimator.setDuration(5000).start();
    }
}
