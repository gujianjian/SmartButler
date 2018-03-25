package com.example.joy.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.entity.MyUser;
import com.example.joy.smartbutler.utils.L;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by joy on 2018/3/25.
 */

public  class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_username;
    private EditText et_password;
    private EditText et_confirmPassword;
    private RadioGroup rg_gender;
    private EditText et_email;
    private Button btn_register;
    //true为男,false为女
    private Boolean isGender=true;
    private EditText et_age;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_confirmPassword = findViewById(R.id.et_confirmPassword);
        et_age = findViewById(R.id.et_age);
        rg_gender = findViewById(R.id.rg_gender);
        et_email = findViewById(R.id.et_email);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(this);
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.rb_male){
                    L.d("aaa");
                    isGender=true;
                }else{
                    L.d("bbb");
                    isGender=false;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                String username=et_username.getText().toString().trim();
                String password=et_password.getText().toString().trim();
                String age=et_age.getText().toString().trim();
                String confirmpassword=et_confirmPassword.getText().toString().trim();
                String email=et_email.getText().toString().trim();

                if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(confirmpassword)
                        &&!TextUtils.isEmpty(email)){
                    if (password.equals(confirmpassword)) {
                        MyUser myUser=new MyUser();
                        myUser.setUsername(username);
                        myUser.setPassword(password);
                        myUser.setGender(isGender);
                        myUser.setAge(Integer.parseInt(age));
                        myUser.setEmail(email);
                        myUser.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e==null){
                                    Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    L.d(e.getMessage());
                                }
                            }
                        });
                    }else{
                        Toast.makeText(this,"两次密码输入不一致！",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"输入框不能为空！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
