package com.gpj.livedatademo;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LiveData";
    private NameViewModel mNameViewModel;
    TextView mTvName;
    Button changeName;
    Button updateList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvName = findViewById(R.id.text);
        changeName = findViewById(R.id.change_name);
        changeName.setOnClickListener(v -> {
            mNameViewModel.getCurrentName().setValue("gpj");
        });

        updateList = findViewById(R.id.update_list);
        updateList.setOnClickListener(v->{
            List<String> nameList = new ArrayList<>();
            for (int i = 0; i < 10; i++){
                nameList.add("jpg<" + i + ">");
            }
            mNameViewModel.getNameList().setValue(nameList);
        });

        updateList = findViewById(R.id.update_list);
        mNameViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(NameViewModel.class);
        mNameViewModel.getCurrentName().observe(this,(String name) -> {
            mTvName.setText(name);
            Log.d(TAG, "currentName: " + name);
        }); // 订阅LiveData中当前Name数据变化，以lambda形式定义Observer
        mNameViewModel.getNameList().observe(this, (List<String> nameList) -> {
            for (String item : nameList) {
                Log.d(TAG, "name: " + item);
            }
        }); // 订阅LiveData中Name列表数据变化，以lambda形式定义Observer

        MyLiveData.getInstance(this).observe(this,(Integer status)->{
            Log.d(TAG, "status: " + status);
        });
    }
}
