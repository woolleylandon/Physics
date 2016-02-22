package com.example.landon.physics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.intro_animation, R.anim.outro_animation);
    }
}
