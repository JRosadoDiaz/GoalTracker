package com.example.joserosado.goaltrackerassignment.GoalTracker.Models;

enum NotificationFrequency {
    CONSTANT,
    MINUTELY,
    FIVE_MINUTES,
    TEN_MINUTES,
    FIFTEEN_MINUTES,
    THIRTY_MINUTES,
    HOURLY,
    DAILY,
    WEEKDAYS
}

public class Config {
    private String SoundSelection;
    private NotificationFrequency ReminderFrequency;
}
