package gpj.com.imageloaderdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gpj on 2018/3/30.
 */

public class ImageLoader {
    private static final String TAG = "ImageLoader-->";

    private static final int MESSAGE_POST_RESULT = 1;


    private static final int CUP_COUNT = Runtime.getRuntime().availableProcessors();   //cpu进程个数
    private static final int COUR_POOL_SIZE = CUP_COUNT + 1;//线程池核心线程数
    private static final int MAXIMUM_POOL_SIZE = CUP_COUNT * 2 + 1;//最大线程数
    private static final long KEEP_ALIVE_TIME = 10L; //非核心线程闲置时的超时时长（秒）

    private static final int TAG_KEY_URI = 999; //用于给imageView设置uri标签，方便复用
    private static final int IO_BUFFER_SIZE = 8 * 1024; //设置io流缓存大小（byte）

    private boolean mIsDiskLruCacheCreate = false; //磁盘缓存是否创建完毕

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {

        private final AtomicInteger mCount = new AtomicInteger(1); //计数开线程个数，原子操作，线程安全

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "ImageThread_" + mCount.getAndIncrement());
        }
    };

    // 创建线程池
    public static final Executor THREAD_POOL_EXECUTOR
            = new ThreadPoolExecutor(COUR_POOL_SIZE,
            MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), sThreadFactory);


    // 在住线程中设置imageView 图片资源
    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_POST_RESULT) {
                LoaderResult result = (LoaderResult) msg.obj;
                ImageView imageView = result.imageView;
                String uri = (String) imageView.getTag(TAG_KEY_URI);
                if (uri.equals(result.uri))
                    imageView.setImageBitmap(result.bitmap);
                else {
                    Log.w(TAG, "set image bitmap,but url has changed, ignored!");
                }
            }
        }
    };

    private Context mContext;
    private LruCache<String, Bitmap> mMemoryCache;

    private ImageLoader(Context context) {
        mContext = context.getApplicationContext();
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024;
        int cacheSize = (int) (maxMemory / 8);

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    public static ImageLoader getInstance(Context context) {
        return new ImageLoader(context);
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        mMemoryCache.put(key, bitmap);
    }

    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    // 下载并图片 ，并和imageview 绑定
    public void bindBitmap(final String uri, final ImageView imageView) {
        bindBitmap(uri, imageView, 0, 0);
    }

    public void bindBitmap(final String uri, final ImageView imageView, final int reqWidth, final int reqHeight) {
        imageView.setTag(TAG_KEY_URI, uri);
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(uri, reqWidth, reqHeight);
                if (bitmap != null) {
                    LoaderResult result = new LoaderResult(imageView, uri, bitmap);
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget();
                }
            }
        };

        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }

    public Bitmap loadBitmap(String uri, int reqWidth, int reqHeight) {
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if (bitmap != null) {
            Log.d(TAG, "loadBitmapFromMemCache,url:" + uri);
            return bitmap;
        }
        bitmap = downloadBitmapFromUrl(uri, reqWidth, reqHeight);

        return bitmap;
    }

    private Bitmap loadBitmapFromMemCache(String url) {
        final String key = hashKeyFormUrl(url);
        Bitmap bitmap = getBitmapFromMemCache(key);
        return bitmap;
    }

    // 将url 经过MD5 处理成字符串，方便做key
    private String hashKeyFormUrl(String url) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    // 字节数组转化成字符串
    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    // 从网上下载图片
    private Bitmap downloadBitmapFromUrl(String urlString, int reqWidth, int reqHeight) {
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),
                    IO_BUFFER_SIZE);
            bitmap = ImageResizeHelper.decodeSampledBitmapFromInputStream(in,
                    reqWidth, reqHeight);
            if (bitmap != null) {
                String key = hashKeyFormUrl(urlString);
                addBitmapToMemoryCache(key, bitmap);
            }
        } catch (final IOException e) {
            Log.e(TAG, "Error in downloadBitmap: " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return bitmap;
    }

    private static class LoaderResult {
        public ImageView imageView;
        public String uri;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView, String uri, Bitmap bitmap) {
            this.imageView = imageView;
            this.uri = uri;
            this.bitmap = bitmap;
        }
    }
}
