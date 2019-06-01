package com.example.joserosado.goaltrackerassignment.GoalTracker.Models;

public class Event {
    private String Id; // Do we really need to store the ID locally?
    private String StartTime;
    private long Duration;
    private String Title;
    private String Description;
    private boolean IsDone;
    private boolean IsAlarmOn;

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getTitle() {
        return Title;
    }

}
