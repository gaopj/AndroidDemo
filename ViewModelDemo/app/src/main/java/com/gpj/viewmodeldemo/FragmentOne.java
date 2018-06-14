package com.gpj.viewmodeldemo;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;

public class FragmentOne extends Fragment {
    private CommunicateViewModel mCommunicateViewModel;

    private Button btn;

    public static FragmentOne getInstance(){
        return new FragmentOne();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommunicateViewModel = ((MainActivity)getActivity()).mCommunicateViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        btn = view.findViewById(R.id.btn_set_name);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommunicateViewModel.setName("Gpj");
            }
        });
        return view;
    }

}