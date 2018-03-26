// IBinderPool.aidl
package gpj.com.aidldemo.binderpool;

// Declare any non-default types here with import statements

interface IBinderPool {
    IBinder queryBinder(int binderCode);
}
