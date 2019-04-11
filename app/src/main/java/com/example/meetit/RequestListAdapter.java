package com.example.meetit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class RequestListAdapter extends ArrayAdapter<MeetingRequest> {
    private static final String TAG = "RequestListAdapter";
    private Context mContext;
    int mResource;

    public RequestListAdapter(Context context, int resource, ArrayList<MeetingRequest> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String title = getItem(position).getTitle();
        String to = getItem(position).getTo();
        String from = getItem(position).getFrom();
        String time = getItem(position).getDateTime();
        String location = getItem(position).getLocation();
        Boolean accepted = getItem(position).getAccepted();

        MeetingRequest request = new MeetingRequest(title, to, from, time, location, accepted);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(mResource, parent, false);
        TextView titleTV = rowView.findViewById(R.id.title);
        TextView fromTV = rowView.findViewById(R.id.from);
        TextView timeTV = rowView.findViewById(R.id.time);
        TextView locationTV = rowView.findViewById(R.id.title);

        titleTV.setText(title);
        fromTV.setText(from);
        timeTV.setText(time);
        locationTV.setText(location);

        return rowView;
    }
}
