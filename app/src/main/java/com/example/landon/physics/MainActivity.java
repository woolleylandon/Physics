package com.example.landon.physics;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void oneClick(View v) {
        startActivity(new Intent(MainActivity.this, OneActivity.class));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void twoClick(View v) {
        startActivity(new Intent(MainActivity.this, TwoActivity.class));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void threeClick(View v) {
        startActivity(new Intent(MainActivity.this, ThreeActivity.class));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}