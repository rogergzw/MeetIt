package com.example.meetit;

import java.util.Date;

public class MeetingRequest {

    private String title;
    private String to;
    private String from;
    private String dateTime;
    private String location;
    private Boolean accepted;
    private String key;

    public MeetingRequest(String title, String to, String from, String dateTime, String location, Boolean accepted, String key) {
        this.title = title;
        this.to = to;
        this.from = from;
        this.dateTime = dateTime;
        this.location = location;
        this.accepted = accepted;
        this.key = key;
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

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
