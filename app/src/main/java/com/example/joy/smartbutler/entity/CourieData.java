package com.example.joy.smartbutler.entity;

/**
 * Created by joy on 2018/4/10.
 */

public class CourieData {

    //物流发生的时间
    private String datetime;
    //物流事件的描述
    private String remark;
    //区域
    private String zone;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
