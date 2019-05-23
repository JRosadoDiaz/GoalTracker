package com.example.joserosado.goaltrackerassignment;

import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.Event;
import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.Sprint;

import java.util.ArrayList;
import java.util.List;

public class EFDatabaseHelper {
    private List<Sprint> sprintList;
    private Sprint currentSprint;

    public EFDatabaseHelper() {
        currentSprint = new Sprint();

        sprintList.add(currentSprint);
    }

    public void populateSprint() {
        Event eventOne = new Event();
        eventOne.setTitle("Work");
        Event eventTwo = new Event();
        eventTwo.setTitle("Date");
        Event eventThree = new Event();
        eventThree.setTitle("Sports game");

        currentSprint.Events.add(eventOne);
        currentSprint.Events.add(eventTwo);
        currentSprint.Events.add(eventThree);
    }


    public Sprint GetCurrentSprint() {
        return currentSprint;
    }
}
