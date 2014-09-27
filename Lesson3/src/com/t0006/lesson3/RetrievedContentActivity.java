package com.t0006.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Created by dimatomp on 26.09.14.
 */
public class RetrievedContentActivity extends Activity {
    public static final String EXTRA_NEW_NAME = "com.t0006.lesson3.RetrievedContentActivity.EXTRA_NEW_NAME";
    public static final String TAG_TASK_FRAGMENT = "com.t0006.lesson3.RetrievedContentActivity.TAG_TASK_FRAGMENT";
    public static final String SAVED_INSTANCE_TAB_NUM = "com.t0006.lesson3.RetrievedContentActivity.SAVED_INSTANCE_TAB_NUM";

    AsyncTaskFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_results);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec child = tabHost.newTabSpec("translations");
        child.setIndicator(getString(R.string.translations));
        child.setContent(R.id.translations);
        tabHost.addTab(child);
        child = tabHost.newTabSpec("images");
        child.setIndicator(getString(R.string.images));
        child.setContent(R.id.images);
        tabHost.addTab(child);
        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_TAB_NUM))
            tabHost.setCurrentTab(savedInstanceState.getInt(SAVED_INSTANCE_TAB_NUM));

        fragment = (AsyncTaskFragment) getFragmentManager().findFragmentByTag(TAG_TASK_FRAGMENT);
        if (fragment == null) {
            fragment = new AsyncTaskFragment();
            getFragmentManager().beginTransaction().add(fragment, TAG_TASK_FRAGMENT).commit();
            startActivityForResult(new Intent(this, InputWordActivity.class), 0);
        } else {
            ((TextView) findViewById(R.id.set_name_text)).setText(fragment.cWord);
            updateWord();
        }

        ListView translations = (ListView) findViewById(R.id.translations);
        translations.setAdapter(fragment.translationsAdapter);

        GridView images = (GridView) findViewById(R.id.images);
        images.setAdapter(fragment.imagesAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_TAB_NUM, ((TabHost) findViewById(R.id.tabHost)).getCurrentTab());
    }

    private void updateWord() {
        String word = ((TextView) findViewById(R.id.set_name_text)).getText().toString();
        if (word == null || word.isEmpty()) {
            findViewById(R.id.tabHost).setVisibility(View.INVISIBLE);
            return;
        }
        fragment.setWord(word);
        findViewById(R.id.tabHost).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String text = data.getStringExtra(EXTRA_NEW_NAME);
            TextView view = (TextView) findViewById(R.id.set_name_text);
            view.setText(text);
            updateWord();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.retrieved_content_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.changeWord) {
            Intent intent = new Intent(this, InputWordActivity.class);
            String text = ((TextView) findViewById(R.id.set_name_text)).getText().toString();
            intent.putExtra(InputWordActivity.EXTRA_PREV_NAME, text);
            startActivityForResult(intent, 0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}