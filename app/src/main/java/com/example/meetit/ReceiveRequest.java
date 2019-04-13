package com.example.meetit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

//In this activity the meetingrequests that have been sent to the current user are displayed. The user can accept or decline these requests.

public class ReceiveRequest extends AppCompatActivity {

    //Define variables
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth Auth;
    private RequestListAdapter adapter;
    private ListView mListView;
    private ChildEventListener mChildeventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_request);

        //Initialize database
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("user");

        //Initialize firebaseauth
        Auth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = Auth.getCurrentUser();

        //Initialize listview and arraylist containing meetingrequest objects
        mListView = (ListView) findViewById(R.id.requestlist);
        ArrayList<MeetingRequest> requestList = new ArrayList<>();

        //Initialize adapter
        adapter = new RequestListAdapter(this, R.layout.meetingrequest_recieve, requestList);
        mListView.setAdapter(adapter);

        //Listener that checks whether data at the reference point has changed
        mChildeventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //On attachment of the listener or when a child is added, add the meetingrequests in the database to the listview
                MeetingRequest request = dataSnapshot.getValue(MeetingRequest.class);
                if (request != null) {
                    adapter.add(request);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //When a child is removed (a meetingrequest is accepted or denied) remove that item from the listview
                MeetingRequest request = dataSnapshot.getValue(MeetingRequest.class);
                removeItem(request.getTo());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        //Attach listener
        databaseReference.child(firebaseUser.getUid()).child("incoming").addChildEventListener(mChildeventListener);
    }

    void removeItem(String uid) {
        //Clear listview of items
        adapter.clear();
        //Add remaining items to the listview
        databaseReference.child(uid).child("incoming").removeEventListener(mChildeventListener);
        databaseReference.child(uid).child("incoming").addChildEventListener(mChildeventListener);
    }

    //Adapter
    private class RequestListAdapter extends ArrayAdapter<MeetingRequest> {
        //Initialize variables
        private static final String TAG = "RequestListAdapter";
        private Context mContext;
        int mResource;

        //Default constructor
        public RequestListAdapter(Context context, int resource, ArrayList<MeetingRequest> objects) {
            super(context, resource, objects);
            this.mContext = context;
            this.mResource = resource;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            //Retrieve data from meetingrequest object
            String title = getItem(position).getTitle();
            String time = getItem(position).getDateTime();
            String location = getItem(position).getLocation();
            String with = getItem(position).getWith();
            String key = getItem(position).getKey();
            String from = getItem(position).getFrom();

            //Initialize inflater to populate the listitem
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);

            //Define and initialize views of the listitem
            TextView titleTV = convertView.findViewById(R.id.title);
            TextView withTV = convertView.findViewById(R.id.with);
            TextView timeTV = convertView.findViewById(R.id.time);
            TextView locationTV = convertView.findViewById(R.id.location);
            TextView keyTV = convertView.findViewById(R.id.key);
            TextView fromTV = convertView.findViewById(R.id.withuid);
            Button acceptbutton = convertView.findViewById(R.id.acceptbutton);
            Button declinebutton = convertView.findViewById(R.id.declinebutton);

            //When the accept button of a list item is clicked, get the data of the corresponding meetingrequest and perform database operations
            acceptbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Meeting request accepted", Toast.LENGTH_SHORT).show();
                    MeetingRequest acceptedRequest = new MeetingRequest(
                            getItem(position).getTitle(),
                            getItem(position).getTo(),
                            getItem(position).getFrom(),
                            getItem(position).getDateTime(),
                            getItem(position).getLocation(),
                            2,
                            getItem(position).getKey(),
                            getItem(position).getWith());
                    acceptRequest(acceptedRequest);
                }
            });

            //When the accept button of a list item is clicked, get the data of the corresponding meetingrequest and perform database operations
            declinebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Meeting request declined", Toast.LENGTH_SHORT).show();
                    MeetingRequest declinedRequest = new MeetingRequest(
                            getItem(position).getTitle(),
                            getItem(position).getTo(),
                            getItem(position).getFrom(),
                            getItem(position).getDateTime(),
                            getItem(position).getLocation(),
                            0,
                            getItem(position).getKey(),
                            getItem(position).getWith());
                    declineRequest(declinedRequest);
                }
            });

            //Set values of the views in the listitem
            titleTV.setText(title);
            withTV.setText("With: " + with);
            timeTV.setText("At: " + time);
            locationTV.setText("In: " + location);
            keyTV.setText(key);
            fromTV.setText(from);

            //Return listitem
            return convertView;
        }
    }

    //Operations to perform on accept
    public void acceptRequest(MeetingRequest acceptedRequest) {
        //Edit the accepted value of the request of the sender, which is already in their planning
        databaseReference.child(acceptedRequest.getFrom()).child("planning").child(acceptedRequest.getKey()).child("accepted").setValue(2);
        //Remove the request from the incoming request of the recipient
        databaseReference.child(acceptedRequest.getTo()).child("incoming").child(acceptedRequest.getKey()).removeValue();
        //Add the request to the planning of the recipient
        databaseReference.child(acceptedRequest.getTo()).child("planning").child(acceptedRequest.getKey()).setValue(acceptedRequest);
    }

    //Operations to perform on decline
    public void declineRequest(MeetingRequest declinedRequest) {
        //Edit the accepted value of the request of the sender, which is already in their planning
        databaseReference.child(declinedRequest.getFrom()).child("planning").child(declinedRequest.getKey()).child("accepted").setValue(0);
        //Remove the request from the incoming request of the recipient
        databaseReference.child(declinedRequest.getTo()).child("incoming").child(declinedRequest.getKey()).removeValue();
    }
}
