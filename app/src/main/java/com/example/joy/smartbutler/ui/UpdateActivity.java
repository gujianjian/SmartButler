package com.example.joy.smartbutler.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.utils.L;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.toolbox.FileUtils;

import java.io.File;

/**
 * Created by joy on 2018/4/19.
 */

public class UpdateActivity extends BaseActivity {

    //正在下载
    private static final int HANDLE_DOWNLOADING = 100;
    //下载完成
    private static final int HANDLE_DWONLOAD_FINISH = 101;
    private TextView tv_progress;
    private String filepath;
    private NumberProgressBar number_progress_bar;

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLE_DOWNLOADING:
                    Bundle data = msg.getData();
                    int progress = data.getInt("progress");
                    tv_progress.setText(progress + "");
                    number_progress_bar.setProgress(progress);
                    break;
                case HANDLE_DWONLOAD_FINISH:
                    String s = (String) msg.obj;
                    tv_progress.setText(s);

                    installApk();
                    break;
            }
            return true;
        }
    });



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        init();
    }

    private void init() {
        tv_progress = findViewById(R.id.tv_progress);
        number_progress_bar = findViewById(R.id.number_progress_bar);
        number_progress_bar.setMax(100);

        ProgressListener listener = new ProgressListener() {
            @Override
            public void onProgress(long transferredBytes, long totalSize) {
                L.d("transferredBytes-->" + transferredBytes + ",totalSize--->" + totalSize);
                int progress = (int) (transferredBytes * 100 / totalSize);
                Message msg = mHandler.obtainMessage();
                msg.what = HANDLE_DOWNLOADING;
                Bundle bundle = new Bundle();
                bundle.putInt("progress", progress);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
        };

//下载回调，内置了很多方法，详细请查看源码
//包括在异步响应的onSuccessInAsync():注不能做UI操作
//下载成功时的回调onSuccess()
//下载失败时的回调onFailure():例如无网络，服务器异常等
        HttpCallback callback = new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                Message msg = mHandler.obtainMessage();
                msg.what = HANDLE_DWONLOAD_FINISH;
                msg.obj = "下载成功";
                mHandler.sendMessage(msg);
            }
        };

        filepath = FileUtils.getSDCardPath() + "/" + System.currentTimeMillis() + ".apk";
        String url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            RxVolley.download(filepath,
                    url,
                    listener, callback);
        }
    }

    //安装APK
    private void installApk() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.fromFile(new File(filepath)), "application/vnd.android.package-archive");
        startActivity(i);
        finish();
    }

}
