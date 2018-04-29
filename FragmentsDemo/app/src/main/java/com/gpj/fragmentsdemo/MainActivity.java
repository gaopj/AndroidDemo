package com.gpj.fragmentsdemo;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends SingleFragmrntActivity {

    private ListTitleFragment mListFragment;
    @Override
    protected Fragment createFragment() {
        mListFragment = new ListTitleFragment();
        return mListFragment;
    }
}
