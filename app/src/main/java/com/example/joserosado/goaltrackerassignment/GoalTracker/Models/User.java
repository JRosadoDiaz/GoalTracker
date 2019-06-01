package com.example.joserosado.goaltrackerassignment.GoalTracker.Models;

import java.util.List;

public class User {
    private String Email;
    private String PassWord;
    private String ID;
    private List<Sprint> PastSprints;
    private Sprint CurrentSprint;

    public void setEmail(String email) {
        if(email != null && !email.isEmpty())
        this.Email = email;
    }

    public void setID(String id) {
        if(id != null && !id.isEmpty()) this.ID = id;
    }

    public void setCurrentSprint(Sprint s) {
        if(s != null) this.CurrentSprint = s;
    }

    public void setPastSprints(List<Sprint> sprints) {
        if(sprints != null) this.PastSprints = sprints;
    }
    // TODO: add Config param

}
