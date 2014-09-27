package ru.ifmo.md.lesson3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//TODO
public class ImageFetcher {
    private final Resources res;
    private final int N;
    public ImageFetcher(int num, Resources r) {
        res = r;
        N = num;
    }

    public Bitmap[] fetch(String query) {
        Bitmap[] imgs = new Bitmap[N];
        imgs[0] = BitmapFactory.decodeResource(res, R.drawable.lena);
        for (int i = 1; i < N; i++) {
            imgs[i] = imgs[0];
        }
        return imgs;
    }
}
