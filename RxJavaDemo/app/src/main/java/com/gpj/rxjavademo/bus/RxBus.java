package com.gpj.rxjavademo.bus;

import android.util.Log;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by v-pigao on 4/14/2018.
 */

public class RxBus {
    private static final String TAG="RxJava";
    private static volatile RxBus rxBus;
    private final Subject<Object, Object> subject = new SerializedSubject<>(PublishSubject.create());
    private RxBus() {
    }

    public static RxBus getInstance() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                if (rxBus == null) {
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }

    public void post(Object ob) {
        Log.d(TAG, "post");
        subject.onNext(ob);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        Log.d(TAG, "toObservable");
        return subject.ofType(eventType);
    }
}
