package com.gpj.rxjavademo.bus;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.gpj.rxjavademo.R;

import rx.Subscription;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class RxBusFragment extends Fragment {
    private static final String TAG="RxJava";
    private TextView tv_text;
    private Subscription subscription;

    private Button btn;
    public RxBusFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rx_bus2, container,false);
        tv_text=  view.findViewById(R.id.tv_text);

        btn = view.findViewById(R.id.registered_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscription= RxBus.getInstance().toObservable(MessageEvent.class).subscribe(new Action1<MessageEvent>() {
                    @Override
                    public void call(MessageEvent messageEvent) {
                        if(messageEvent!=null) {
                            tv_text.setText(messageEvent.getMessage());
                        }
                    }
                });
            }
        });
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(subscription!=null&&!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
