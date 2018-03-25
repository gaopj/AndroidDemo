package gpj.com.aidldemo;

import android.app.Application;
import android.util.Log;
import android.os.Process;

/**
 * Created by gpj on 2018/3/25.
 */

public class MyApp extends Application {
    public static final String TAG = "MyApp->";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("gpj", TAG + "Pid:" + Process.myPid()
                + ", Uid:" + Process.myUid() + ", Tid:" + Process.myTid());
        Log.d("gpj", TAG + Utils.getCurProcessName(this));
    }
}
