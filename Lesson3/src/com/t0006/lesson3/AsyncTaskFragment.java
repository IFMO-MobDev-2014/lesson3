package com.t0006.lesson3;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by dimatomp on 27.09.14.
 */
public class AsyncTaskFragment extends Fragment {
    /*package local*/ TranslationLoaderTask translationLoaderTask;
    /*package local*/ ImageLoaderTask imageLoaderTask;
    /*package local*/ TranslationsAdapter translationsAdapter = new TranslationsAdapter(this);
    /*package local*/ ImagesAdapter imagesAdapter = new ImagesAdapter(this);
    /*package local*/ String cWord;

    private Collection<Integer> preservedMessages = new LinkedList<>();

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

        translationLoaderTask = new TranslationLoaderTask(this, translationsAdapter);
        imageLoaderTask = new ImageLoaderTask(this, imagesAdapter);
        translationLoaderTask.execute(newWord);
        imageLoaderTask.execute(newWord);
    }

    public void showMessage(int res) {
        if (isDetached())
            preservedMessages.add(res);
        else
            getActivity().runOnUiThread(new MessageDisplayer(res));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        for (int res : preservedMessages)
            showMessage(res);
        preservedMessages.clear();
    }

    class MessageDisplayer implements Runnable {
        final int res;

        MessageDisplayer(int res) {
            this.res = res;
        }

        @Override
        public void run() {
            Toast.makeText(getActivity(), res, Toast.LENGTH_LONG).show();
        }
    }
}
