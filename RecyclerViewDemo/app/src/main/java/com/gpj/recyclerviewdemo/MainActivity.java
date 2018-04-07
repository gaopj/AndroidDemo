package com.gpj.recyclerviewdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gpj.recyclerviewdemo.recyclerview.DividerGridItemDecoration;
import com.gpj.recyclerviewdemo.recyclerview.DividerItemDecoration;
import com.gpj.recyclerviewdemo.recyclerview.MyRecyclerViewAdapter;
import com.gpj.recyclerviewdemo.recyclerview.MyStaggeredAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private List<String> mList;
    private MyRecyclerViewAdapter mRecyclerViewAdaper;
    private MyStaggeredAdapter myStaggeredAdapter;
    private RecyclerView mRecyclerView;

    private Button mVerticalBtn;
    private Button mHorizontalBtn;
    private Button mGridBtn;
    private Button mWaterfallBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        mList=new ArrayList<String>();
        for (int i = 1; i < 50; i++)
        {
            mList.add("item-->"+i);
        }
    }

    private void initView() {
        mRecyclerView= this.findViewById(R.id.recyclerview);

        mVerticalBtn = findViewById(R.id.verticalBtn);
        mVerticalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListView(LinearLayoutManager.VERTICAL);
            }
        });

        mHorizontalBtn = findViewById(R.id.horizontalBtn);
        mHorizontalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListView(LinearLayoutManager.HORIZONTAL);
            }
        });

        mGridBtn = findViewById(R.id.gridBtn);
        mGridBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGridView();
            }
        });

        mWaterfallBtn = findViewById(R.id.waterfallBtn);
        mWaterfallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWaterfallView();
            }
        });

    }

    public void setListView(int mode){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        if(mode==LinearLayoutManager.HORIZONTAL){
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        }
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, mode));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewAdaper=new MyRecyclerViewAdapter(this, mList);
        setLister();
        mRecyclerView.setAdapter(mRecyclerViewAdaper);
    }

    public void setGridView(){
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewAdaper=new MyRecyclerViewAdapter(this, mList);
        setLister();
        mRecyclerView.setAdapter(mRecyclerViewAdaper);
    }

    public void setWaterfallView(){
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myStaggeredAdapter=new MyStaggeredAdapter(this, mList);
        mRecyclerView.setAdapter(myStaggeredAdapter);
    }

    private void setLister(){
        mRecyclerViewAdaper.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "点击第" + (position + 1) + "条", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("确认删除吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mRecyclerViewAdaper.removeData(position);
                            }
                        })
                        .show();
            }
        });
    }
}
