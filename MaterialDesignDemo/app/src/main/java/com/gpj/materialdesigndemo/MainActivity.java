package com.gpj.materialdesigndemo;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button  mSnackbar;
    private Button mTextInputLayoutBtn;
    private ConstraintLayout mActivityMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivityMain = findViewById(R.id.activity_main);
        mSnackbar = findViewById(R.id.snackbarBtn);
        mSnackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackbar();
            }
        });

        mTextInputLayoutBtn = findViewById(R.id.textInputLayoutBtn);
        mTextInputLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TextInputLayoutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showSnackbar(){
        Snackbar.make(mActivityMain,"title_gpj",Snackbar.LENGTH_LONG)
                .setAction("click_gpj", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"Toast_gpj",Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
}
