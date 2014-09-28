package ru.ifmo.ctddev.katunina.translator;

/**
 * Created by Евгения on 28.09.2014.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import ru.ifmo.ctddev.soloveva.translator.R;


public class TranslateActivity extends Activity {

    class ResponseContainer {
        //see schema at http://api.yandex.com/translate/doc/dg/reference/translate.xml

        int code;
        String lang;
        List<String> text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        findViewsByIds();
    }

    private void findViewsByIds() {
        edtInput = ((EditText) findViewById(R.id.edtInput));
        btnTranslate = ((Button) findViewById(R.id.btnTranslate));
        tvResult = ((TextView) findViewById(R.id.tvResult));
        pbTranslating = ((ProgressBar) findViewById(R.id.pbTranslating));
    }

    EditText edtInput;
    Button btnTranslate;
    TextView tvResult;
    ProgressBar pbTranslating;

    GsonGetter<ResponseContainer> currentGetter;

    public void btnTranslateOnClick(View view) throws UnsupportedEncodingException {
        String apiKey = "trnsl.1.1.20140927T181312Z.f77e1f526c98445d.6cf115583bde2a4771c08e22c7615b0fc84b9a42";
        String direction = "en-ru";
        String word = URLEncoder.encode(edtInput.getText().toString(), "UTF-8");

        String uri = String.format("https://translate.yandex.net/api/v1.5/tr.json/translate?key=%s&lang=%s&text=%s",
                apiKey,
                direction,
                word
        );

        currentGetter = new GsonGetter<>(ResponseContainer.class);
        currentGetter.get(uri, new GsonGetter.Callback<ResponseContainer>() {
            @Override
            public void onComplete(GsonGetter<ResponseContainer> sender, ResponseContainer result) {
                if (sender != currentGetter)
                    return;
                pbTranslating.setVisibility(View.GONE);
                if (result == null)
                    return;
                for (String line : result.text)
                    tvResult.append(line + "\n");
            }
        });

        tvResult.setText("");
        pbTranslating.setVisibility(View.VISIBLE);
    }
}
