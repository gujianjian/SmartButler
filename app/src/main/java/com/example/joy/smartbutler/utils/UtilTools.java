package com.example.joy.smartbutler.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by joy on 2018/3/24.
 */

public class UtilTools {

    /**
     * 设置字体
     * @param mContext
     * @param view
     */
    public static void setTypeFace(Context mContext, TextView view){
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/FONT.TTF");
        view.setTypeface(typeface);
    }
}
