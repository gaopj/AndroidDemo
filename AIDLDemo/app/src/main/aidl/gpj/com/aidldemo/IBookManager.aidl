// IBookManager.aidl
package gpj.com.aidldemo;

// Declare any non-default types here with import statements

import gpj.com.aidldemo.bean.Book;
import gpj.com.aidldemo.IOnNewBookArrivedListener;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
