package com.example.joserosado.goaltrackerassignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

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

                Toast toast = Toast.makeText(getApplicationContext(),eventName + " - " + eventDescription,Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
