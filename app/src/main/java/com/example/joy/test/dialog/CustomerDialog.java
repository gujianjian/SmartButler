package com.example.joy.test.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.joy.smartbutler.R;

/**
 * Created by joy on 2018/4/1.
 */

public class CustomerDialog extends Dialog {
    public CustomerDialog(@NonNull Context context) {
        super(context,R.style.CustomerDialogStyle);
        setContentView(R.layout.my_dialog);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity= Gravity.CENTER;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(R.style.dialogAnim);
    }

}
