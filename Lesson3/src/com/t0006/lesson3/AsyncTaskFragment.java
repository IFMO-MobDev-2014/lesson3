package com.t0006.lesson3;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by dimatomp on 27.09.14.
 */
public class AsyncTaskFragment extends Fragment implements View.OnClickListener {
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

    public void onClick(View view) {
        imagesAdapter.moreContent();
        imageLoaderTask = new ImageLoaderTask(this, imagesAdapter);
        imageLoaderTask.execute(cWord);
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
        translationLoaderTask.execute(newWord);
        onClick(null);
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
