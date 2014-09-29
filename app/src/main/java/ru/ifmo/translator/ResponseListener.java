package ru.ifmo.translator;

import android.graphics.drawable.Drawable;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
public interface ResponseListener {
    void onResponse(String result, Drawable[] images);
}
