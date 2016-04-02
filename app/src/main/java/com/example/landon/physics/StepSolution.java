package com.example.landon.physics;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.WindowManager;
import android.widget.TextView;
import android.util.DisplayMetrics;

public class StepSolution extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams windowManager = getWindow().getAttributes();
        windowManager.dimAmount = 0.75f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setContentView(R.layout.step_solution);
        FontHelper.applyFont(this, findViewById(R.id.step_solution), "fonts/avenir.ttf");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int x = dm.widthPixels;
        int y = dm.heightPixels;
        getWindow().setLayout((int) (x * 0.9), (int) (y * .75));
        Intent i = getIntent();
        String step = i.getStringExtra("Tag");
        TextView text = (TextView) findViewById(R.id.words);
        text.setText(step);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}