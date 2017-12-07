package com.example.asus.sportiway.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by alenovan on 6/2/17.
 */

public class edit_login extends android.support.v7.widget.AppCompatEditText {

    public edit_login(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public edit_login(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public edit_login(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
            setTypeface(tf);
        }
    }

}
