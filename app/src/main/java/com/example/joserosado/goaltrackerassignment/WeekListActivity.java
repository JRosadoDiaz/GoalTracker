package com.example.joserosado.goaltrackerassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.joserosado.goaltrackerassignment.GoalTracker.db.FirebaseManager;

public class WeekListActivity extends AppCompatActivity {

    FirebaseManager manager = new FirebaseManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week_log);
        createHandlers();
    }

    private void createHandlers(){
        Button sunday_button = findViewById(R.id.sunday);


        sunday_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Testing Input of text views

                DayGoalsActivityIntent(DAYS_OF_WEEK.SUNDAY);

            }
        });
        Button monday_button = findViewById(R.id.monday);

        monday_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Testing Input of text view
                DayGoalsActivityIntent(DAYS_OF_WEEK.MONDAY);

            }
        });
        Button tuesday_button = findViewById(R.id.tuesday);


        tuesday_button .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Testing Input of text views
                DayGoalsActivityIntent(DAYS_OF_WEEK.TUESDAY);

            }
        });
        Button wednesday_button = findViewById(R.id.wednesday);


        wednesday_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Testing Input of text views
                DayGoalsActivityIntent(DAYS_OF_WEEK.WEDNESDAY);


            }
        });
        Button thursday_button = findViewById(R.id.thursday);


        thursday_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Testing Input of text views
                DayGoalsActivityIntent(DAYS_OF_WEEK.THURSDAY);


            }
        });
        Button friday_button = findViewById(R.id.friday);


        friday_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Testing Input of text views
                DayGoalsActivityIntent(DAYS_OF_WEEK.FRIDAY);


            }
        });
        Button saturday_button = findViewById(R.id.saturday);


        saturday_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Testing Input of text views
                DayGoalsActivityIntent(DAYS_OF_WEEK.SATURDAY);

            }
        });
    }

    private void DayGoalsActivityIntent(DAYS_OF_WEEK day){
        Intent intent = new Intent(this,DayGoalsActivity.class);
        intent.putExtra("DayOfTheWeek", day.toString());
        startActivity(intent);
        //Toast.makeText(getApplicationContext(),manager.getSignedInUser().getEmail(),Toast.LENGTH_SHORT)
        //.show();
    }
}
