package gpj.com.aidldemo.binderpool;

import android.os.RemoteException;

/**
 * Created by gpj on 2018/3/26.
 */

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }
}
