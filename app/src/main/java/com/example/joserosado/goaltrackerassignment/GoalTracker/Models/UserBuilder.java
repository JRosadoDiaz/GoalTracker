package com.example.joserosado.goaltrackerassignment.GoalTracker.Models;

import java.util.List;

public class UserBuilder {
    private User user;
    public UserBuilder()
    {
        newInstance();
    }

    public UserBuilder email(String email)
    {
        user.setEmail(email);
        return this;
    }

    public UserBuilder id(String ID)
    {
        user.setID(ID);
        return this;
    }

    public UserBuilder currentSprint(Sprint s)
    {
        user.setCurrentSprint(s);
        return this;
    }

    public UserBuilder pastSprints(List<Sprint> sprints)
    {
        user.setPastSprints(sprints);
        return this;
    }

    public UserBuilder newInstance()
    {
        user = new User();
        return this;
    }

    public User returnUser()
    {
        return user;
    }
}
