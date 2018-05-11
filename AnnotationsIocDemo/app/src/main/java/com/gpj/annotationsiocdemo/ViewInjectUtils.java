package com.gpj.annotationsiocdemo;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by v-pigao on 5/10/2018.
 */

public class ViewInjectUtils {
    public static void inject(Activity activity) {
        injectContentView(activity);
        injectViews( activity);
        injectEvents( activity);
    }
    private static void injectContentView(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        //获取注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            //注解的值就是layout id
            int contentViewId = contentView.value();
            try {
                activity.setContentView(contentViewId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectViews(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();

        //遍历所有field，找出其中具有ViewInject注解的
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);

            if (viewInject != null) {
                //取出值
                int viewId = viewInject.value();
                if (viewId != -1) {
                    try {
                        //找到view并赋值
                        Object view = activity.findViewById(viewId);
                        field.setAccessible(true);
                        field.set(activity, view);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void injectEvents(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getMethods();

        //遍历所有的method
        for (Method method : methods) {
            //获取每个method对应的注解
            Annotation[] annotations = method.getAnnotations();

            for (Annotation annotation : annotations) {
                //获取注解对应的类型
                Class<? extends Annotation> annotationType = annotation.annotationType();

                //若是EventBase修饰的注解，则需要注入事件监听
                EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                if (eventBase != null) {
                    //class name （View.OnClickListener.class）
                    Class<?> listenerType = eventBase.listenerClass();

                    //class method （"setOnClickListener"）
                    String listenerSetter = eventBase.setListenerMethod();

                    //callback method （"onClick"）
                    String methodName = eventBase.listenerCallback();

                    try {
                        //获取注解对应的值
                        Method annotationMethod = annotationType.getDeclaredMethod("value");
                        int[] viewIds = (int[]) annotationMethod.invoke(annotation);

                        //创建动态代理
                        DynamicHandler handler = new DynamicHandler(activity);
                        //关联listener的回调接口和注解实际修饰的method
                        handler.addMethod(methodName, method);
                        Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(),
                                new Class<?>[]{listenerType}, handler);

                        //为每一个View设置listener
                        for (int viewId : viewIds) {
                            View view = activity.findViewById(viewId);
                            Method setListenerMethod = view.getClass()
                                    .getMethod(listenerSetter, listenerType);
                            setListenerMethod.invoke(view, listener);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
