package com.example.meetit;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//Adapter that is used to create the listitems for the planned meetings

class PlanningListAdapter extends ArrayAdapter<MeetingRequest> {

    //Initialize variables
    private static final String TAG = "PlanningListAdapter";
    private Context mContext;
    int mResource;

    //Default constructor
    public PlanningListAdapter(Context context, int resource, ArrayList<MeetingRequest> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Retrieve data from the meetingrequest object
        String title = getItem(position).getTitle();
        String time = getItem(position).getDateTime();
        String location = getItem(position).getLocation();
        String with = getItem(position).getWith();
        int accepted = getItem(position).getAccepted();

        //Initialize inflater to populate the listitem
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(mResource, parent, false);

        //Define and initialize views of the listitem
        TextView titleTV = rowView.findViewById(R.id.title);
        TextView fromTV = rowView.findViewById(R.id.from);
        TextView timeTV = rowView.findViewById(R.id.time);
        TextView locationTV = rowView.findViewById(R.id.location);
        TextView acceptedTV = rowView.findViewById(R.id.accepted);

        //Set values of the views in the listitem
        titleTV.setText(title);
        fromTV.setText("With: " + with);
        timeTV.setText("At: " + time);
        locationTV.setText("In: " + location);
        if (accepted == 2) {
            acceptedTV.setText("Accepted");
            acceptedTV.setTextColor(Color.GREEN);
        } else if (accepted == 1) {
            acceptedTV.setText("Waiting for response");
            acceptedTV.setTextColor(Color.YELLOW);
        } else if (accepted == 0) {
            acceptedTV.setText("Declined");
            acceptedTV.setTextColor(Color.RED);
        }

        //Return listitem
        return rowView;
    }
}
