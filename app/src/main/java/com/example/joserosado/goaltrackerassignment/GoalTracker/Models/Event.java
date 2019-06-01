package com.example.joserosado.goaltrackerassignment.GoalTracker.Models;

public class Event {
    private String Id; // Do we really need to store the ID locally?
    private String StartTime;
    private long Duration;
    private String Title;
    private String Description;
    private boolean IsDone = false;
    private boolean IsAlarmOn;

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getTitle() { return Title; }

    public void switchIsDoneBoolean() { IsDone = !IsDone;}

    public boolean getIsDone() { return IsDone; }

}
