package gpj.com.imageloaderdemo.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gpj on 2018/3/30.
 */

// 用于获取压缩适配后的图片
public class ImageResizeHelper {
    private static final String TAG = "ImageResizer";


    public static Bitmap decodeSampledBitmapFromInputStream(InputStream in, int reqWidth, int reqHeight) {
        // 先设置inJustDecodeBounds 用于计算options，此时不真正读取图片至内存

        Bitmap bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = in.read(buffer)) > -1 ) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());
        InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(stream1, null, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeStream(stream2, null, options);
        try {
            in.close();
            baos.close();
            stream1.close();
            stream2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }

        // 图片原生尺寸
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(TAG, "origin, w= " + width + " h=" + height);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            //计算最大insamplesize值是2的幂，同时保持高度和宽度大于所要求的高度和宽度。
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        Log.d(TAG, "sampleSize:" + inSampleSize);
        return inSampleSize;
    }
}
