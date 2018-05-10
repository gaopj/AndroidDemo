package com.gpj.annotationsiocdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@ContentView(value = R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.helloTxt)
    private TextView tx1;
    @ViewInject(R.id.button)
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注入代码
        ViewInjectUtils.inject(this);

        tx1.setText("zhujiehou");
    }

    //我们的目前就是实现，点击R.id.test对应的button后
    //会调用到clickBtnInvoked函数
    @OnClick({R.id.button})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.button:
                Toast.makeText(this, "click test btn",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
