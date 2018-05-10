package com.gpj.annotationsiocdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by v-pigao on 5/10/2018.
 */

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    String setListenerMethod();
    Class<?> listenerClass();
    String listenerCallback();
}
