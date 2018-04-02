package com.example.joy.smartbutler.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.entity.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.example.joy.smartbutler.utils.UtilTools.toast;

/**
 * Created by joy on 2018/4/2.
 */

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_email;
    private Button btn_send_email;
    private EditText et_origin_pwd;
    private EditText et_new_pwd;
    private EditText et_confirm_pwd;
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        initView();
    }

    private void initView() {
        //邮件找回密码
        et_email = findViewById(R.id.et_email);
        btn_send_email = findViewById(R.id.btn_send_email);
        btn_send_email.setOnClickListener(this);

        et_origin_pwd = findViewById(R.id.et_origin_pwd);
        et_new_pwd = findViewById(R.id.et_new_pwd);
        et_confirm_pwd = findViewById(R.id.et_confirm_pwd);
        btn = findViewById(R.id.btn_reset_pwd);
        btn.setOnClickListener(this);

    }

    public static void createIntent(Context mContext) {
        mContext.startActivity(new Intent(mContext, ResetPasswordActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //邮件修改密码
            case R.id.btn_send_email:
                final String email = et_email.getText().toString().trim();
                if (!TextUtils.isEmpty(email)) {
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                toast("重置密码请求成功，请到" + email + "邮箱进行密码重置操作");
                            } else {
                                toast("失败:" + e.getMessage());
                            }
                            finish();
                        }
                    });
                } else {
                    toast("请输入邮箱地址");
                }
                break;
            //重置密码
            case R.id.btn_reset_pwd:
                String origin_pwd = et_origin_pwd.getText().toString();
                String new_pwd = et_new_pwd.getText().toString();
                String confirm_pwd = et_confirm_pwd.getText().toString();
                if (!TextUtils.isEmpty(origin_pwd) && !TextUtils.isEmpty(new_pwd) && !TextUtils.isEmpty(confirm_pwd)) {
                    if (new_pwd.equals(confirm_pwd)) {
////                        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
//                        BmobUser.updateCurrentUserPassword(origin_pwd, new_pwd, new UpdateListener() {
//                            @Override
//                            public void done(BmobException e) {
//                                if(e==null){
//                                    toast("密码修改成功，可以用新密码进行登录啦");
//                                }else{
//                                    toast("失败:" + e.getMessage());
//                                }
//                            }
//                        });


                        //这里要使用这个方法，要使用上面那个方法需要登陆
                        MyUser newUser = new MyUser();
                        newUser.setPassword(new_pwd);
                        MyUser myUser = MyUser.getCurrentUser(MyUser.class);
                        newUser.update(myUser.getObjectId(),new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    toast("密码修改成功");
                                }else{
                                    toast("密码修改失败:" + e.getMessage());
                                }
                                finish();
                            }
                        });
                    }else{
                        toast("两次输入的密码不一致");
                    }
                } else {
                    toast("输入框不能为空");
                }

                break;
            default:
                break;
        }
    }
}
