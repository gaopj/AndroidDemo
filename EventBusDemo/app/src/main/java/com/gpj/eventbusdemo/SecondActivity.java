package com.gpj.eventbusdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by v-pigao on 4/14/2018.
 */

public class SecondActivity extends AppCompatActivity {
    private Button bt_message;
    private TextView tv_message;
    private Button bt_subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_message=this.findViewById(R.id.tv_message);
        tv_message.setText("SecondActivity");
        bt_subscription=findViewById(R.id.bt_subscription);
        bt_subscription.setText("发送粘性事件");
        bt_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new MessageEvent("SecondActivity粘性事件"));
                finish();
            }
        });
        bt_message=this.findViewById(R.id.bt_message);
        bt_message.setText("发送事件");
        bt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("SecondActivity发来"));
                finish();
            }
        });

    }
}
