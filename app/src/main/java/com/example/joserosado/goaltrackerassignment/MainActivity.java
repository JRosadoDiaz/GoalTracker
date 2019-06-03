package com.example.joserosado.goaltrackerassignment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joserosado.goaltrackerassignment.GoalTracker.db.EFDatabaseHelper;
import com.example.joserosado.goaltrackerassignment.GoalTracker.db.FirebaseManager;

import static android.support.v4.app.NotificationCompat.PRIORITY_DEFAULT;

public class MainActivity extends AppCompatActivity {

    EFDatabaseHelper Helper = new EFDatabaseHelper();
    FirebaseManager manager = new FirebaseManager();
    private static final String CHANNEL_ID="timer_notification";
    private static final String CHANNEL_NAME="Notification";
    private static final String CHANNEL_DESC="Goal Timer Notifications";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText passwordField = findViewById(R.id.Password);
        EditText emailField = findViewById(R.id.email);
        Button login_button = findViewById(R.id.login_button);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                String password = passwordField.getText().toString();
                String email = emailField.getText().toString();
                // Testing Input of text views
                if(password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please input a password", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }
                else if(email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please input an email", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }
                if(isValid)
                {
                    attemptSignIn(email, password);
                }


            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel );
        }
        findViewById(R.id.Notification_Test).setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){
                displayNotification();
            }
        });
    }

    private void displayNotification(){
        NotificationCompat.Builder nBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_check)
                .setContentTitle("You Have a Task")
                .setContentText("You have a task due at this time. Has it been completed?")
                .setPriority(PRIORITY_DEFAULT);

        NotificationManagerCompat nManagerCompat = NotificationManagerCompat.from(this);
        nManagerCompat.notify(1, nBuilder.build());
    }

    private void attemptSignIn(String email, String password)
    {
        manager.signIn(email, password)
              .addOnCompleteListener(task ->{
                  if(task.isSuccessful() && task.getResult().getUser() != null)
                  {
                      WeekListActivityIntent();
                  }else
                  {
                      Toast.makeText(getApplicationContext(), "Your email or password is incorrect, please try again", Toast.LENGTH_SHORT)
                              .show();
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
