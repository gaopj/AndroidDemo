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


public class MainActivity extends Activity {
    public static final String TAG = "MainActivity->";

    private static final int MESSAGE_NRE_BOOK_ARRIVED = 1;

    private Button mAddBookBtn;
    private Button mGetBookBtn;

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
}
