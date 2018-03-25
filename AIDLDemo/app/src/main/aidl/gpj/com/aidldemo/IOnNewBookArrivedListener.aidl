// IOnNewBookArrivedListener.aidl
package gpj.com.aidldemo;

import gpj.com.aidldemo.bean.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
  void onNewBookArrived(in Book newBook);
}
