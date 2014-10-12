package com.pinguinson.translator.models;

import android.graphics.drawable.Drawable;

/**
 * Created by pinguinson on 12.10.2014.
 */
public class Photo {
    private String id;
    private String url;
    private Drawable drawable;

    public Photo(){

    }

    public Photo(String id) {
        this.id = id;
    }

    public Photo(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
