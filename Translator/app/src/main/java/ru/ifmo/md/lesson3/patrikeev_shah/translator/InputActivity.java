package ru.ifmo.md.lesson3.patrikeev_shah.translator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.concurrent.ExecutionException;

public class InputActivity extends Activity {

    private EditText wordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        wordField = (EditText) findViewById(R.id.editText);
    }

    public void translateClicked(View view) {
        String wordToTranslate = wordField.getText().toString();
        Log.d("Word", "entered " + wordToTranslate);
        String translation = getTranslation(wordToTranslate);
        Intent resultScreenIntent = new Intent(this, ResultActivity.class);
        resultScreenIntent.putExtra(getPackageName() + ".initialWord", wordToTranslate);
        resultScreenIntent.putExtra(getPackageName() + ".translatedWord", translation);
        startActivity(resultScreenIntent);
    }

    private String getTranslation(String wordToTranslate) {
        TranslationReceiver translationReceiver = new TranslationReceiver();
        translationReceiver.execute(wordToTranslate);
        String result = null;
        try {
            result = translationReceiver.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

}
