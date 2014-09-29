package com.dreamteam.translator.translator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class SearchField extends Activity {

    public static final String QUERY = "query";
    public static final String TRANSLATION_RESULT = "translationResult";
    public static final String IMAGES = "images";
    public static final long TRANSLATION_TIMEOUT = 4000;

    private Button searchButton;
    private EditText searchField;
    private Intent searchIntent;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_field);
        searchButton = (Button) findViewById(R.id.search_button);
        searchField = (EditText) findViewById(R.id.search_field);
        searchIntent = new Intent(this, ResultsList.class);
        dialog = new ProgressDialog(this);
        dialog.setTitle(getString(R.string.dialog_title));
    }

    public void sendMessage(View view) {
        String query = searchField.getText().toString();
        Log.i("QUERY MSG", query);
        searchIntent.putExtra(QUERY, query);
        dialog.setMessage(getString(R.string.translating_msg));
        dialog.show();
        QueryTranslateTask translate = new QueryTranslateTask(this, query);
        TimeoutTaskRunner.runTask(translate, TRANSLATION_TIMEOUT);
    }

    public void onTranslateFinished(String result) {
        searchIntent.putExtra(TRANSLATION_RESULT, result);
        ImageSearchTask imSearch = new ImageSearchTask(this);
        imSearch.execute(result);
        dialog.setMessage(getString(R.string.search_images_msg));
    }

    public void onImageSearchFinished(ArrayList<String> urls) {
        searchIntent.putStringArrayListExtra(IMAGES, urls);
        dialog.dismiss();
        startActivity(searchIntent);
    }

    public void onImageSearchCancelled() {
        dialog.dismiss();
        // TODO: show error message
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
