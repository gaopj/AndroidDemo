package com.gpj.viewmodeldemo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentTwo extends Fragment {
    TextView mTvName;
    private CommunicateViewModel mCommunicateViewModel;

    public static FragmentTwo getInstance(){
        return new FragmentTwo();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommunicateViewModel = ((MainActivity)getActivity()).mCommunicateViewModel;
        mCommunicateViewModel.getName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mTvName.setText(s);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        mTvName = view.findViewById(R.id.text_name);
        return view;
    }

}