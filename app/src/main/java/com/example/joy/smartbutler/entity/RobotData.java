package com.example.joy.smartbutler.entity;

/**
 * Created by joy on 2018/4/16.
 */

public class RobotData {
    public static final int VALUE_TEXT_LEFT=1;
    public static final int VALUE_TEXT_RIGHT=2;
    private int type;
    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
