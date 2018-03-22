package com.example.joy.smartbutler.utils;

import android.util.Log;

/**
 * Created by joy on 2018/3/22.
 */

public class L {
    private final static boolean DEBUG=true;
    private final static String TAG="smartbutler";

    public static void d(String text){
        if(DEBUG){
            Log.d(TAG, text);
        }
    }

    public static void i(String text){
        if(DEBUG){
            Log.i(TAG, text);
        }
    }

    public static void e(String text){
        if(DEBUG){
            Log.e(TAG, text);
        }
    }

    public static void w(String text){
        if(DEBUG){
            Log.w(TAG, text);
        }
    }
}
