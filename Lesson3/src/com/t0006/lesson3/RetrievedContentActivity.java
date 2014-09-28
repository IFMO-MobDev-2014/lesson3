package com.t0006.lesson3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Created by dimatomp on 26.09.14.
 */
public class RetrievedContentActivity extends Activity {
    public static final String EXTRA_SET_WORD = "com.t0006.lesson3.RetrievedContentActivity.EXTRA_SET_WORD";
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
            String word = getIntent().getStringExtra(EXTRA_SET_WORD);
            ((TextView) findViewById(R.id.set_name_text)).setText(word);
        } else
            ((TextView) findViewById(R.id.set_name_text)).setText(fragment.cWord);
        updateWord();

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
        CharSequence text = ((TextView) findViewById(R.id.set_name_text)).getText();
        if (text == null || text.length() == 0) {
            findViewById(R.id.tabHost).setVisibility(View.INVISIBLE);
            return;
        }
        fragment.setWord(text.toString());
        findViewById(R.id.tabHost).setVisibility(View.VISIBLE);
    }
}