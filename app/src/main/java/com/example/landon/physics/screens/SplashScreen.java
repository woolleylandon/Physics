package com.example.landon.physics;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.widget.RelativeLayout;
import android.view.animation.AlphaAnimation;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int splashInterval = 1000;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_main);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.screen);
        AlphaAnimation animation = new AlphaAnimation(0.0f , 1.0f ) ;
        animation.setFillAfter(true);
        animation.setDuration(1000);
        layout.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }, splashInterval);
    }
}