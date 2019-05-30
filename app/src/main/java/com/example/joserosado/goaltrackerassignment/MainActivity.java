package com.example.joserosado.goaltrackerassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.joserosado.goaltrackerassignment.GoalTracker.db.EFDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    EFDatabaseHelper Helper = new EFDatabaseHelper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button login_button = findViewById(R.id.login_button);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Testing Input of text views

                WeekListActivityIntent();
            }
        });
    }

    private void WeekListActivityIntent(){
        Intent intent = new Intent(this,WeekListActivity.class);
        startActivity(intent);
        //Toast toast = Toast.makeText(getApplicationContext(),"Hits",Toast.LENGTH_SHORT);
        //toast.show();
    }
}
