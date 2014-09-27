package com.abrafcm.translator.translator;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Nikita Yaschenko on 27.09.14.
 */
public interface IImagesProvider {
    public Bitmap getImage(String url);
    public ArrayList<ImageItem> getItems(String word);
}
