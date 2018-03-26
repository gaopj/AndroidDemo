package gpj.com.aidldemo.binderpool;

import android.os.RemoteException;

/**
 * Created by gpj on 2018/3/26.
 */

public class SayHelloImpl extends ISayHello.Stub {
    @Override
    public String hello(String content) throws RemoteException {
        return "hello->" +content;
    }
}
