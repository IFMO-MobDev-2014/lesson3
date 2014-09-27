package com.abrafcm.translator.translator;

import android.graphics.Bitmap;

/**
 * Created by Nikita Yaschenko on 27.09.14.
 */
public interface IImagesProvider {
    public Bitmap getImage(String url);
}
