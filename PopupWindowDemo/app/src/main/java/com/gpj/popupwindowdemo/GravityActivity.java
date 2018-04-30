package com.gpj.popupwindowdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.gpj.popupwindowdemo.CommonPopupWindow.LayoutGravity;


public class GravityActivity extends AppCompatActivity implements View.OnClickListener {
    // anchor for popup window
    private Button gravityBt;
    // horizontal radio buttons
    private RadioButton alignLeftRb;
    private RadioButton alignRightRb;
    private RadioButton centerHoriRb;
    private RadioButton toRightRb;
    private RadioButton toLeftRb;
    // vertical radio buttons
    private RadioButton alignAboveRb;
    private RadioButton alignBottomRb;
    private RadioButton centerVertRb;
    private RadioButton toBottomRb;
    private RadioButton toAboveRb;
    // popup window
    private CommonPopupWindow window;
    private LayoutGravity layoutGravity;
    private TextView horiText;
    private TextView vertText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity);

        window=new CommonPopupWindow(this, R.layout.popup_gravity, 80, ViewGroup.LayoutParams.WRAP_CONTENT) {
            @Override
            protected void initView() {
                View view=getContentView();
                horiText= view.findViewById(R.id.hori_text);
                vertText= view.findViewById(R.id.vert_text);
            }

            @Override
            protected void initEvent() {}
        };
        layoutGravity=new LayoutGravity(LayoutGravity.ALIGN_LEFT| LayoutGravity.TO_BOTTOM);

        initView();
        initEvent();
    }

    private void initView() {
        gravityBt= findViewById(R.id.gravity_bt);

        alignLeftRb= findViewById(R.id.align_left_rb);
        alignRightRb= findViewById(R.id.align_right_rb);
        centerHoriRb= findViewById(R.id.center_hori_rb);
        toRightRb= findViewById(R.id.to_right_rb);
        toLeftRb= findViewById(R.id.to_left_rb);

        alignAboveRb= findViewById(R.id.align_above_rb);
        alignBottomRb= findViewById(R.id.align_bottom_rb);
        centerVertRb= findViewById(R.id.center_vert_rb);
        toBottomRb= findViewById(R.id.to_bottom_rb);
        toAboveRb= findViewById(R.id.to_above_rb);
    }

    private void initEvent() {
        gravityBt.setOnClickListener(this);

        alignLeftRb.setOnClickListener(this);
        alignRightRb.setOnClickListener(this);
        centerHoriRb.setOnClickListener(this);
        toRightRb.setOnClickListener(this);
        toLeftRb.setOnClickListener(this);

        alignAboveRb.setOnClickListener(this);
        alignBottomRb.setOnClickListener(this);
        centerVertRb.setOnClickListener(this);
        toBottomRb.setOnClickListener(this);
        toAboveRb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gravity_bt:
                window.showBashOfAnchor(gravityBt, layoutGravity, 0, 0);
                break;
            case R.id.align_left_rb:
                layoutGravity.setHoriGravity(LayoutGravity.ALIGN_LEFT);
                horiText.setText("AL");
                break;
            case R.id.align_right_rb:
                layoutGravity.setHoriGravity(LayoutGravity.ALIGN_RIGHT);
                horiText.setText("AR");
                break;
            case R.id.center_hori_rb:
                layoutGravity.setHoriGravity(LayoutGravity.CENTER_HORI);
                horiText.setText("CH");
                break;
            case R.id.to_right_rb:
                layoutGravity.setHoriGravity(LayoutGravity.TO_RIGHT);
                horiText.setText("TR");
                break;
            case R.id.to_left_rb:
                layoutGravity.setHoriGravity(LayoutGravity.TO_LEFT);
                horiText.setText("TL");
                break;
            case R.id.align_above_rb:
                layoutGravity.setVertGravity(LayoutGravity.ALIGN_ABOVE);
                vertText.setText("AA");
                break;
            case R.id.align_bottom_rb:
                layoutGravity.setVertGravity(LayoutGravity.ALIGN_BOTTOM);
                vertText.setText("AB");
                break;
            case R.id.center_vert_rb:
                layoutGravity.setVertGravity(LayoutGravity.CENTER_VERT);
                vertText.setText("CV");
                break;
            case R.id.to_bottom_rb:
                layoutGravity.setVertGravity(LayoutGravity.TO_BOTTOM);
                vertText.setText("TB");
                break;
            case R.id.to_above_rb:
                layoutGravity.setVertGravity(LayoutGravity.TO_ABOVE);
                vertText.setText("TA");
                break;
        }
    }
}
