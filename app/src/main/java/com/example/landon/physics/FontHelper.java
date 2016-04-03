package com.example.landon.physics;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Typeface;

public class FontHelper {
    private static final String TAG = FontHelper.class.getSimpleName();

    public static void applyFont(final Context context, final View root, final String fontPath) {
        try {
            if (root instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) root;
                //59 6f 75 20 61 72 65 20 6e 6f 77 20 62 61 6c 6c 73 20 64 65 65 70 20 69 6e
                // 20 74 68 65 20 63 6f 64 65 2c 20 61 6e 64 2c 20 66 6f 72 20 73 6f 6d 65 20
                // 72 65 61 73 6f 6e 20 75 6e 65 78 70 6c 61 69 6e 65 64 20 62 79 20 73 63 69
                // 65 6e 63 65 2c 20 79 6f 75 20 68 61 76 65 20 67 6f 6e 65 20 73 6f 20 66 61
                // 72 20 61 73 20 74 6f 20 63 6f 70 79 20 74 68 69 73 20 74 65 78 74 20 69 6e
                // 74 6f 20 61 20 74 72 61 6e 73 6c 61 74 6f 72 2e 20 49 20 64 6f 6e e2 80 99
                // 74 20 6b 6e 6f 77 20 77 68 61 74 20 79 6f 75 e2 80 99 72 65 20 6c 6f 6f 6b
                // 69 6e 67 20 66 6f 72 20 62 75 74 20 49 20 68 6f 70 65 20 79 6f 75 20 66 69
                // 6e 64 20 69 74 20 3a 44
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++)
                    applyFont(context, viewGroup.getChildAt(i), fontPath);
            } else if (root instanceof TextView)
                ((TextView) root).setTypeface(Typeface.createFromAsset(context.getAssets(), fontPath));
        } catch (Exception e) {
            Log.e(TAG, String.format("Error occured when trying to apply %s font for %s view", fontPath, root));
            e.printStackTrace();
        }
    }
}
