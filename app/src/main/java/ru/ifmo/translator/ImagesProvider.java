package ru.ifmo.translator;

import android.graphics.drawable.Drawable;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
public class ImagesProvider {
    private static Drawable[] images;

    public static Drawable[] getImages() {
        return images;
    }

    public static void setImages(Drawable[] images) {
        ImagesProvider.images = images;
    }
}
