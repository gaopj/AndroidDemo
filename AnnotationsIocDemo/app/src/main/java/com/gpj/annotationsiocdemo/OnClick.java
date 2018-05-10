package com.gpj.annotationsiocdemo;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by v-pigao on 5/10/2018.
 */

//修饰method的
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//定义该注解对应的描述
@EventBase(setListenerMethod = "setOnClickListener",
        listenerClass = View.OnClickListener.class, listenerCallback = "onClick")
public @interface OnClick {
    int[] value();
}
