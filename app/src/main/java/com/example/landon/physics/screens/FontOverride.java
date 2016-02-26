package com.example.landon.physics;

import android.content.Context;
import android.widget.TextView;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class FontOverride extends TextView {
    public FontOverride(Context context) {
        super(context);
        setFont();
    }

    public FontOverride(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public FontOverride(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/avenir.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}