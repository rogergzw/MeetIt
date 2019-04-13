
package com.example.meetit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

//First screen you see when opening the app. It leads the users through the register / sign in process

public class MainActivity extends AppCompatActivity {

    //Defining signin button
    private Button signin_button;

    //Defining firebaseauth variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    //Defining requestcode for the signin completionlistener
    public static final int RC_SIGN_IN = 1;

    //Defining firebasedatabase variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);

        //Initializing siginin button
        signin_button = findViewById(R.id.signin_button);

        //Initializing database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        // Initializing auth
        mFirebaseAuth = FirebaseAuth.getInstance();

        //Authstatelistener listens to changes in the status of the user (signed in or not signed in) and triggers when attached or when a user signs in or out
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(MainActivity.this, "You are now logged in. Welcome to MeetIt!", Toast.LENGTH_SHORT).show();
                    //method that saves the user's data in the database and sends them on to the next screen when logged in
                    onSignedInInitialize(user.getUid(), user.getEmail(), user.getDisplayName());
                }
            }
        };

        //When the signin button is pressed, this code runs. It starts the signin flow provided by firebaseauth with a completion callback.
        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(Arrays.asList(
                                        new AuthUI.IdpConfig.EmailBuilder().build()))
                                .build(),
                        RC_SIGN_IN);
            }
        });
     }

    //callback listener for when signing in goes wrong or is aborted.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED){
                Toast.makeText(MainActivity.this, "Sign in cancelled!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

     public void openPlanner() {
            Intent intent = new Intent(this, Planner.class);
            startActivity(intent);
    }

    //Method that writes the current user to the database without overwriting existing meetingrequests and sends the user on to the next screen.
    private void onSignedInInitialize(String uid, String email, String username) {
        User currentUser = new User(email, username, uid);
        mDatabaseReference = mDatabaseReference.child("user").child(uid);
        mDatabaseReference.child("username").setValue(currentUser.getUsername());
        mDatabaseReference.child("email").setValue(currentUser.getEmail());
        mDatabaseReference.child("uid").setValue(currentUser.getUid());
        openPlanner();
    }
}
