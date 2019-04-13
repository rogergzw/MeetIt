package com.example.meetit;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Random;

//In this actitvity, users can input a name, time and date for the meetingrequest that they can send to the user that was selected in the previous activity.

public class SendingRequest extends AppCompatActivity {

    //Define database variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMeetingRequestDatabaseReference;

    //Define buttons and views
    Button btnDatePicker, btnTimePicker, btnSubmit;
    EditText txtDate, txtTime, txtTitle;
    TextView txtTo;

    //Define variables
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String dateTime;
    private String to;
    private String toUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendingrequest);

        //Get current firebaseuser
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Initialize database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMeetingRequestDatabaseReference = mFirebaseDatabase.getReference().child("user");

        //Initialize views and buttons
        txtTitle = (EditText) findViewById(R.id.title);
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        btnSubmit = (Button) findViewById(R.id.submit);
        txtTo = (TextView) findViewById(R.id.sendingto);

        //Get data about the user to which the request is being sent from the previous activity
        Intent intent = getIntent();
        to = intent.getStringExtra("displayName");
        toUid = intent.getStringExtra("uid");
        txtTo.setText("Meeting with " + to);

        //Datepicker
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(SendingRequest.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        //Timepicker
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(SendingRequest.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        //Send request button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Convert the date and time to a single string
                dateTime = txtDate.getText().toString() + " " + txtTime.getText().toString();

                //Get random meeting location
                String location = randomLocation();

                //Create meetingrequest object with the data from the sendingrequest form
                MeetingRequest request = new MeetingRequest(txtTitle.getText().toString(), toUid, user.getUid(), dateTime, location, 1, null, to);

                //Generate key used to store the meetingrequests
                String requestkey = mMeetingRequestDatabaseReference.push().getKey();
                request.setKey(requestkey);

                //Write the meetingrequest to the database in the planning node of the current user
                mMeetingRequestDatabaseReference.child(user.getUid()).child("planning").child(requestkey).setValue(request);

                //Edit the meetingrequest object for the recipient, so they can see who they are meeting with
                request.setWith(user.getDisplayName());
                //Write the meetingrequest to the database in the incoming node of the recipient of the meetingrequest
                mMeetingRequestDatabaseReference.child(toUid).child("incoming").child(requestkey).setValue(request);

                //Clear input fields
                txtTime.setText("");
                txtDate.setText("");
                txtTitle.setText("");

                Toast toast= Toast.makeText(getApplicationContext(),
                        "Meeting Request Sent!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();

                //Send the user back to the main screen
                Intent intent = new Intent(SendingRequest.this, Planner.class);
                startActivity(intent);
            }
        });
    }

    //Random location generator
    public String randomLocation() {
        Random rand = new Random();
        int selector = rand.nextInt(9);
        switch (selector) {
            case 0:
                return "Atlas";
            case 1:
                return "Gemini";
            case 2:
                return "Luna";
            case 3:
                return "MetaForum";
            case 4:
                return "Paviljoen";
            case 5:
                return "IPO";
            case 6:
                return "Matrix";
            case 7:
                return "Flux";
            case 8:
                return "Traverse";
            default:
                return "";
        }
    }
}
