package com.example.meetit;

import java.util.Date;

public class MeetingRequest {

    private String title;
    private String dateTime;
    private String location;
    private String notes;
    private Boolean accepted;

    public MeetingRequest(String title, String dateTime, String location, String notes, Boolean accepted) {
        this.title = title;
        this.dateTime = dateTime;
        this.location = location;
        this.notes = notes;
        this.accepted = accepted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
