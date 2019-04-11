package com.example.meetit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectUser extends AppCompatActivity {

    private FirebaseDatabase mFirebasedatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private UserListAdapter mUserListAdapter;
    private ChildEventListener userListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        mFirebasedatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebasedatabase.getReference().child("user");
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        ListView mListView = findViewById(R.id.userlist);

        final ArrayList<User> users = new ArrayList<>();
        mUserListAdapter = new UserListAdapter(this, R.layout.userlistitem, users);
        mListView.setAdapter(mUserListAdapter);

        userListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                String uid = dataSnapshot.getKey();
                if (!uid.equals(firebaseUser.getUid())) {
                    mUserListAdapter.add(user);
                }
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

        mDatabaseReference.addChildEventListener(userListener);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView view1 = (TextView)view.findViewById(R.id.name);
                TextView view2 = (TextView)view.findViewById(R.id.uid);
                String name = view1.getText().toString();
                String uid = view2.getText().toString();
                Toast.makeText(getBaseContext(), name, Toast.LENGTH_LONG).show();
                goToSendingRequest(name, uid);
            }
        });
    }

    private void goToSendingRequest(String name, String uid) {
        Intent intent = new Intent(this, SendingRequest.class);
        intent.putExtra("displayName", name);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }
}
