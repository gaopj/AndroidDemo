package com.gpj.httpsdemo;

import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by v-pigao on 5/14/2018.
 */

public class Https {
    public static final String TAG="Https";
    public static final int RESULT_SAFE=0;
    public static final int RESULT_WRONG=1;
    public static int checkCertificate(String urlPath) {
        int rst = RESULT_SAFE;

        //需要进行Https连接
        HttpsURLConnection conn = null;
        try {
            //Https连接可以指定SSLSocketFactory
            SSLSocketFactory sf = null;
            try {
                //创建SSLContext
                SSLContext sc = SSLContext.getInstance("TLS");
                //指定我们自定义的TrustManager
                sc.init(null, createTrustManager(), null);

                sf = sc.getSocketFactory();
            } catch (Exception e) {
                Log.e(TAG,e.toString());
            }

            URL url = new URL(urlPath);
            //openConnection仅创建出HttpsURLImplConnection，没有进行实际的网络访问
            //还可以设置参数
            conn = (HttpsURLConnection) url.openConnection();
            if (sf != null) {
                //指定其SSLSocketFactory
                conn.setSSLSocketFactory(sf);
            }

            //进行实际的网络访问时，就会进行证书检测
            conn.connect();
        } catch (IOException e) {
            String errorMsg = e.getMessage();
            Log.e(TAG,e.toString());
            rst = RESULT_WRONG;

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rst;
    }

    private static TrustManager[] createTrustManager() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                        //not implemented
                    }

                    //这个函数就是检测访问的网址，服务端返回的证书
                    //其中，chain就是需要验证的证书链，authType是认证类型
                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                        localCheckServerTrusted(chain, authType);
                    }
                }
        };
    }
    private static void localCheckServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        Exception errEx = null;
        try {
            Class applicationConfigImpl
                    = Class.forName("android.security.net.config.ApplicationConfig");
            Method getDefaultInstanceM
                    = applicationConfigImpl.getDeclaredMethod("getDefaultInstance");
            //反射加载ApplicationConfig对象
            Object applicationConfigObj = getDefaultInstanceM.invoke(null);

            Class rootTrustManagerImpl
                    = Class.forName("android.security.net.config.RootTrustManager");
            Constructor rootConstructor
                    = rootTrustManagerImpl.getConstructor(applicationConfigImpl);
            //反射加载RootTrustManager
            Object rootTrustManagerObj = rootConstructor.newInstance(applicationConfigObj);

            //找到checkServerTrusted方法
            Method checkServerTrustedM = rootTrustManagerImpl.getDeclaredMethod(
                    "checkServerTrusted", X509Certificate[].class, String.class);

            //调用即可
            checkServerTrustedM.invoke(rootTrustManagerObj, chain, authType);
        } catch (ClassNotFoundException e) {
            errEx = e;
        } catch (NoSuchMethodException e) {
            errEx = e;
        } catch (IllegalAccessException e) {
            errEx = e;
        } catch (InstantiationException e) {
            errEx = e;
        } catch (InvocationTargetException e) {
            //打印校验异常的栈信息，做特殊处理
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            PrintWriter pw = new PrintWriter(buf, true);
            e.printStackTrace(pw);
            String expMessage = buf.toString();
            pw.close();
            throw new CertificateException( e);

        }
        if (errEx != null) {
            throw new CertificateException( errEx);
        }
    }
}
