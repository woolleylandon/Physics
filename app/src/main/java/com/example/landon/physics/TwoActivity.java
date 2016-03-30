package com.example.landon.physics;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import com.example.landon.physics.logic.MCA;
import com.example.landon.physics.logic.Measure;
import android.support.v7.app.AppCompatActivity;

public class TwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        FontHelper.applyFont(this, findViewById(R.id.activity_two), "fonts/avenir.ttf");
        Button popButton = (Button) findViewById(R.id.button);
        Button stepButton = (Button) findViewById(R.id.step);
        stepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TwoActivity.this, StepSolution.class));
            }
        });
        popButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TwoActivity.this, PopAcceleration.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void clearClick(View v) {
        ((EditText) findViewById(R.id.mca_s0_txt)).getText().clear();
        ((EditText) findViewById(R.id.mca_sf_txt)).getText().clear();
        ((EditText) findViewById(R.id.mca_v0_txt)).getText().clear();
        ((EditText) findViewById(R.id.mca_vf_txt)).getText().clear();
        ((EditText) findViewById(R.id.mca_a_txt)).getText().clear();
        ((EditText) findViewById(R.id.mca_t_txt)).getText().clear();
    }

    public void twoClick(View view) {
        EditText s0Text = (EditText) findViewById(R.id.mca_s0_txt);
        EditText sfText = (EditText) findViewById(R.id.mca_sf_txt);
        EditText v0Text = (EditText) findViewById(R.id.mca_v0_txt);
        EditText vfText = (EditText) findViewById(R.id.mca_vf_txt);
        EditText aText = (EditText) findViewById(R.id.mca_a_txt);
        EditText tText = (EditText) findViewById(R.id.mca_t_txt);

        Measure s0 = null;
        Measure sf = null;
        Measure v0 = null;
        Measure vf = null;
        Measure a = null;
        Measure t = null;

        if (!s0Text.getText().toString().equals("")) {
            s0 = new Measure(Double.parseDouble(s0Text.getText().toString()), "m");
        }

        if (!sfText.getText().toString().equals("")) {
            sf = new Measure(Double.parseDouble(sfText.getText().toString()), "m");
        }

        if (!v0Text.getText().toString().equals("")) {
            v0 = new Measure(Double.parseDouble(v0Text.getText().toString()), "m");
        }

        if (!vfText.getText().toString().equals("")) {
            vf = new Measure(Double.parseDouble(vfText.getText().toString()), "m");
        }

        if (!aText.getText().toString().equals("")) {
            a = new Measure(Double.parseDouble(aText.getText().toString()), "m");
        }

        if (!tText.getText().toString().equals("")) {
            t = new Measure(Double.parseDouble(tText.getText().toString()), "m");
        }

        MCA system = new MCA(s0, sf, v0, vf, a, t);

        system.setContext(getApplicationContext());

        try {
            if (system.solveSystem()) {
                s0Text.setText(system.getS0().getMagnitude() + "");
                sfText.setText(system.getSf().getMagnitude() + "");
                v0Text.setText(system.getV0().getMagnitude() + "");
                vfText.setText(system.getVf().getMagnitude() + "");
                aText.setText(system.getA().getMagnitude() + "");
                tText.setText(system.getT().getMagnitude() + "");

                Intent intent = new Intent(getApplicationContext(), StepSolution.class);
                intent.putExtra("Tag", system.getStepSolution());
                startActivity(intent);
            } else {
                Log.i("INFO", "Unsolvable problem");
            }
        } catch (Exception error) {
            Log.e("ERROR", "Crashed due to unsolvable problem.");
            error.printStackTrace();
        }
    }
}