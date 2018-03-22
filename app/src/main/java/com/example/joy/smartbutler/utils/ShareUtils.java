package com.example.joy.smartbutler.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by joy on 2018/3/22.
 */

public class ShareUtils {

    public static final String NAME = "config";

    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, String defVal) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(key, defVal);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).apply();
    }

    public static int getInt(Context context, String key, int defVal) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getInt(key, defVal);
    }

    public static void putBoolean(Context context, String key, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defVal) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getBoolean(key, defVal);
    }

    public static void shareDel(Context context,String key){
        context.getSharedPreferences(NAME,Context.MODE_PRIVATE).edit().remove(key).apply();
    }

    public static void shareDelAll(Context context){
        context.getSharedPreferences(NAME,Context.MODE_PRIVATE).edit().clear().apply();
    }

}
