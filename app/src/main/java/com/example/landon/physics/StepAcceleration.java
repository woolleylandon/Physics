package com.example.landon.physics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class StepAcceleration extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_acceleration);
        FontHelper.applyFont(this, findViewById(R.id.step_acceleration), "fonts/avenir.ttf");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int x = dm.widthPixels;
        int y = dm.heightPixels;
        getWindow().setLayout((int) (x * 0.9), (int) (y * 0.64));
        Intent i = getIntent();
        String step = i.getStringExtra("Tag");
        TextView text = (TextView) findViewById(R.id.words);
        text.setText(step);
    }
}