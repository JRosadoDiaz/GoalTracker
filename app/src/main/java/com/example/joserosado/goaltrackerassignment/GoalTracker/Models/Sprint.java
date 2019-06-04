package com.example.joserosado.goaltrackerassignment.GoalTracker.Models;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Sprint {
    public ArrayList<Event> Events;
    private String sprintId;

    public Sprint() {
        Events = new ArrayList<>();
    }

    public String getSprintId() {
        return sprintId;
    }

    public void setSprintId(String id)
    {
        sprintId = id;
    }


}
