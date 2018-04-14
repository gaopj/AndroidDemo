package com.gpj.eventbusdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private TextView tv_message1;
    private TextView tv_message2;
    private Button bt_message;
    private Button bt_subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_message1=this.findViewById(R.id.tv_message);
        tv_message1.setText("MainActivity1");

        tv_message2=this.findViewById(R.id.tv_message2);
        tv_message2.setText("MainActivity2");
        bt_subscription=this.findViewById(R.id.bt_subscription);
        bt_subscription.setText("注册事件");
        bt_message=this.findViewById(R.id.bt_message);
        bt_message.setText("跳转到SecondActivity");
        bt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });

        bt_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!EventBus.getDefault().isRegistered(MainActivity.this)) {
                    //注册事件
                    EventBus.getDefault().register(MainActivity.this);
                }else{
                    Toast.makeText(MainActivity.this,"请勿重复注册事件",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGpjEvent(MessageEvent messageEvent){
        tv_message1.setText(messageEvent.getMessage());
    }

    @Subscribe(sticky = true)
    public void onGpj2StickyEvent(MessageEvent messageEvent){
        tv_message2.setText("2--->"+messageEvent.getMessage());
    }
    @Subscribe(sticky = true)
    public void onGpjStickyEvent(MessageEvent messageEvent){
        tv_message1.setText("1-->"+messageEvent.getMessage());
    }


}
