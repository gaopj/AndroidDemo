package com.gpj.rxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by v-pigao on 4/14/2018.
 */

public class OkHttpActivity extends AppCompatActivity {
    private static final String TAG="RxJava";
    private OkHttpClient mOkHttpClient;
    private Observable mObservable;

    Button btn1;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mObservable =  getObservable("59.108.54.37");
            }
        });
        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postAsynHttp();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
       // postAsynHttp("59.108.54.37");
    }

    private void postAsynHttp() {
        mObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, e.getMessage());
            }
            @Override
            public void onNext(String s) {
                Log.d(TAG, s);
                Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Observable<String> getObservable(final String ip) {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                // 被订阅之后的回调
                Log.d(TAG, "call");
                mOkHttpClient = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("ip", ip)
                        .build();
                Request request = new Request.Builder()
                        .url("http://ip.taobao.com/service/getIpInfo.php")
                        .post(formBody)
                        .build();
                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        subscriber.onError(new Exception("error"));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();

                        // 调用之后 发送一个消息
                        subscriber.onNext(str);
                        subscriber.onCompleted();
                    }
                });
            }
        });
        return observable;
    }

}