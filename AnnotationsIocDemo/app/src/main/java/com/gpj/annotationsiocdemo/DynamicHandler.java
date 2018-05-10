package com.gpj.annotationsiocdemo;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by v-pigao on 5/10/2018.
 */

public class DynamicHandler implements InvocationHandler {
    private WeakReference<Object> mHandlerRef;
    private final HashMap<String, Method> mMethodMap = new HashMap<>(1);

    public DynamicHandler(Object handler) {
        mHandlerRef = new WeakReference<>(handler);
    }

    //完成关联
    public void addMethod(String name, Method method) {
        mMethodMap.put(name, method);
    }

    public Object getHandler() {
        return mHandlerRef.get();
    }

    public void setHandler(Object handler) {
        mHandlerRef = new WeakReference<>(handler);
    }

    //这个函数其实就能很好的反映动态代理的功能
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Object handler = mHandlerRef.get();
        if (handler != null) {
            //调用实际的方法
            String methodName = method.getName();
            Method realM = mMethodMap.get(methodName);
            if (realM != null) {
                return realM.invoke(handler, objects);
            }
        }

        return null;
    }
}
