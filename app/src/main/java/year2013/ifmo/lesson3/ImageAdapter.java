package year2013.ifmo.lesson3;

/**
 * Created by Юлия on 29.09.2014.
 */
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
           // imageView.setLayoutParams(new GridView.LayoutParams(50, 250));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    public	Integer[] mThumbIds = { R.drawable.l2561, R.drawable.l2562,
            R.drawable.l2563, R.drawable.l2564, R.drawable.l2565,
            R.drawable.l2566, R.drawable.l2561, R.drawable.l2562,
            R.drawable.l2563};
}
