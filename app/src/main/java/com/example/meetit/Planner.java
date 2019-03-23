package com.example.meetit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Planner extends AppCompatActivity {
    private Button sendingrequest_button;
    private Button receiverequest_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        Button sendingrequest = findViewById(R.id.sendrequest_button);
        Button receiverequest = findViewById(R.id.receiverequest_button);

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
    }

     public void openSendingRequest() {
            Intent intent = new Intent(this, SendingRequest.class);
            startActivity(intent);
         }

     public void openReceiveRequest() {
            Intent intent = new Intent(this, ReceiveRequest.class);
            startActivity(intent);
         }

    }