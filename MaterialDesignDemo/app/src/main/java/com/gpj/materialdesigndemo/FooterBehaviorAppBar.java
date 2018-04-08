package com.gpj.materialdesigndemo;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by v-pigao on 4/8/2018.
 */

public class FooterBehaviorAppBar  extends CoordinatorLayout.Behavior<View> {
    public FooterBehaviorAppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float translationY = Math.abs(dependency.getY());
        Log.i("gpj",translationY+"");
        child.setTranslationY(translationY);
        return true;
    }
}