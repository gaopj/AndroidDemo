package gpj.com.aidldemo;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import gpj.com.aidldemo.bean.Book;
import gpj.com.aidldemo.binderpool.BinderPoolHelper;
import gpj.com.aidldemo.binderpool.BinderPoolImpl;
import gpj.com.aidldemo.binderpool.ComputeImpl;
import gpj.com.aidldemo.binderpool.ICompute;
import gpj.com.aidldemo.binderpool.ISayHello;
import gpj.com.aidldemo.binderpool.SayHelloImpl;


public class MainActivity extends Activity {
    public static final String TAG = "MainActivity->";

    private static final int MESSAGE_NRE_BOOK_ARRIVED = 1;

    private Button mAddBookBtn;
    private Button mGetBookBtn;
    private Button mBinderPoolBtn;

    private ISayHello mSayHello;
    private ICompute mCompute;

    private IBookManager mBookManager;
    private Handler mHandler = new MyHandler();
    private IOnNewBookArrivedListener mIOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NRE_BOOK_ARRIVED, newBook).sendToTarget();
        }
    };
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBookManager = IBookManager.Stub.asInterface(service);

            try {
                mBookManager.registerListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();

        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    void initView() {
        mAddBookBtn = findViewById(R.id.add_book_btn);
        mGetBookBtn = findViewById(R.id.get_book_btn);
        mBinderPoolBtn = findViewById(R.id.binder_pool_btn);
    }

    void initEvent() {
        mAddBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mBookManager.addBook(new Book(3, "book3"));
                    Log.d("gpj", TAG + "query book list "
                            + mBookManager.getBookList().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        mGetBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<Book> list = null;
                        try {
                            list = mBookManager.getBookList();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        Log.d("gpj", TAG + "query book list,list type: "
                                + list.getClass().getCanonicalName());
                        Log.d("gpj", TAG + "query book list " + list.toString());
                    }
                }).start();
            }
        });

        mBinderPoolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        binderPoolWork();
                    }
                }).start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mBookManager != null
                && mBookManager.asBinder().isBinderAlive()) {
            Log.d("gpj", TAG + "unregister listener:  "
                    + mIOnNewBookArrivedListener);
            try {
                mBookManager.unregisterListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        unbindService(mConnection);
        super.onDestroy();

    }

    static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MESSAGE_NRE_BOOK_ARRIVED:
                    Log.d("gpj", TAG + "receive new book :" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }

    private void binderPoolWork(){
        BinderPoolHelper binderPoolHelper = BinderPoolHelper.getInsatance(this);

        Log.d(TAG, "visit ISayHello");
        IBinder helloBinder = binderPoolHelper.queryBinder(BinderPoolImpl.BINDER_SAY_HELLO);
        mSayHello = SayHelloImpl.asInterface(helloBinder);
        String msg = "helloworld";
        Log.d("gpj",TAG+"content:" + msg);

        try {
            String rmsg = mSayHello.hello(msg);
            Log.d("gpj",TAG+"ISayHello reply:" + rmsg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        Log.d(TAG, "visit ICompute");
        IBinder computeBinder = binderPoolHelper.queryBinder(BinderPoolImpl.BINDER_COMPUTE);
        mCompute = ComputeImpl.asInterface(computeBinder);
        try {
            System.out.println("1+1=" + mCompute.add(1, 1));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
