package com.example.joy.smartbutler.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joy.smartbutler.R;

import org.w3c.dom.Text;

/**
 * Created by joy on 2018/4/20.
 */

public class CommonSettingView extends LinearLayout {

    private TextView tv_name;
    private TextView tv_desc;
    private String settingName;
    private String settingDesc;
    private LinearLayout ll_setting;

    public CommonSettingView(Context context) {
        super(context);
        initView(context);
    }

    public CommonSettingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommonSettingView);
        settingName = a.getString(R.styleable.CommonSettingView_setting_name);
        settingDesc = a.getString(R.styleable.CommonSettingView_setting_desc);
        a.recycle();
        initView(context);
    }

    public CommonSettingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_common_setting_view, this, true);
        tv_name = findViewById(R.id.tv_name);
        tv_desc = findViewById(R.id.tv_desc);
        ll_setting = findViewById(R.id.ll_setting);

        if (!TextUtils.isEmpty(settingName)
                && !TextUtils.isEmpty(settingDesc)) {
            tv_name.setText(settingName);
            tv_desc.setText(settingDesc);
        }else{
            tv_name.setText("未设置");
            tv_desc.setText("未设置");
        }
    }

    //设置点击事件
    public void setOnCommonSettingViewClick(OnClickListener onClickListener){
        ll_setting.setOnClickListener(onClickListener);
    }

    //设置setting标题
    public void setSettingViewName(String title){
        tv_name.setText(title);
    }

    //设置setting描述
    public void setSettingViewDesc(String desc) {
        tv_desc.setText(desc);
    }
}
