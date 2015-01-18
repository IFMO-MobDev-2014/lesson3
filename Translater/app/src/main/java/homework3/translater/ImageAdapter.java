package homework3.translater;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anstanasia on 18.01.2015.
 */

public class ImageAdapter extends BaseAdapter {
    List<Bitmap> images = new ArrayList<Bitmap>();
    Context context;

    public ImageAdapter(Context c, List<Bitmap> l) {
        context = c;
        images = l;
    }

    public int getCount() {
        return images.size();
    }

    public Bitmap getItem(int position) {
        return images.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(90 * (int) Main.x, 90 * (int) Main.x));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        Bitmap bitmap = getItem(position);

        if (bitmap == null) {
           bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.not_found);
        }

        imageView.setImageBitmap(bitmap);

        return imageView;
    }
}
