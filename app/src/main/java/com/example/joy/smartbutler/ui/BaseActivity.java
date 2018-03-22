package com.example.joy.smartbutler.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Created by joy on 2018/3/22.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 5.0以上去除阴影方法
         */
        if(Build.VERSION.SDK_INT>=21){
            getSupportActionBar().setElevation(0);
        }
    }
}
