package com.example.vi34.lesson3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MyActivity extends Activity {

    String textToTranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // make this call by button
        //downloadImages();
        //-------
        setContentView(R.layout.firstscreen);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }
    // not sure where it must be...
    public void myClickHandler(View view) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

        } else {
            // display error
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*// move this method to second activity
    protected void downloadImages()
    {
        // now try to return single image - later need to add Adapter
        ImageDownloader imageDownloader = new ImageDownloader();
        Bitmap result = imageDownloader.search("cat");

    }*/

    public void onClick(View view) {
        EditText textInEnglish = (EditText) findViewById(R.id.editText);
        textToTranslate = textInEnglish.getText().toString();
        if (textToTranslate.length() == 0) {
            //exception
        } else {
            //execution
        }
        Intent intent = new Intent(MyActivity.this, SecondActivity.class);
        intent.putExtra("Text to translate", textToTranslate);
        startActivity(intent);
    }

}
