package com.example.meetit;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SendingRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendingrequest);
    }

    private static final String TAG = "SendingRequest";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;



}
