package com.example.meetit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    private Button signin_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button signin_button = findViewById(R.id.signin_button);

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                openPlanner();
            }
         });
     }

     public void openPlanner() {
         Intent intent = new Intent(this, Planner.class);
         startActivity(intent);
    }
}
