package homework3.translater;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class Main extends Activity {
    Translator t;
    //rewrite
    public static TextView tv;
    public static CharSequence x;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //rewrite
        tv = (TextView) findViewById(R.id.textView3);
        t = new Translator();
        String text = "word";
        t.execute(text);
        //
    }
}
