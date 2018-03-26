package gpj.com.aidldemo.binderpool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

import gpj.com.aidldemo.BinderPoolService;

/**
 * Created by gpj on 2018/3/26.
 */

public class BinderPoolHelper {
    private static final String TAG = "BinderPoolHelper-> ";

    private static Context mContext;
    private static IBinderPool mBinderPool;
    private static CountDownLatch mConnectBinderPoolCountDownLatch;


    private static IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d("gpj",TAG+"binder died");
            mBinderPool.asBinder().unlinkToDeath(mDeathRecipient,0);
            mBinderPool = null;
            connectBinderPoolService();
        }
    };
    private static ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool =IBinderPool.Stub.asInterface(service);

            try {
                mBinderPool.asBinder().linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            mConnectBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private BinderPoolHelper(){};
    private static class BinderPoolHelperHolder{
        private static final BinderPoolHelper sInstance= new BinderPoolHelper();
    }

    public static BinderPoolHelper getInsatance(Context context){
        BinderPoolHelperHolder.sInstance.mContext = context;
        connectBinderPoolService();
        return BinderPoolHelperHolder.sInstance;
    }

    private static synchronized void connectBinderPoolService(){
        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent service = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(service,mServiceConnection,Context.BIND_AUTO_CREATE);

        try {
            mConnectBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public IBinder queryBinder(int binderCode){
        IBinder binder = null;

        if(mBinderPool!=null){
            try {
                binder = mBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        return binder;
    }
}
