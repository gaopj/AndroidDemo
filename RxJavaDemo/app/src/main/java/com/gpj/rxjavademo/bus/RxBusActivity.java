package com.gpj.rxjavademo.bus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gpj.rxjavademo.R;


public class RxBusActivity extends AppCompatActivity {
    private static final String TAG="RxJava";
    private Button bt_post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);
        bt_post= (Button) this.findViewById(R.id.bt_post);
        bt_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getInstance().post(new MessageEvent("用RxJava实现RxBus"));
            }
        });
    }
}
