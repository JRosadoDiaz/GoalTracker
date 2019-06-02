package com.example.joserosado.goaltrackerassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.Event;
import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.Sprint;
import com.example.joserosado.goaltrackerassignment.GoalTracker.db.EFDatabaseHelper;

public class DayGoalsActivity extends AppCompatActivity {
    EFDatabaseHelper helper = new EFDatabaseHelper();

    String day;



    public DayGoalsActivity(){
        day = getIntent().getStringExtra("DayOfTheWeek");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_goals);

        Sprint currentSprint = helper.GetCurrentSprint();

        Button add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Testing Input of text views
                setContentView(R.layout.add);
                AddActivityIntent(day);
            }
        });
        for (Event item : currentSprint.Events ) {

        }
    }

    private void AddActivityIntent(String day){
        Intent intent = new Intent(this,AddActivity.class);
        intent.putExtra("DayOfTheWeek", day);
        startActivity(intent);
        //Toast toast = Toast.makeText(getApplicationContext(),"Hits",Toast.LENGTH_SHORT);
        //toast.show();
    }
}
