package com.example.joy.smartbutler.ui;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.joy.qrcode.Generatectivity;
import com.example.joy.smartbutler.R;

/**
 * Created by joy on 2018/4/23.
 */

public class MyGeneratectivity extends Generatectivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_layout);
        ImageView iv_chinese_logo=findViewById(R.id.iv_chinese_logo);
        Generatectivity.createEnglishQRCodeWithLogoAndParams(this,"gujianjian",iv_chinese_logo);

    }



}
