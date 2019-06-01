package com.example.joserosado.goaltrackerassignment.GoalTracker.db;

import android.support.annotation.NonNull;

import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.Event;
import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.Sprint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class EFDatabaseHelper {
    private Sprint currentSprint;

    public EFDatabaseHelper() {
        currentSprint = new Sprint();
    }

    public Sprint GetCurrentSprint() {
        Event eventOne = new Event();
        eventOne.setTitle("Work");

        Event eventTwo = new Event();
        eventTwo.setTitle("Date");

        Event eventThree = new Event();
        eventThree.setTitle("Sports game");

        currentSprint.Events.add(eventOne);
        currentSprint.Events.add(eventTwo);
        currentSprint.Events.add(eventThree);

        return currentSprint;
    }

    public Event GetEvent() {
        return new Event() {

        };
    }

    public void SaveEvent(Event event) {

    }
}
