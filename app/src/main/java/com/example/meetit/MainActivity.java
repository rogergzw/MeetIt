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

public class MainActivity extends AppCompatActivity {
    private Button signin_button;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 1;

    public static String mUsername;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);

        signin_button = findViewById(R.id.signin_button);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(MainActivity.this, "You are now logged in. Welcome to MeetIt!", Toast.LENGTH_SHORT).show();
                    onSignedInInitialize(user.getUid(), user.getEmail(), user.getDisplayName());
                    openPlanner();
                } else {
                    onSignedOutCleanup();
                }
            }
        };

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

    public void openHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void onSignedInInitialize(String uid, String email, String username) {
        mUsername = username;
        User currentUser = new User(email, username, uid);
        mDatabaseReference = mDatabaseReference.child("user").child(uid);
        mDatabaseReference.setValue(currentUser);
        openPlanner();
//        attachDatabaseReadListener();
    }
    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
//        openHome();
    }
}
