package com.gpj.rippleanimationdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private View[] mChildViews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup viewGroup = findViewById(R.id.root_view);
        mChildViews = new View[viewGroup.getChildCount()];
        for (int i = 0; i < mChildViews.length; i++) {
            mChildViews[i] = viewGroup.getChildAt(i);
        }
    }

    public void onClick(View view) {
        RippleAnimation.create(view).setDuration(200).start();
        int color;
        switch (view.getId()) {
            case R.id.red:
                color = Color.RED;
                break;
            case R.id.green:
                color = Color.GREEN;
                break;
            case R.id.blue:
                color = Color.BLUE;
                break;
            case R.id.yellow:
                color = Color.YELLOW;
                break;
            default:
                color = Color.TRANSPARENT;
                break;
        }
        updateColor(color);
    }

    private void updateColor(int color) {

        for (View view : mChildViews) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(color);
            } else {
                view.setBackgroundColor(color);
            }
        }
    }
}
