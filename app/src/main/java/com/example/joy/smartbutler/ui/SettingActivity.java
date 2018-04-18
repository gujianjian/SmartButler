package com.example.joy.smartbutler.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.joy.smartbutler.MainActivity;
import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.service.SmsService;
import com.example.joy.smartbutler.utils.L;
import com.example.joy.smartbutler.utils.ShareUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by joy on 2018/3/24.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Switch switch_sms;
    private List<String> listPermission = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settting);
        initview();
    }

    private void initview() {
        switch_sms = findViewById(R.id.switch_sms);
        switch_sms.setOnClickListener(this);

        boolean ischecked = ShareUtils.getBoolean(this, "sms_checked", false);
        switch_sms.setChecked(ischecked);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_sms:
                ShareUtils.putBoolean(SettingActivity.this, "sms_checked", switch_sms.isChecked());
                if (switch_sms.isChecked()) {
                    //申请短信接收权限
                    requestSmsPermission();
                    startSmsService();
                }
                break;
        }
    }

    private void startSmsService() {
        Intent intent = new Intent(SettingActivity.this, SmsService.class);
        startService(intent);
    }

    /**
     * 申请多个权限sms_receive
     */
    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_SMS},1);
            listPermission.add(Manifest.permission.RECEIVE_SMS);
        }

        //这个仅仅作为多项权限申请测试使用
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            listPermission.add(Manifest.permission.CALL_PHONE);
        }
        String[] permissions = listPermission.toArray(new String[listPermission.size()]);
        if (listPermission.size() > 0) {
            ActivityCompat.requestPermissions(this, permissions, 1);
            listPermission.clear();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        L.d("permissions-->" + permissions.length + "  grantResults--->" + grantResults.length);
        boolean flag = true;
        switch (requestCode) {
            case 1:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                        break;
                    }
                }
                if (!flag) {
                    Toast.makeText(this, "拒绝权限不能启动", Toast.LENGTH_SHORT).show();
                }

//                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    startSmsService();
//                }else{
//                    Toast.makeText(this, "拒绝权限不能启动", Toast.LENGTH_SHORT).show();
//                }

                break;
        }
    }
}
