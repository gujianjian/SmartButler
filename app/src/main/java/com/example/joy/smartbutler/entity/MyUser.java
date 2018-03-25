package com.example.joy.smartbutler.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by joy on 2018/3/25.
 */

public class MyUser extends BmobObject {
    private String username;
    private String password;
    private int age;
    private boolean isGender;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGender() {
        return isGender;
    }

    public void setGender(boolean gender) {
        isGender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
