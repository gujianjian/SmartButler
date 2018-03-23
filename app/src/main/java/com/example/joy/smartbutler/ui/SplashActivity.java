package com.example.joy.smartbutler.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.joy.smartbutler.MainActivity;
import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.utils.ShareUtils;
import com.example.joy.smartbutler.utils.StaticClass;
import com.example.joy.smartbutler.utils.UtilTools;

/**
 * Created by joy on 2018/3/24.
 */

public class SplashActivity extends AppCompatActivity {

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case StaticClass.HANDLE_SPLASH:
                    if(isfirst()){
                        Intent guide = new Intent(SplashActivity.this, GuideActivity.class);
                        startActivity(guide);
                        finish();
                    }else{
                        Intent main = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(main);
                        finish();
                    }
                    break;
            }
            return true;
        }
    });

    private boolean isfirst() {
        boolean is_first=ShareUtils.getBoolean(this,StaticClass.IS_FIRST,true);
        if(is_first){
            ShareUtils.putBoolean(this,StaticClass.IS_FIRST,false);
            return true;
        }else{

            return false;
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        initData();
    }

    private void initData() {
        handler.sendEmptyMessageDelayed(StaticClass.HANDLE_SPLASH, 1000);
    }

    private void initView() {
        TextView tv_splash = findViewById(R.id.tv_splash);
        UtilTools.setTypeFace(this,tv_splash);
    }
}
