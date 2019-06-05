package com.example.joserosado.goaltrackerassignment;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.Event;
import com.example.joserosado.goaltrackerassignment.GoalTracker.db.FirebaseManager;

public class AddActivity extends AppCompatActivity {

    private FirebaseManager manager = new FirebaseManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        String DayOfTheWeek = getIntent().getStringExtra("DayOfTheWeek");
        Toast toastDay = Toast.makeText(getApplicationContext(), DayOfTheWeek,Toast.LENGTH_SHORT);
        toastDay.show();

        TextView Name_Text = findViewById(R.id.txtName);

        TextView Event_Description = findViewById(R.id.txtDescription);

        Button Submit_Button = findViewById(R.id.btnSubmit);

        Submit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String eventName = Name_Text.getText().toString();
                //System.out.println(eventName);

                String eventDescription = Event_Description.getText().toString();
                //System.out.println(eventDescription);

                if(eventName == null || eventName.isEmpty())
                {

                }else
                {
                    Event event = new Event();
                    event.setTitle(eventName);
                    event.setDescription(eventDescription == null ? "" : eventDescription);
                    manager.getCurrentSprint().onSuccessTask(result -> manager.addEventToSprint(event, result))
                    .addOnFailureListener(exception ->{
                        failEventAdditionDisplay();
                        Log.d("AddEvent", "AddingEventFailed", exception);
                    }).addOnCompleteListener(task ->
                    {
                        if(task.isSuccessful()) Toast.makeText(getApplicationContext(),eventName + " - " + eventDescription,Toast.LENGTH_SHORT)
                        .show();
                        AddActivity.this.finish();
                    });
                }
            }
        });
    }

    private void failEventAdditionDisplay()
    {
        Toast.makeText(getApplicationContext(), "Adding Event Failed, please try again", Toast.LENGTH_SHORT).show();
    }

  //  private void notifTest(){
        //NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)

         //       .setContentTitle("Testing")
       //         .setContentText("Testing")
     //           .setPriority(NotificationCompat.PRIORITY_DEFAULT);
   // }
}
