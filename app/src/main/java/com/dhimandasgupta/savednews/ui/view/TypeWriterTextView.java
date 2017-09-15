package com.dhimandasgupta.savednews.ui.view;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by dhimandasgupta on 15/09/17.
 */

public class TypeWriterTextView  extends AppCompatTextView {
    private CharSequence text;
    private int index;
    private long delay = 64; // in ms

    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(text.subSequence(0, index++));
            if (index <= text.length()) {
                mHandler.postDelayed(characterAdder, delay);
            }
        }
    };

    public TypeWriterTextView(Context context) {
        super(context);
    }
    public TypeWriterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mHandler.removeCallbacks(characterAdder);
    }

    public void animateText(CharSequence txt) {
        text = txt;
        index = 0;
        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, delay);
    }

    public void setCharacterDelay(long m) {
        delay = m;
    }
}
