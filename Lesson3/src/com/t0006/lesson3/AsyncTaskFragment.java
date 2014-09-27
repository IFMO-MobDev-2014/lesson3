package com.t0006.lesson3;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by dimatomp on 27.09.14.
 */
public class AsyncTaskFragment extends Fragment {
    /*package local*/ TranslationLoaderTask translationLoaderTask;
    /*package local*/ ImageLoaderTask imageLoaderTask;
    /*package local*/ TranslationsAdapter translationsAdapter;
    /*package local*/ ImagesAdapter imagesAdapter;
    /*package local*/ String cWord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setWord(String newWord) {
        if (newWord.equals(cWord))
            return;
        cWord = newWord;
        if (translationLoaderTask != null)
            translationLoaderTask.cancel(true);
        if (imageLoaderTask != null)
            imageLoaderTask.cancel(true);
        translationsAdapter.reset();
        imagesAdapter.reset();

        translationLoaderTask = new TranslationLoaderTask(translationsAdapter);
        // TODO No contexts here!
        imageLoaderTask = new ImageLoaderTask(getActivity(), imagesAdapter);
        translationLoaderTask.execute(newWord);
        imageLoaderTask.execute(newWord);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        translationsAdapter.setContext(activity);
        imagesAdapter.setContext(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        translationsAdapter.setContext(null);
        imagesAdapter.setContext(null);
    }
}
