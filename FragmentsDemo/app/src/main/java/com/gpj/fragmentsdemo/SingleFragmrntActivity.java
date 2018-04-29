package com.gpj.fragmentsdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by v-pigao on 4/29/2018.
 */

public abstract class SingleFragmrntActivity extends FragmentActivity{

    protected abstract Fragment createFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment =fm.findFragmentById(R.id.id_fragment_container);

        if(fragment == null )
        {
            fragment = createFragment() ;

            fm.beginTransaction().add(R.id.id_fragment_container,fragment).commit();
        }
    }
}
