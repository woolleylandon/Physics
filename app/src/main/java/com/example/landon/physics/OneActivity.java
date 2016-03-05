package com.example.landon.physics;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.landon.physics.logic.MCV;
import com.example.landon.physics.logic.Measure;

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

    public void oneClick(View v) {
        /*EditText x0Text = (EditText) findViewById(R.id.x0Text);
        EditText xfText = (EditText) findViewById(R.id.xfText);
        EditText vText  = (EditText) findViewById(R.id.vText);
        EditText tText  = (EditText) findViewById(R.id.tText);

        double x0TextInt = Double.parseDouble(x0Text.getText().toString());
        double xfTextInt = Double.parseDouble(xfText.getText().toString());
        double vTextInt  = Double.parseDouble(vText.getText().toString());
        double tTextInt  = Double.parseDouble(tText.getText().toString());

        MCV sys = new MCV(new Measure(x0TextInt, "m"),
                new Measure(xfTextInt, "m"),
                new Measure(vTextInt, "m"),
                new Measure(tTextInt, "m"));*/
    }
}