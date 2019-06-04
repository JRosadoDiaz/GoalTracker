package com.example.joserosado.goaltrackerassignment.GoalTracker.Models;

import java.util.Map;

public class Event {
    private String Id; // Do we really need to store the ID locally?
    private String StartTime;
    private long Duration;
    private String Title;
    private String Description;
    private boolean IsDone = false;
    private boolean IsAlarmOn;

    public void setId(String id){this.Id = id;}
    public void setTitle(String title) {
        this.Title = title;
    }
    public void setIsDone(boolean isDone){this.IsDone = isDone;}
    public void setDescription(String description)
    {this.Description = description == null ? "" : description;}

    public String getTitle() { return Title; }

    public void switchIsDoneBoolean() { IsDone = !IsDone;}

    public boolean getIsDone() { return IsDone; }

    public String getDescription() {return Description;}

    public long getDuration(){return Duration;}

    public String getId(){return Id;}

    public static Event pullData(Map<String, Object> mappedData, String Id)
    {
       Event generatedEvent = new Event();
       generatedEvent.setIsDone((Boolean) mappedData.getOrDefault("isDone", false));
       generatedEvent.setId(Id);
       generatedEvent.setTitle((String) mappedData.getOrDefault("title", ""));
       generatedEvent.setDescription((String) mappedData.getOrDefault("description", ""));
       //All other DB data retrieval goes in this static method
        // generatedEvent.
        return generatedEvent;
    }
}
