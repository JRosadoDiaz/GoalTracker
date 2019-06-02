package com.example.joserosado.goaltrackerassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joserosado.goaltrackerassignment.GoalTracker.db.EFDatabaseHelper;
import com.example.joserosado.goaltrackerassignment.GoalTracker.db.FirebaseManager;

public class MainActivity extends AppCompatActivity {

    EFDatabaseHelper Helper = new EFDatabaseHelper();
    FirebaseManager manager = new FirebaseManager();

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
