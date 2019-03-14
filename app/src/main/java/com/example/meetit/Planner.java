package com.example.meetit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Planner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
    }

    public void goToReceive(View view) {
        startActivity(new Intent(this, ReceiveRequest.class));
    }

    public void goToSend(View view) {
        startActivity(new Intent(this, SendingRequest.class));
    }

    public void goToLocation(View view) {
        startActivity(new Intent(this, Location.class));
    }
}
