package com.abrafcm.translator.translator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * Created by Nikita Yaschenko on 27.09.14.
 */
public class FakeImagesProvider implements IImagesProvider {

    private Context mContext;
    private Bitmap bitmap;

    public FakeImagesProvider(Context context) {
        mContext = context;
        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
    }

    @Override
    public Bitmap getImage(String url) {
        return bitmap;
    }

    @Override
    public ArrayList<ImageItem> getItems(String word) {
        ArrayList<ImageItem> items = new ArrayList<ImageItem>();
        for (int i = 0; i < 10; i++) {
            items.add(new ImageItem("fake url"));
        }
        return items;
    }
}
