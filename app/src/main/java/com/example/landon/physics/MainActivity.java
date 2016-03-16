package com.example.landon.physics;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * The Solve Physics program solves various physics problems
 * and gives the user a step by step solutions guide on how
 * said problem is to be accomplished.
 *
 * @author  Landon Woolley
 * @author  Marco Cardoso
 * @author  Winson So
 * @version 1.2
 */

public class MainActivity extends AppCompatActivity {

    /**
     * The onCreate function is the first code that is run
     * It sets the layout to the main activity and adds
     * the custom font that we want for the app.
     *
     * @param savedInstanceState This is created by default
     *                           in Android Studios
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FontHelper.applyFont(this, findViewById(R.id.activity_main), "fonts/avenir.ttf");
    }

    /**
     * oneClick is activated when the first problem is tapped.
     * It opens a new activity with a sliding animation
     *
     * @param v The View v is used to adjust the view
     */
    public void oneClick(View v) {
        startActivity(new Intent(MainActivity.this, OneActivity.class));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    /**
     * twoClick is activated when the second problem is tapped.
     * It opens a new activity with a sliding animation
     *
     * @param v The View v is used to adjust the view
     */
    public void twoClick(View v) {
        startActivity(new Intent(MainActivity.this, TwoActivity.class));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    /**
     * threeClick is activated when the third problem is tapped.
     * It opens a new activity with a sliding animation
     *
     * @param v The View v is used to adjust the view
     */
    public void threeClick(View v) {
        startActivity(new Intent(MainActivity.this, ThreeActivity.class));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}