package ru.ifmo.mobdev.translator;

import android.os.AsyncTask;

/**
 * Created by sugakandrey on 19.09.14.
 */
public class Translator extends AsyncTask<String, Void, String>{
    MainActivity mainScreen;

    public Translator(MainActivity resultScreen) {
        this.mainScreen = resultScreen;
    }

    @Override
    protected String doInBackground(String... strings) {
        return "placeholder";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mainScreen.onTranslation(s);
    }
}
