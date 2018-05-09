package com.gpj.transitionanimationdemo;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewGroup rootView = findViewById(R.id.rootView);

        Button button = findViewById(R.id.begin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    //定义结束Scene
                    Scene scene2 = Scene.getSceneForLayout(rootView, R.layout.scene2, MainActivity.this);

                    //利用TransitionManager进行变换
                    TransitionManager.go(scene2, new ChangeBounds());
                }
            }
        });

        Button button2 = findViewById(R.id.begin2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    //定义结束Scene
                    Scene scene2 = Scene.getSceneForLayout(rootView, R.layout.scene2, MainActivity.this);
                    Transition transition = new ChangeBounds();
                    transition.addTarget(R.id.image1);
                    //利用TransitionManager进行变换
                    TransitionManager.go(scene2,transition);
                }
            }
        });

        Button button3 = findViewById(R.id.begin3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    ChangeBounds changeBounds = new ChangeBounds();
                    changeBounds.setDuration(3000);
                    //开启延迟动画
                    TransitionManager.beginDelayedTransition(rootView, changeBounds);

                    //可以直接修改rootView中子View的属性
                    //当动画结束后，子View的属性就会被修改到指定值

                    View image1 = findViewById(R.id.image1);
                    ViewGroup.LayoutParams params1 = image1.getLayoutParams();
                    params1.height = 200;
                    params1.width = 200;
                    image1.setLayoutParams(params1);

                    View image2 = findViewById(R.id.image2);
                    ViewGroup.LayoutParams params2 = image2.getLayoutParams();
                    params2.height = 400;
                    params2.width = 400;
                    image2.setLayoutParams(params2);
                }
            }
        });

        Button button4 = findViewById(R.id.begin4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                    RotateWhenHeightChangeTransition changeBounds = new RotateWhenHeightChangeTransition();
                    changeBounds.setDuration(3000);
                    //开启延迟动画
                    TransitionManager.beginDelayedTransition(rootView, changeBounds);

                    //可以直接修改rootView中子View的属性
                    //当动画结束后，子View的属性就会被修改到指定值

                    View image1 = findViewById(R.id.image1);
                    ViewGroup.LayoutParams params1 = image1.getLayoutParams();
                    params1.height = 200;
                    params1.width = 200;
                    image1.setLayoutParams(params1);

                    View image2 = findViewById(R.id.image2);
                    ViewGroup.LayoutParams params2 = image2.getLayoutParams();
                    params2.height = 400;
                    params2.width = 400;
                    image2.setLayoutParams(params2);

                }
            }
        });
    }
}
