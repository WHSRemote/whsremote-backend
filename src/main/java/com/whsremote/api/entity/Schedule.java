package com.whsremote.api.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Schedule {

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
        return "Schedule{" +
                "userId='" + userId + '\'' +
                ", json='" + json + '\'' +
                '}';
    }
}
