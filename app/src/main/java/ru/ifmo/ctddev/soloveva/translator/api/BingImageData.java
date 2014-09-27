package ru.ifmo.ctddev.soloveva.translator.api;

import com.google.gson.annotations.SerializedName;

import ru.ifmo.ctddev.soloveva.translator.ImageData;

/**
 * Created by maria on 26.09.14.
 */
public class BingImageData implements ImageData {
    @SerializedName("MediaUrl")
    private String url;
    @SerializedName("Width")
    private int width;
    @SerializedName("Height")
    private int height;

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    BingImageData() {}
}
