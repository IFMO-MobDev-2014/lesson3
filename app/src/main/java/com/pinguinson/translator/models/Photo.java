package com.pinguinson.translator.models;

import android.graphics.drawable.Drawable;

/**
 * Created by pinguinson on 12.10.2014.
 */
public class Photo {
    private String url;
    private Drawable drawable;

    public Photo(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
