package com.example.joserosado.goaltrackerassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joserosado.goaltrackerassignment.GoalTracker.db.FirebaseManager;

public class Register extends AppCompatActivity {

    FirebaseManager manager = new FirebaseManager();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        EditText passwordRegister = findViewById(R.id.registerpassword);
        EditText passwordConfirm = findViewById(R.id.confirmText);
        EditText emailRegister = findViewById(R.id.registerEmail);
        Button register_button = findViewById(R.id.register_button);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isRegister = true;

                String password = passwordRegister.getText().toString();
                String email = emailRegister.getText().toString();
                String confirm = passwordConfirm.getText().toString();


                if(password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please input a password", Toast.LENGTH_SHORT).show();
                    isRegister = false;
                }
                else if(email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please input an email", Toast.LENGTH_SHORT).show();
                    isRegister = false;
                }
                else if(confirm.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please confirm your password", Toast.LENGTH_SHORT).show();
                    isRegister = false;
                }else if(!confirm.equals(password))
                {
                    Toast.makeText(getApplicationContext(), "Your passwords do not match, please try again", Toast.LENGTH_SHORT)
                            .show();
                    isRegister = false;
                }
                if(isRegister)
                {
                    manager.createAccount(email, password);
                    attemptRegister(email,password);
                }
            }
        });
    }

    private void attemptRegister(String email, String password)
    {
        manager.createAccount(email, password)
                .addOnCompleteListener(task ->{
                    if(task.isSuccessful() && task.getResult().getUser() != null)
                    {
                        BacktoLogin();
                    }else
                    {
                        Toast.makeText(getApplicationContext(), "Your email or password is invaild, please try again", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

    }

    private void BacktoLogin(){
        finish();
    }

}
