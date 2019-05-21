package com.example.joserosado.goaltrackerassignment.GoalTracker.Models;

import java.util.HashMap;
import java.util.List;

public class User {
    private String Email;
    private String PassWord;
    private String ID;
    private List<Sprint> PastSprints;
    private Sprint CurrentSprint;
    // TODO: add Config param


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<Sprint> getPastSprints() {
        return PastSprints;
    }

    public void setPastSprints(List<Sprint> pastSprints) {
        PastSprints = pastSprints;
    }

    public Sprint getCurrentSprint() {
        return CurrentSprint;
    }

    public void setCurrentSprint(Sprint currentSprint) {
        CurrentSprint = currentSprint;
    }
}
