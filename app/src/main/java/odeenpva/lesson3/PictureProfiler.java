package odeenpva.lesson3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Женя on 27.09.2014.
 */
public class PictureProfiler {
    private Bitmap image;
    private Bitmap qualityImage;
    private String qRef;
    public PictureProfiler(Bitmap img, String ref) {
        image = img;
        qRef = ref;
    }
    public Bitmap getImage() {
        return image;
    }
    public Bitmap getQualityImage() {
        return qualityImage;
    }
    public String getQRef() {
        return qRef;
    }

}
