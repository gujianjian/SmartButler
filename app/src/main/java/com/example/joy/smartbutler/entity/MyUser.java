package com.example.joy.smartbutler.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by joy on 2018/3/25.
 */

public class MyUser extends BmobUser {

    private int age;
    private boolean isGender;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String desc;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getGender() {
        return isGender;
    }

    public void setGender(boolean gender) {
        isGender = gender;
    }


}
