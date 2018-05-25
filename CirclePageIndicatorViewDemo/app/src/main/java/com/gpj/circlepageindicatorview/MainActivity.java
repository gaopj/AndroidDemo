package com.gpj.circlepageindicatorview;

import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private CirclePageIndicatorView mCirclePageIndicator;

    private String[] mDataList = {"G", "P","J", };
    private List<View> mPages =new LinkedList<>();
    private PagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mViewPager = findViewById(R.id.vp);

        mCirclePageIndicator =  findViewById(R.id.indicator);
        mCirclePageIndicator.setViewPager(mViewPager);
        LayoutInflater inflater=getLayoutInflater();
        for(int i=0;i<mDataList.length;i++) {
           View page = inflater.inflate(R.layout.page, null);
            TextView pageText = page.findViewById(R.id.text);
            pageText.setText(mDataList[i]);
            mPages.add(page);
        }

        mPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mDataList.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mPages.get(position));
                return mPages.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
    }


}
