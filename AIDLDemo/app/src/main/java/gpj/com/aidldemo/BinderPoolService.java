package gpj.com.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import gpj.com.aidldemo.binderpool.BinderPoolImpl;

/**
 * Created by gpj on 2018/3/26.
 */

public class BinderPoolService extends Service {
    private static final String TAG = "BinderPoolService-> ";

    private Binder mBinderPool = new BinderPoolImpl();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinderPool;
    }
    @Override
    public void onCreate(){
        super.onCreate();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
