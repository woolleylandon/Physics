package com.example.landon.physics;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import com.example.landon.physics.logic.MCV;
import com.example.landon.physics.logic.Measure;
import com.example.landon.physics.logic.PhysicsSystem;

import android.support.v7.app.AppCompatActivity;

public class OneActivity extends AppCompatActivity {

    MCV system;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        FontHelper.applyFont(this, findViewById(R.id.activity_one), "fonts/avenir.ttf");
        Button popButton = (Button) findViewById(R.id.button);
        popButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OneActivity.this, PopVelocity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void clearClick(View view) {
        ((EditText) findViewById(R.id.x0Text)).getText().clear();
        ((EditText) findViewById(R.id.xfText)).getText().clear();
        ((EditText) findViewById(R.id.vText)).getText().clear();
        ((EditText) findViewById(R.id.tText)).getText().clear();
        system = null;
    }

    public void oneClick(View view) {
        try {

            if (system != null && system.solved == true) {
                Log.i("INFO", "Already Solved");
            } else {

                EditText x0Text = (EditText) findViewById(R.id.x0Text);
                EditText xfText = (EditText) findViewById(R.id.xfText);
                EditText vText = (EditText) findViewById(R.id.vText);
                EditText tText = (EditText) findViewById(R.id.tText);

                Measure x0 = null;
                Measure xf = null;
                Measure v = null;
                Measure t = null;

                if (!x0Text.getText().toString().equals("")) {
                    x0 = new Measure(Double.parseDouble(x0Text.getText().toString()), "m");
                }

                if (!xfText.getText().toString().equals("")) {
                    xf = new Measure(Double.parseDouble(xfText.getText().toString()), "m");
                }

                if (!vText.getText().toString().equals("")) {
                    v = new Measure(Double.parseDouble(vText.getText().toString()), "m/s");
                }

                if (!tText.getText().toString().equals("")) {
                    t = new Measure(Double.parseDouble(tText.getText().toString()), "s");
                }

                system = new MCV(x0, xf, v, t);

                system.setContext(getApplicationContext());

                try {
                    if (system.solveSystem()) {
                        x0Text.setText(system.getX0().getMagnitude() + "");
                        xfText.setText(system.getXf().getMagnitude() + "");
                        vText.setText(system.getV().getMagnitude() + "");
                        tText.setText(system.getT().getMagnitude() + "");
                    } else {
                        Log.i("INFO", "Unsolvable problem");
                    }
                } catch (Exception error) {
                    Log.e("ERROR", "Crashed due to unsolvable problem.");
                    error.printStackTrace();
                }
            }

        } catch (Exception entry) {
            Log.e("ERROR", "Crashed due to out of place comma.");
            entry.printStackTrace();
        }

    }

    public void stepOneClick(View view) {
        try {
            Intent intent = new Intent(getApplicationContext(), StepSolution.class);
            intent.putExtra("Tag", system.getStepSolution());
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } catch (Exception stepError) {
            Log.e("ERROR", "Crashed due to no inputs to solve.");
            stepError.printStackTrace();
        }
    }
}