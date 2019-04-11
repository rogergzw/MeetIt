package com.example.meetit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class UserListAdapter extends ArrayAdapter<User> {
    private static final String TAG = "RequestListAdapter";
    private Context mContext;
    int mResource;

    public UserListAdapter(Context context, int resource, ArrayList<User> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String username = getItem(position).getUsername();
        String email = getItem(position).getEmail();
        String uid = getItem(position).getUid();

        User user = new User(username, email, uid);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(mResource, parent, false);
        TextView nameTV = rowView.findViewById(R.id.name);
        TextView emailTV = rowView.findViewById(R.id.email);
        TextView uidTV = rowView.findViewById(R.id.uid);

        nameTV.setText(username);
        emailTV.setText(email);
        uidTV.setText(uid);

        return rowView;
    }
}
