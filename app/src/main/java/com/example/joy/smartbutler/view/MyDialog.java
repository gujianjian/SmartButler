package com.example.joy.smartbutler.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.joy.smartbutler.R;

/**
 * Created by joy on 2018/3/29.
 */

public class MyDialog extends Dialog {

    public MyDialog(@NonNull Context context,int anim,int layout) {
        super(context, R.style.MyDialog);
        setContentView(layout);

        Window window = getWindow();
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.gravity= Gravity.CENTER;
//        params.width=width;
//        params.height=height;
//        window.setAttributes(params);
        window.setWindowAnimations(anim);
    }

    /**
     * 自定义对话框
     * @param context context
     * @param anim 动画资源文件
     * @param layout 布局资源文件
     * @param gravity 位置
     */
    public MyDialog(@NonNull Context context,int anim,int layout,int gravity) {
        super(context, R.style.MyDialog);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity= gravity;
        params.width= WindowManager.LayoutParams.MATCH_PARENT;

        window.setAttributes(params);
        window.setWindowAnimations(anim);

    }




//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
}
