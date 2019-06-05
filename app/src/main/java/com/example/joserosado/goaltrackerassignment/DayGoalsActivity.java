package com.example.joserosado.goaltrackerassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.Event;
import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.Sprint;
import com.example.joserosado.goaltrackerassignment.GoalTracker.db.EFDatabaseHelper;
import com.example.joserosado.goaltrackerassignment.GoalTracker.db.FirebaseManager;

public class DayGoalsActivity extends AppCompatActivity {
    FirebaseManager manager = new FirebaseManager();
  //  String day;

    //public DayGoalsActivity(){
      //  day = getIntent().getStringExtra("DayOfTheWeek");

    //}

    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String day = getIntent().getStringExtra("DayOfTheWeek");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_goals);
        TextView dayView = (TextView)findViewById(R.id.dayView);
        dayView.setText(day);

        Button add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Testing Input of text views
                AddActivityIntent(day);
            }
        });
        createList();
    }

    private void  createList(){
        manager.getCurrentSprint().addOnSuccessListener(currentSprint ->
        {
            linearLayout = DayGoalsActivity.this.findViewById(R.id.day_list);
            for (Event item : currentSprint.Events) {
                TextView tv = new TextView(DayGoalsActivity.this);
                tv.setText(item.getTitle());
                CheckBox cb = new CheckBox(DayGoalsActivity.this);
                if (item.getIsDone() == true) {
                    cb.setChecked(true);
                }
                linearLayout.addView(tv);
                linearLayout.addView(cb);
            }
        });
    }

    private void AddActivityIntent(String day){
        Intent intent = new Intent(this,AddActivity.class);
        intent.putExtra("DayOfTheWeek", day);
        startActivity(intent);
        //Toast toast = Toast.makeText(getApplicationContext(),"Hits",Toast.LENGTH_SHORT);
        //toast.show();
    }
}
