package com.example.meetit;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;

public class Home extends AppCompatActivity {
    private Button signin_button;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mMeetingRequestDatabaseReference;



//    private FirebaseAuth mFirebaseAuth;
//    private FirebaseAuth.AuthStateListener mAuthStateListener;
//
//    public static final String ANONYMOUS = "anonymous";
//    public static final int RC_SIGN_IN = 1;
//
//    public String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button signin_button = findViewById(R.id.signin_button);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMeetingRequestDatabaseReference = mFirebaseDatabase.getReference().child("meetingrequests");
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    Toast.makeText(Home.this, "You are now logged in. Welcome to Friendly Chat!", Toast.LENGTH_SHORT).show();
//                    onSignedInInitialize(user.getDisplayName());
//
//                } else {
//                    onSignedOutCleanup();
//                    startActivityForResult(
//                            AuthUI.getInstance()
//                                    .createSignInIntentBuilder()
//                                    .setIsSmartLockEnabled(false)
//                                    .setAvailableProviders(Arrays.asList(
//                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
//                                            new AuthUI.IdpConfig.EmailBuilder().build()))
//                                    .build(),
//                            RC_SIGN_IN);
//                }
//            }
//        };

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
//                startActivityForResult(
//                        AuthUI.getInstance()
//                                .createSignInIntentBuilder()
//                                .setIsSmartLockEnabled(false)
//                                .setAvailableProviders(Arrays.asList(
//                                        new AuthUI.IdpConfig.GoogleBuilder().build(),
//                                        new AuthUI.IdpConfig.EmailBuilder().build()))
//                                .build(),
//                        RC_SIGN_IN);
                openPlanner();
            }
         });
     }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN) {
//            if (resultCode == RESULT_OK) {
//                Toast.makeText(Home.this, "Signed in!", Toast.LENGTH_SHORT).show();
//            } else if (resultCode == RESULT_CANCELED){
//                Toast.makeText(Home.this, "Sign in cancelled!", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
//    }


    @Override
    protected void onPause() {
        super.onPause();
//        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

     public void openPlanner() {
            Intent intent = new Intent(this, Planner.class);
            startActivity(intent);
    }

//    private void onSignedInInitialize(String username) {
//        mUsername = username;
//        openPlanner();
////        attachDatabaseReadListener();
//    }
//
//    private void onSignedOutCleanup() {
//        mUsername = ANONYMOUS;
////        mMessageAdapter.clear();
//
//    }
}
