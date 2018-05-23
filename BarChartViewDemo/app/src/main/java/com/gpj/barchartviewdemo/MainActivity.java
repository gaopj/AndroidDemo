package com.gpj.barchartviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gpj.barchartviewdemo.barchart.BarChartView;

public class MainActivity extends AppCompatActivity {


    private static final String[] HORIZONTAL_AXIS= {"1", "2", "3", "4",
            "5", "6", "7", "8", "9", "10", "11", "12"};

    private static final float[] DATA = {12, 95, 45, 56, 89, 70, 49, 22, 23, 78, 12, 3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BarChartView lineChart = findViewById(R.id.bar_chart);
        lineChart.setHorizontalAxis(HORIZONTAL_AXIS);
        lineChart.setDataList(DATA, 100);
    }
}
