package com.gpj.asynctaskloader;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //指定Loader对应的id
    public static final int mLoaderId = 0;
    private  Loader loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loader = getLoaderManager().getLoader(mLoaderId);
        if(loader==null) {

            //定义一个回调接口，一个callback可以监听多个loader
            //模板参数为AysncTaskLoader加载数据的返回值
            LoaderManager.LoaderCallbacks<List<String>> callback =
                    new LoaderManager.LoaderCallbacks<List<String>>() {

                        //LoaderManager创建Loader时，回调该接口
                        @Override
                        public Loader<List<String>> onCreateLoader(int id, Bundle args) {
                            //可以根据id创建出不同的Loader
                            //要求创建出的loader，不能是非静态内部类
                            //原因应该是避免内存泄露
                            if (id == mLoaderId) {
                                Log.d("jpg", "onCreateLoader");
                                return new FetchItemsLoader(MainActivity.this);
                            } else {
                                return null;
                            }
                        }

                        //FetchItemsLoader加载完数据时，就会回调该接口
                        @Override
                        public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
                            //同样可以根据id进行不同的操作
                            if (loader.getId() == mLoaderId) {
                                Log.d("jpg", "onLoadFinished");

                                //可以进行更新界面等操作

                            }
                        }

                        @Override
                        public void onLoaderReset(Loader<List<String>> loader) {
                        }
                    };

            //利用LoaderManager启动Loader，指定loader对应的id、参数和回调接口
            getLoaderManager().restartLoader(mLoaderId, null, callback);
        }else {
            Log.d("jpg", "loader exist, loader ID:"+loader.getId());
        }
    }

    //AsyncTaskLoader同样为抽象类，在模板参数中指定loadInBackground的返回值
    private static class FetchItemsLoader extends AsyncTaskLoader<List<String>> {
        FetchItemsLoader(Context context) {
            super(context);
        }

        //必须实现这个方法，forceLoad为父类接口
        //调用该接口后，才会执行loadInBackground的操作
        @Override
        protected void onStartLoading() {
            Log.d("jpg", "onStartLoading");
            forceLoad();
        }

        //返回值将会传回给callback的onLoadFinished接口
        @Override
        public List<String> loadInBackground() {
            Log.d("jpg", "loadInBackground");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new LinkedList<>();
        }
    }
}
