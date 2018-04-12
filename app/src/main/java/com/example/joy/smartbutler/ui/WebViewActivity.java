package com.example.joy.smartbutler.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.joy.smartbutler.R;

/**
 * Created by joy on 2018/4/11.
 */

public class WebViewActivity extends BaseActivity {

    private String title;
    private String url;
    private WebView my_webview;
    private ProgressBar pb_progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        title = bundle.getString("title");
        url = bundle.getString("url");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }

        initView();
    }

    private void initView() {
        my_webview = findViewById(R.id.my_webview);
        pb_progress = findViewById(R.id.pb_progress);

        //设置支持js
        my_webview.getSettings().setJavaScriptEnabled(true);
        //设置缩放
        my_webview.getSettings().setSupportZoom(true);
        my_webview.getSettings().setBuiltInZoomControls(true);
        //设置本地显示
        my_webview.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
        my_webview.setWebChromeClient(new WebViewClient());
        my_webview.loadUrl(url);
    }

    public static void createIntent(Context mContext,Bundle bundle){
        Intent intent=new Intent(mContext,WebViewActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    public class WebViewClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if(newProgress==100){
                pb_progress.setVisibility(View.GONE);
            }
        }
    }
}
