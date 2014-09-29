package ru.ifmo.md.lesson3;

import android.os.AsyncTask;
import static ru.ifmo.md.lesson3.Translator.TranslateLanguage.*;

/**
 * @author volhovm
 *         Created on 9/27/14
 */

public class FindTranslationTask extends AsyncTask<String, Void, String> {
    MainActivity activity;
    Exception exception;

    public FindTranslationTask(MainActivity mainActivity) {
        activity = mainActivity;
    }

    @Override
    protected String doInBackground(String[] strings) {
        String input = strings[0];
        Translator translator;
        if (input.matches("[А-я\\s\\d]+"))
            translator = new Translator(Russian, English);
        else translator = new Translator(English, Russian);
        try {
            return translator.translate(input);
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    protected void onPostExecute(String result) {
        if (exception == null) {
            activity.onTranslateResponse(result);
        } else {
            activity.onTranslateFail(exception);
        }
    }
}
