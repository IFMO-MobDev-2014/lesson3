package com.example.home.lesson3;

import android.graphics.Bitmap;

/**
 * Created by Home on 27.09.2014.
 */

public class RowItem {
    private Bitmap imageId;

    public RowItem(Bitmap imageId) {
        this.imageId = imageId;
    }
    public Bitmap getImageId() {
        return imageId;
    }
    public void setImageId(Bitmap imageId) {
        this.imageId = imageId;
    }
    @Override
    public String toString() {
        return "";
    }
}