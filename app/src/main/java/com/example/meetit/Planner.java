package com.example.meetit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

//Main screen of the app, from here the user can navigate to the different functionalities

public class Planner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        //Define and initialize buttons
        Button sendingrequest = findViewById(R.id.sendrequest_button);
        Button receiverequest = findViewById(R.id.receiverequest_button);
        Button planoverview = findViewById(R.id.planoverview_button);
        Button signoutButton = findViewById(R.id.signout);

        //Onclicklisteners for the buttons that sends the user on to the appropriate screen

        sendingrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSendingRequest();
            }
        });

        receiverequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReceiveRequest();
            }
        });

        planoverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSchedules();
            }
        });

        //When the sign out button is pressed, sign the user out and send them back to the login screen.
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(Planner.this);
                Toast.makeText(Planner.this, "Signed out", Toast.LENGTH_SHORT).show();
                openHome();
            }
        });
    }

     public void openSendingRequest() {
            Intent intent = new Intent(this, SelectUser.class);
            startActivity(intent);
         }

     public void openReceiveRequest() {
            Intent intent = new Intent(this, ReceiveRequest.class);
            startActivity(intent);
         }

     public void openSchedules() {
            Intent intent = new Intent(this,Schedules.class);
            startActivity(intent);
         }

     public void openHome() {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
         }
}

