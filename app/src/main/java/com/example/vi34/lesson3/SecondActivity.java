package com.example.vi34.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends Activity {

    public static ImageView[] imageView = new ImageView[10];
    String translation;
    String textToTranslate;
    public static Bitmap[] bmp = new Bitmap[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textToTranslate = getIntent().getStringExtra("Text to translate");

        downloadImages("nyancat");

        setContentView(R.layout.secondscreen);

        TextView russianText = (TextView) findViewById(R.id.TextView2);
        // take a translation
        translation = "Hello, world!!!";
        russianText.setText(textToTranslate); // intent works


        //Ooh Max! It's too "Bydlo code"!
        imageView[0] = (ImageView) findViewById(R.id.imageView2);
        imageView[1] = (ImageView) findViewById(R.id.imageView3);
        imageView[2] = (ImageView) findViewById(R.id.imageView4);
        imageView[3] = (ImageView) findViewById(R.id.imageView5);
        imageView[4] = (ImageView) findViewById(R.id.imageView6);
        imageView[5] = (ImageView) findViewById(R.id.imageView7);
        imageView[6] = (ImageView) findViewById(R.id.imageView8);
        imageView[7] = (ImageView) findViewById(R.id.imageView9);
        imageView[8] = (ImageView) findViewById(R.id.imageView10);
        imageView[9] = (ImageView) findViewById(R.id.imageView11);

        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.source);
        imageView[0].setImageBitmap(bmp[0]);

        imageView[1].setImageBitmap(bitmap);
        imageView[2].setImageBitmap(bitmap);
        imageView[3].setImageBitmap(bitmap);
        imageView[4].setImageBitmap(bitmap);
        imageView[5].setImageBitmap(bitmap);
        imageView[6].setImageBitmap(bitmap);
        imageView[7].setImageBitmap(bitmap);
        imageView[8].setImageBitmap(bitmap);
        imageView[9].setImageBitmap(bitmap);
        //download and print images
    }

    protected void downloadImages(String query)
    {
        // To do: need to add Adapter
        ImageDownloader imageDownloader = new ImageDownloader();
        imageDownloader.search(query);

    }

    /*
    public void redrawImages(){
        imageView[0].invalidate();
        imageView[1].invalidate();
    }
*/

    public void onBackPressed() {
        Intent intent = new Intent(SecondActivity.this, MyActivity.class);
        startActivity(intent);
        //finish();
    }

}