package ru.ifmo.mobdev.translator.models;

import android.graphics.drawable.Drawable;

/**
 * @author nqy
 */
public class Picture {
    private String name;
    private String author;
    private String url;
    private String thumbnailUrl;
    private Drawable drawable;

    public Picture(String name, String author, String url) {
        this.name = name;
        this.author = author;
        this.url = url;
        this.thumbnailUrl = url.replace("4.jpg", "3.jpg");
        drawable = null;
    }

    public Picture() {
        name = null;
        author = null;
        url = null;
        thumbnailUrl = null;
        drawable = null;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
