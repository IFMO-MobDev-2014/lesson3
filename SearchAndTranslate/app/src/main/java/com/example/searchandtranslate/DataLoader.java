package com.example.searchandtranslate;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.TextView;

/**
 * Created by knah on 24.09.2014.
 */

public class DataLoader {

    public static class MyCallbackString implements MyCallback<String> {
        public MyCallbackString(){}
        public void run(String param) {
            OutputActivity.text.setText(param);
        }
    }

    public static class MyCallbackPicture implements  MyCallback<Bitmap[]> {
        Context context;

        public MyCallbackPicture(Context context) {
            this.context = context;
        }

        public void run(Bitmap[] param) {
            OutputActivity.grid.setAdapter(new PicturesAdapter(context, param));
        }
    }

    public static void asyncTranslate(String word, MyCallbackString callback) {
        callback.run(word); // TODO: write proper code
    }

    private static final int[] testBitmap = {
            0xff0000ff, 0xff00ffff, 0xff00ff00, 0xffffff00,
            0xffff0000, 0xffff00ff, 0xffffffff, 0xff000000,
            0xff999999, 0xffffffff, 0xffffffff, 0xffffffff,
            0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff
    };

    public static void asyncLoadPictures(String word, MyCallbackPicture callback) {
        Bitmap rv = Bitmap.createBitmap(testBitmap, 4, 4, Bitmap.Config.ARGB_8888);
        callback.run(new Bitmap[] {rv}); // TODO: write proper code
    }
}
