package ru.ifmo.md.lesson3;

import android.os.AsyncTask;

/**
 * @author volhovm
 *         Created on 9/27/14
 */

public class FindTranslationTask extends AsyncTask<String, Void, String> {
    MainActivity activity;
    public FindTranslationTask(MainActivity mainActivity) {
        activity = mainActivity;
    }

    @Override
    protected String doInBackground(String[] strings) {
        String input = strings[0];
        Translator translator;
        if (input.matches("[А-я\\s\\d]+"))
            translator = new Translator(Translator.TranslateDirection.RussianToEnglish);
        else translator = new Translator(Translator.TranslateDirection.EnglishToRussian);
        return translator.translate(input);
    }

    protected void onPostExecute(String result) {
        activity.onTranslateResponse(result);
    }
}
