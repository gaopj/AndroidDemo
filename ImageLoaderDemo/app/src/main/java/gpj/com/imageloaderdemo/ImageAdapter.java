package gpj.com.imageloaderdemo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by v-pigao on 3/31/2018.
 */

public class ImageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Drawable mDefaultBitmapDrawable;
    private List<String> mUrList;

    private ImageViewSetListener mListener;
    public ImageAdapter(Context context,List<String> list ,ImageViewSetListener listener) {
        mInflater = LayoutInflater.from(context);
        mDefaultBitmapDrawable = context.getResources().getDrawable(R.drawable.image_default);
        mUrList = list;
        mListener = listener;
    }

    @Override
    public int getCount() {
        return mUrList.size();
    }

    @Override
    public String getItem(int position) {
        return mUrList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.image_list_item,parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageView imageView = holder.imageView;
        final String tag = (String)imageView.getTag();
        final String uri = getItem(position);
        if (!uri.equals(tag)) {
            imageView.setImageDrawable(mDefaultBitmapDrawable);
        }
        mListener.bingImageView(imageView,uri);

        return convertView;
    }

    private static class ViewHolder {
        public ImageView imageView;
    }

    public interface ImageViewSetListener{
        void bingImageView(ImageView imageView,String uri);
    }
}
