package com.example.tancen.pullrefreshrecyclerviewdemo;

import java.util.Date;

/**
 * Created by classTC on 11/30/2015.模拟数据bean
 */
public class DataBean {
    private int id;
    private String name;
    private Date createdTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
