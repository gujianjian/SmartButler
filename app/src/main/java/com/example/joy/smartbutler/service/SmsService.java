package com.example.joy.smartbutler.service;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.utils.L;
import com.example.joy.smartbutler.utils.StaticClass;
import com.example.joy.smartbutler.view.SmsLinearLayout;

import org.w3c.dom.Text;

/**
 * Created by joy on 2018/4/18.
 */

public class SmsService extends Service implements View.OnClickListener {

    private SmsReceiver smsReceive;
    private View mView;
    private String address;
    private String content;
    private WindowManager wm;
    private HomeReceiver homeReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        L.d("短信服务启动了...");



        //监听短信
        IntentFilter filter = new IntentFilter();
        filter.addAction(StaticClass.SMS_ACTION);
        filter.setPriority(Integer.MAX_VALUE);
        smsReceive = new SmsReceiver();
        registerReceiver(smsReceive, filter);

        //监听home键
        IntentFilter homeFiler = new IntentFilter();
        homeFiler.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        homeReceiver = new HomeReceiver();
        registerReceiver(homeReceiver, homeFiler);
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceive);
        unregisterReceiver(homeReceiver);
    }


    private static final String SYSTEM_DIALOG_REASON_KEY="reason";
    private static final String SYSTEM_DIALOG_HOME_KEY="homekey";
    class HomeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)){
//                L.i("test:"+intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY));
                if (SYSTEM_DIALOG_HOME_KEY.equals(intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY))) {
                    if (mView.getParent() != null) {
                        wm.removeView(mView);
                    }
                }
            }
            L.i("action:"+action);
        }
    }

    class SmsReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            L.d("我收到短信了。。。");
            Object[] objects= (Object[]) intent.getExtras().get("pdus");
            for (Object obj : objects) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);

                address = smsMessage.getOriginatingAddress();
                content = smsMessage.getDisplayMessageBody();

                L.d("address:"+ address +",content:"+ content);

                showSmsWindow();
            }
        }
    }

    //显示短信窗口
    private void showSmsWindow() {
        wm = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.MATCH_PARENT;
        lp.flags= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        lp.type=WindowManager.LayoutParams.TYPE_TOAST;//记得使用toast 不要用type_phone
        lp.format= PixelFormat.TRANSLUCENT;

        mView = View.inflate(getApplicationContext(), R.layout.layout_sms, null);

        TextView tv_address=mView.findViewById(R.id.tv_address);
        tv_address.setText("发送人:"+address);
        TextView tv_content = mView.findViewById(R.id.tv_content);
        tv_content.setText("内容:"+content);
        Button btn_sendsms = mView.findViewById(R.id.btn_sendsms);
        btn_sendsms.setOnClickListener(this);

        SmsLinearLayout sms_linearlayout = mView.findViewById(R.id.sms_linearlayout);
        sms_linearlayout.setDispatchKeyEventListener(new SmsLinearLayout.DispatchKeyEventListener() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                if (KeyEvent.KEYCODE_BACK== event.getKeyCode()) {
                    if (mView.getParent() != null) {
                        wm.removeView(mView);
                    }

                }

                return true;
            }
        });

        wm.addView(mView,lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sendsms:
                if(mView.getParent()!=null){
                    wm.removeView(mView);
                    sendSms();
                }
                break;
        }
    }

    //回复短信
    private void sendSms() {
        Uri uri = Uri.parse("smsto:" + address);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body", "");
        startActivity(intent);
    }
}
