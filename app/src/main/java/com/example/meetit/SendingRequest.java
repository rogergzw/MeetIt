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

public class SendingRequest extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMeetingRequestDatabaseReference;
    private ChildEventListener mChildEventListener;

    Button btnDatePicker, btnTimePicker, btnSubmit;
    EditText txtDate, txtTime, txtTitle, txtLocation;
    TextView txtTo;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String dateTime;
    private String to;
    private String toUid;
    private Boolean accepted;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendingrequest);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMeetingRequestDatabaseReference = mFirebaseDatabase.getReference().child("user");

        txtTitle = (EditText) findViewById(R.id.title);

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        btnSubmit = (Button) findViewById(R.id.submit);

        txtTo = (TextView) findViewById(R.id.sendingto);

        Intent intent = getIntent();
        to = intent.getStringExtra("displayName");
        toUid = intent.getStringExtra("uid");
        txtTo.setText("Meeting with " + to);

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

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTime = txtDate.getText().toString() + " " + txtTime.getText().toString();

                MeetingRequest request = new MeetingRequest(txtTitle.getText().toString(), toUid, user.getUid(), dateTime, "Atlas", false, null);

                mMeetingRequestDatabaseReference.child(user.getUid()).child("Planning").push().setValue(request);
                String requestKey = mMeetingRequestDatabaseReference.child(user.getUid()).child("Planning").getKey();
                request.setKey(requestKey);
                mMeetingRequestDatabaseReference.child(toUid).child("incoming").push().setValue(request);

                txtTime.setText("");
                txtDate.setText("");
                txtTitle.setText("");

                Toast toast= Toast.makeText(getApplicationContext(),
                        "Meeting Request Sent!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        });
    }
}
