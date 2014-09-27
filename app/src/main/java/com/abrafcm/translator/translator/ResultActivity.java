package com.abrafcm.translator.translator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;


public class ResultActivity extends Activity {
    private static final String TAG = "ResultActivity";

    private ITranslateProvider mTranslator;
    private IImagesProvider mImagesProvider;
    private GridView mGridView;
    private TextView mTranslationTextView;
    private ArrayList<ImageItem> mItems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String translateString = intent.getStringExtra(Helper.EXTRA_TRANSLATE);

        setupViews();

        mTranslator = new FakeTranslateProvider();
        mImagesProvider = new FakeImagesProvider(this);
        mItems = mImagesProvider.getItems(translateString);

        setupAdapter();

        mTranslationTextView.setText(mTranslator.translate(translateString));
    }

    private void setupViews() {
        mGridView = (GridView) findViewById(R.id.grid_view);
        mTranslationTextView = (TextView) findViewById(R.id.text_translation);
    }

    private void setupAdapter() {
        if (mItems == null) {
            mGridView.setAdapter(null);
        } else {
            mGridView.setAdapter(new ImageAdapter(this, mItems, mImagesProvider));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
