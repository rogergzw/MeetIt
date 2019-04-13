package com.example.meetit;

import java.util.Date;

public class MeetingRequest {

    private String title;
    private String to;
    private String from;
    private String dateTime;
    private String location;
    private int accepted;
    private String key;
    private String with;

    //Class which is used to store and retrieve meeting requests from and to the database

    public MeetingRequest() {
    }

    public MeetingRequest(String title, String to, String from, String dateTime, String location, int accepted, String key, String with) {
        this.title = title;
        this.to = to;
        this.from = from;
        this.dateTime = dateTime;
        this.location = location;
        this.accepted = accepted;
        this.key = key;
        this.with = with;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setWith(String with) {
        this.with = with;
    }

    public String getWith() {
        return with;
    }
}
