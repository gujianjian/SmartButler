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

    public MyDialog(@NonNull Context context,int width,int height) {
        super(context, R.style.MyDialog);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity= Gravity.CENTER;
        params.width=width;
        params.height=height;
        window.setAttributes(params);
        window.setWindowAnimations(R.style.dialogAnim);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_dialog);
    }
}
