package com.gpj.materialdesigndemo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button  mSnackbar;
    private Button mTextInputLayoutBtn;
    private Button mTabInputLayoutBtn;
    private LinearLayout mActivityMain;

    private FloatingActionButton mFloatBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivityMain = findViewById(R.id.activity_main);

        mFloatBtn = findViewById(R.id.fab);
        mFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"float_gpj",Toast.LENGTH_SHORT).show();
            }
        });

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

        mTabInputLayoutBtn = findViewById(R.id.tabLayoutBtn);
        mTabInputLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MaterialDesignActivity.class);
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
