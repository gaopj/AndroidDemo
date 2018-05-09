package com.gpj.transitionanimationdemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.ViewGroup;

/**
 * Created by v-pigao on 5/9/2018.
 */

//我定义了一个起始和结束时，View的高度发生变化时，就会旋转的动画效果
@TargetApi(19)
public class RotateWhenHeightChangeTransition extends Transition {
    private static final String PROP_NAME_TRANSITION_HEIGHT = "gpj_test";

    //首先必须复写captureStartValues和captureEndValues
    //如同函数名，这里主要记录我们感兴趣的数据
    //按照键值对的形式存入transitionValues.values

    //记录开始Scene中View的高度
    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.put(
                PROP_NAME_TRANSITION_HEIGHT, transitionValues.view.getLayoutParams().height);
    }

    //记录结束Scene中View的高度
    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(
                PROP_NAME_TRANSITION_HEIGHT, transitionValues.view.getLayoutParams().height);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot,
                                   TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }

        //比对开始和结束时，高度是否发生变化
        int startHeight = (int)startValues.values.get(PROP_NAME_TRANSITION_HEIGHT);
        int endHeight = (int) endValues.values.get(PROP_NAME_TRANSITION_HEIGHT);
        //若发生变化，则创建属性动画对应的Animator
        if (startHeight != endHeight) {
            return ObjectAnimator
                    .ofFloat(endValues.view, "rotation", 0, 360)
                    .setDuration(2000);
        }

        return null;
    }
}