package com.luofeng.runningman.model;

/**
 * Created by 罗峰 on 2016/8/11.
 */
public class RunRecord {
    private int id;
    private String mode;
    private String dateTime;
    private String distance;
    private String duration;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String date) {
        this.dateTime = date;
    }

    public String getDistance() {
        return distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }
    public void setDuration(String time) {
        this.duration = time;
    }
}
