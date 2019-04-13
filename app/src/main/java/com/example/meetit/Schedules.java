package com.example.meetit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//In this activity, meetingrequests that have been sent are displayed, as well as meetingrequest that were recieved and accepted.

public class Schedules extends AppCompatActivity {

    //Define variables
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedules);

        //Initialize database
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("user");

        //Initialize auth
        Auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = Auth.getCurrentUser();

        //Initialize listview and arraylist containing meetingrequest objects
        ListView mListView = (ListView) findViewById(R.id.meetinglist);
        ArrayList<MeetingRequest> plannedList = new ArrayList<>();

        //Initialize adapter
        final PlanningListAdapter adapter = new PlanningListAdapter(this, R.layout.meetingrequest_planned, plannedList);
        mListView.setAdapter(adapter);

        //Listener that checks whether data at the reference point has changed
        ChildEventListener mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //On attachment of the listener or when a child is added, add the meetingrequests in the database to the listview
                MeetingRequest request = dataSnapshot.getValue(MeetingRequest.class);
                adapter.add(request);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        //Attach listener
        databaseReference.child(firebaseUser.getUid()).child("planning").addChildEventListener(mChildEventListener);
    }
}
