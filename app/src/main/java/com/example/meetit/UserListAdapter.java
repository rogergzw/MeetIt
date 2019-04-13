package com.example.meetit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//Adapter for the list of users that can be selected to send a meetingrequest to.

class UserListAdapter extends ArrayAdapter<User> {
    //Initialize variables
    private static final String TAG = "UserListAdapter";
    private Context mContext;
    int mResource;

    //Default constructor
    public UserListAdapter(Context context, int resource, ArrayList<User> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Retrieve data from User object
        String username = getItem(position).getUsername();
        String email = getItem(position).getEmail();
        String uid = getItem(position).getUid();

        //Initialize inflater to populate the listitem
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(mResource, parent, false);

        //Define and initialize views of the listitem
        TextView nameTV = rowView.findViewById(R.id.name);
        TextView emailTV = rowView.findViewById(R.id.email);
        TextView uidTV = rowView.findViewById(R.id.uid);

        //Set values of the views in the listitem
        nameTV.setText(username);
        emailTV.setText(email);
        uidTV.setText(uid);

        //Return listitem
        return rowView;
    }
}
