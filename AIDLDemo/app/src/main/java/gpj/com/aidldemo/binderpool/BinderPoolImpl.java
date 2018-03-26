package gpj.com.aidldemo.binderpool;

import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by gpj on 2018/3/26.
 */

public class BinderPoolImpl extends IBinderPool.Stub {
    public static final int BINDER_SAY_HELLO = 1;
    public static final int BINDER_COMPUTE = 2;

    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder = null;
        switch (binderCode) {
            case BINDER_SAY_HELLO:
                binder = new SayHelloImpl();
                break;
            case BINDER_COMPUTE:
                binder = new ComputeImpl();
                break;
            default:
                break;
        }
        return binder;
    }
}
