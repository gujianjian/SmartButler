package com.example.joy.smartbutler.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
 * Created by joy on 2018/4/18.
 */

public class SmsLinearLayout extends LinearLayout {

    private DispatchKeyEventListener mDispatchKeyEventListener;

    public SmsLinearLayout(Context context) {
        super(context);
    }

    public SmsLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SmsLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDispatchKeyEventListener(DispatchKeyEventListener listener){
        this.mDispatchKeyEventListener=listener;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (mDispatchKeyEventListener != null) {
            return mDispatchKeyEventListener.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }

    public interface DispatchKeyEventListener{
        boolean dispatchKeyEvent(KeyEvent event);
    }
}