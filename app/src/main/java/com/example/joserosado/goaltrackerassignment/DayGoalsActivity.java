package com.example.joserosado.goaltrackerassignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.Event;
import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.Sprint;
import com.example.joserosado.goaltrackerassignment.GoalTracker.db.EFDatabaseHelper;

public class DayGoalsActivity extends AppCompatActivity {
    EFDatabaseHelper helper = new EFDatabaseHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_goals);

        Sprint currentSprint = helper.GetCurrentSprint();

        for (Event item : currentSprint.Events ) {

        }
    }
}
