package com.example.joy.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.joy.smartbutler.MainActivity;
import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.entity.MyUser;
import com.example.joy.smartbutler.utils.L;
import com.example.joy.smartbutler.utils.ShareUtils;
import com.example.joy.smartbutler.utils.UtilTools;
import com.example.joy.smartbutler.view.MyDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by joy on 2018/3/25.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_register;
    private Button btn_login;
    private CheckBox cb_remember_password;
    private EditText et_username;
    private EditText et_password;
    private String username;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        //注册
        btn_register = findViewById(R.id.btn_register);
        //登陆
        btn_login = findViewById(R.id.btn_login);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        cb_remember_password = findViewById(R.id.cb_remember_password);

        boolean isRemember=ShareUtils.getBoolean(this, "remember_password", false);
        cb_remember_password.setChecked(isRemember);
        if(isRemember){
            et_username.setText(ShareUtils.getString(this, "username", ""));
            et_password.setText(ShareUtils.getString(this,"password",""));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                Intent register = new Intent(this, RegisterActivity.class);
                startActivity(register);
                break;
            case R.id.btn_login:
                final MyDialog dialog=new MyDialog(this,200,200);
                dialog.show();

                username = et_username.getText().toString().trim();
                password = et_password.getText().toString();
                MyUser myUser = new MyUser();
                myUser.setUsername(username);
                myUser.setPassword(password);
                myUser.login(new SaveListener<MyUser>() {
                    @Override
                    public void done(MyUser user, BmobException e) {
                        if (e == null) {
                            dialog.dismiss();
                            UtilTools.toast("登陆成功");
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            UtilTools.toast("登陆失败：" + e.getErrorCode());
                            L.d("LoginError:" + e.getMessage());
                        }
                    }
                });

                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareUtils.shareDel(this,"remember_password");
        ShareUtils.shareDel(this, "username");
        ShareUtils.shareDel(this,"password");
        if (cb_remember_password.isChecked()) {
            ShareUtils.putBoolean(this, "remember_password", true);
            ShareUtils.putString(this, "username", username);
            ShareUtils.putString(this,"password",password);
        }
    }
}
