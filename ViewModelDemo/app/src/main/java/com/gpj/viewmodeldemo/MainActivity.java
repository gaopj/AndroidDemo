package com.gpj.viewmodeldemo;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public CommunicateViewModel mCommunicateViewModel;

    private FragmentOne mFragment1;
    private FragmentTwo mFragment2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        mFragment1 =FragmentOne.getInstance();
        mFragment2 =FragmentTwo.getInstance();
        transaction.replace(R.id.fragment_one, mFragment1);
        transaction.replace(R.id.fragment_two, mFragment2);
        transaction.commit();

        mCommunicateViewModel = new ViewModelProvider
                .NewInstanceFactory()
                .create(CommunicateViewModel.class);

    }
}
