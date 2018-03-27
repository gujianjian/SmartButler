package com.example.joy.smartbutler.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joy.smartbutler.application.BaseApplication;

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

    /**
     * toast工具类
     */
    public static void toast(String text){
        Toast.makeText(BaseApplication.getMyContext(),text,Toast.LENGTH_SHORT).show();
    }
}
