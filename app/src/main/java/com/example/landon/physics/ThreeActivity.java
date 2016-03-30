package com.example.landon.physics;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.landon.physics.logic.Collision;
import com.example.landon.physics.logic.Measure;

public class ThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        FontHelper.applyFont(this, findViewById(R.id.activity_three), "fonts/avenir.ttf");
        Button popButton = (Button) findViewById(R.id.button);
        Button stepButton = (Button) findViewById(R.id.step);
        stepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThreeActivity.this, StepSolution.class));
            }
        });
        popButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThreeActivity.this, PopCollisions.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void clearClick(View v) {
        ((EditText) findViewById(R.id.mAText)).getText().clear();
        ((EditText) findViewById(R.id.mBText)).getText().clear();
        ((EditText) findViewById(R.id.vA1Text)).getText().clear();
        ((EditText) findViewById(R.id.vB1Text)).getText().clear();
        ((EditText) findViewById(R.id.v2Text)).getText().clear();
    }

    public void threeClick(View view) {
        EditText mAText = (EditText) findViewById(R.id.mAText);
        EditText mBText = (EditText) findViewById(R.id.mBText);
        EditText vA1Text = (EditText) findViewById(R.id.vA1Text);
        EditText vB1Text = (EditText) findViewById(R.id.vB1Text);
        EditText v2Text = (EditText) findViewById(R.id.v2Text);

        Measure massA = null;
        Measure massB = null;
        Measure va = null;
        Measure vb = null;
        Measure vf = null;

        if (!mAText.getText().toString().equals("")) {
            massA = new Measure(Double.parseDouble(mAText.getText().toString()), "m");
        }

        if (!mBText.getText().toString().equals("")) {
            massB = new Measure(Double.parseDouble(mBText.getText().toString()), "m");
        }

        if (!vA1Text.getText().toString().equals("")) {
            va = new Measure(Double.parseDouble(vA1Text.getText().toString()), "m/s");
        }

        if (!vB1Text.getText().toString().equals("")) {
            vb = new Measure(Double.parseDouble(vB1Text.getText().toString()), "s");
        }

        if (!v2Text.getText().toString().equals("")) {
            vf = new Measure(Double.parseDouble(v2Text.getText().toString()), "s");
        }

        Collision system = new Collision(massA, massB, va, vb, vf);

        try {
            if (system.solveSystem()) {
                mAText.setText(system.getMassA().getMagnitude() + "");
                mBText.setText(system.getMassB().getMagnitude() + "");
                vA1Text.setText(system.getVa().getMagnitude() + "");
                vB1Text.setText(system.getVb().getMagnitude() + "");
                v2Text.setText(system.getVf().getMagnitude() + "");
            } else {
                Log.i("INFO", "Unsolvable problem");
            }
        } catch (Exception error) {
            Log.e("ERROR", "Crashed due to unsolvable problem.");
        }
    }
}