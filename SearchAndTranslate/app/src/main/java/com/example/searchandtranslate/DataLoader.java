package com.example.searchandtranslate;

import android.graphics.Bitmap;

/**
 * Created by knah on 24.09.2014.
 */
public class DataLoader {

    public static void asyncTranslate(String word, MyCallback<String> callback) {
        callback.run(word); // TODO: write proper code
    }

    private static final int[] testBitmap = {
            0xff0000ff, 0xff00ffff, 0xff00ff00, 0xffffff00,
            0xffff0000, 0xffff00ff, 0xffffffff, 0xff000000,
            0xff999999, 0xffffffff, 0xffffffff, 0xffffffff,
            0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff
    };

    public static void asyncLoadPictures(String word, MyCallback<Bitmap[]> callback) {
        Bitmap rv = Bitmap.createBitmap(testBitmap, 4, 4, Bitmap.Config.ARGB_8888);
        callback.run(new Bitmap[] {}); // TODO: write proper code
    }
}
