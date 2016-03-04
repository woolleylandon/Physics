package com.example.landon.physics;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;

public class PopCollisions extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_collisions);
        FontHelper.applyFont(this, findViewById(R.id.pop_collisions), "fonts/avenir.ttf");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int x = dm.widthPixels;
        int y = dm.heightPixels;
        getWindow().setLayout((int) (x * 0.9), (int) (y * 0.64));
    }
}