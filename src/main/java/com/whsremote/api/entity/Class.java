package com.whsremote.api.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema="classes")
public class Class {

    @Id
    private String userId;
    private String json;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public String toString() {
        return "Class{" +
                "userId='" + userId + '\'' +
                ", json='" + json + '\'' +
                '}';
    }
}
