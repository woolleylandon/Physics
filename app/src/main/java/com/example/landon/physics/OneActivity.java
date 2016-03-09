package com.example.landon.physics;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import com.example.landon.physics.logic.MCV;
import com.example.landon.physics.logic.Measure;
import android.support.v7.app.AppCompatActivity;

public class OneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        FontHelper.applyFont(this, findViewById(R.id.activity_one), "fonts/avenir.ttf");
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OneActivity.this, PopVelocity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void oneClick(View view) {
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
            System.out.println("S0 is NOT empty: " + x0Text.getText().toString());
        } else {
            System.out.println("S0 IS empty: " + x0Text.getText().toString());
        }

        if (!xfText.getText().toString().equals("")) {
            xf = new Measure(Double.parseDouble(xfText.getText().toString()), "m");
            System.out.println("S0 is NOT empty: " + xfText.getText().toString());
        } else {
            System.out.println("S0 IS empty: " + xfText.getText().toString());
        }

        if (!vText.getText().toString().equals("")) {
            v = new Measure(Double.parseDouble(vText.getText().toString()), "m");
            System.out.println("S0 is NOT empty: " + vText.getText().toString());
        } else {
            System.out.println("S0 IS empty: " + vText.getText().toString());
        }

        if (!tText.getText().toString().equals("")) {
            t = new Measure(Double.parseDouble(tText.getText().toString()), "m");
            System.out.println("S0 is NOT empty: " + tText.getText().toString());
        } else {
            System.out.println("S0 IS empty: " + tText.getText().toString());
        }

        MCV system = new MCV(x0, xf, v, t);

        if (system.solveSystem()) {
            x0Text.setText(system.getX0().getMagnitude() + "");
            xfText.setText(system.getXf().getMagnitude() + "");
            vText.setText(system.getV().getMagnitude() + "");
            tText.setText(system.getT().getMagnitude() + "");
        } else {
            // say in step by step that system cannot be solved
        }
    }
}