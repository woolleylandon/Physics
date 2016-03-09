package com.example.landon.physics;

import android.os.Bundle;
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
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
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

    public void twoClick(View v) {
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
            System.out.println("S0 is NOT empty: " + s0Text.getText().toString());
        } else {
            System.out.println("S0 IS empty: " + s0Text.getText().toString());
        }

        if (!sfText.getText().toString().equals("")) {
            sf = new Measure(Double.parseDouble(sfText.getText().toString()), "m");
            System.out.println("Sf is NOT empty: " + sfText.getText().toString());
        } else {
            System.out.println("Sf IS empty: " + sfText.getText().toString());
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

        if (system.solveSystem()) {
            s0Text.setText(system.getS0().getMagnitude() + "");
            sfText.setText(system.getSf().getMagnitude() + "");
            v0Text.setText(system.getV0().getMagnitude() + "");
            vfText.setText(system.getVf().getMagnitude() + "");
            aText.setText(system.getA().getMagnitude() + "");
            tText.setText(system.getT().getMagnitude() + "");
        } else {
            // say in step by step that system cannot be solved
        }
    }
}