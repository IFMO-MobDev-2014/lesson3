package com.example.vi34.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ProblemWithConnectionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problemscreen);
    }

    public void onBackPressed() {
        Intent intent = new Intent(ProblemWithConnectionActivity.this, MyActivity.class);
        startActivity(intent);
        finish();
    }

}
