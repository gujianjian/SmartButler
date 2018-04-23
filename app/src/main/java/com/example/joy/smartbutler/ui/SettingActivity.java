package com.example.joy.smartbutler.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.joy.qrcode.CaptureActivity;
import com.example.joy.qrcode.Generatectivity;
import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.service.SmsService;
import com.example.joy.smartbutler.utils.L;
import com.example.joy.smartbutler.utils.ShareUtils;
import com.example.joy.smartbutler.utils.StaticClass;
import com.example.joy.smartbutler.view.CommonSettingView;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2018/3/24.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Switch switch_sms;
    private List<String> listPermission = new ArrayList<>();
    private int versionCode;
    private String versionName;
    private TextView tv_version_name;
    private LinearLayout ll_check_version;
    private String downloadurl;
    private CommonSettingView setting_scan;
    private CommonSettingView generate_qrcode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settting);
        initview();
    }

    private void initview() {
        //短信开启
        switch_sms = findViewById(R.id.switch_sms);
        switch_sms.setOnClickListener(this);

        boolean ischecked = ShareUtils.getBoolean(this, "sms_checked", false);
        switch_sms.setChecked(ischecked);

        //版本检测
        tv_version_name = findViewById(R.id.tv_version_name);
        checkVersionCode(tv_version_name);
        ll_check_version = findViewById(R.id.ll_check_version);
        ll_check_version.setOnClickListener(this);

        //扫一扫
        setting_scan = findViewById(R.id.setting_scan);
        setting_scan.setOnCommonSettingViewClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this,"扫一扫",Toast.LENGTH_SHORT).show();
                Intent openCameraIntent = new Intent(SettingActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
            }
        });

        //生成二维码
        generate_qrcode=findViewById(R.id.generate_qrcode);
        generate_qrcode.setOnCommonSettingViewClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingActivity.this, MyGeneratectivity.class);
                startActivity(i);
            }
        });
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //开启短信监听
            case R.id.switch_sms:
                ShareUtils.putBoolean(SettingActivity.this, "sms_checked", switch_sms.isChecked());
                if (switch_sms.isChecked()) {
                    //申请短信接收权限
                    requestSmsPermission();
                    startSmsService();
                }
                break;
            //检测版本
            case R.id.ll_check_version:
                RxVolley.get(StaticClass.DOWNLOAD_URL, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                       L.d("download--->"+t);
                       parseJson(t);
                    }
                });


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


    //检测显示版本号
    private void checkVersionCode(TextView tv_version_name) {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
            versionName = packageInfo.versionName;
            tv_version_name.setText("检测版本：" + versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    //显示版本更新窗口
    private void showUpdateDialog(String content) {
        new AlertDialog.Builder(this)
                .setTitle("版本提示")
                .setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(SettingActivity.this, UpdateActivity.class);
                        i.putExtra("url",downloadurl);
                        startActivity(i);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }


    //解析下载内容
    private void parseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            int code = jsonObject.getInt("versionCode");
            downloadurl = jsonObject.getString("downloadurl");
            String content = jsonObject.getString("content");
            if(code>versionCode){
                showUpdateDialog(content);
            }else{
                Toast.makeText(SettingActivity.this,"已是最新版本！",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
