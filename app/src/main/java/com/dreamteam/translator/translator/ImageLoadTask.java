package com.dreamteam.translator.translator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by anton on 25/09/14.
 */
public class ImageLoadTask implements Runnable {
    Context ctx;
    private ImageView imageView;
    private Drawable[] cachedImages;
    private Integer position;
    private String url;

    public ImageLoadTask(ImageView imageView, Drawable[] cachedImages, int position, Context ctx, String url) {
        this.imageView = imageView;
        this.cachedImages = cachedImages;
        this.position = position;
        this.ctx = ctx;
        this.url = url;
    }

    public ImageLoadTask(Context ctx, ImageView imageView, String url) {
        this.ctx = ctx;
        this.imageView = imageView;
        this.url = url;
    }

    @Override
    public void run() {
        Drawable initial;

        try {
            InputStream is = (InputStream) new URL(url).getContent();
            initial = Drawable.createFromStream(is, "");
        } catch (IOException e) {
            initial = ctx.getResources().getDrawable(R.drawable.image_error);
        }

        if (cachedImages != null && cachedImages[position] == null) {
            cachedImages[position] = initial;
        }
        if (ctx instanceof ResultsList) {
            ResultsList activity = (ResultsList) ctx;
            activity.setImageView(imageView, initial);
        } else {
            ImagePreview activity = (ImagePreview) ctx;
            activity.setImageView(imageView, initial);
        }
    }
}
