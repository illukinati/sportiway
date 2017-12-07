package com.example.asus.sportiway.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by alenovan on 6/2/17.
 */

public class button_text extends android.support.v7.widget.AppCompatButton {

    public button_text(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public button_text(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public button_text(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
            setTypeface(tf);
        }
    }

}

