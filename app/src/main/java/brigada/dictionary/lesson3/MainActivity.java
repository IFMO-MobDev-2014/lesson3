package brigada.dictionary.lesson3;

/**
 * Created by Dmitry on 29.09.2014.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button button = (Button) findViewById(R.id.translate);
        final EditText editText = (EditText) findViewById(R.id.field);
        editText.setTextSize(40);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this,Worker.class);
                intent.putExtra("word",word);
                startActivity(intent);
            }
        });
    }
}
