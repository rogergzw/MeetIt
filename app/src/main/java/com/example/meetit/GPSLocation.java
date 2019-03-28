package com.example.meetit;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//
//import java.util.Locale;

public class GPSLocation extends AppCompatActivity {
    private int LOCATION_PERMISSION_CODE = 1;
    //private FusedLocationProviderClient fusedLocationClient;

    //protected Location mLastLocation;

    private TextView locationTV;
    //private String mLatitudeLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

//        locationTV = findViewById(R.id.location_text_view);
//        //mLatitudeLabel = "latitude";
//
//        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        Button buttonRequest = findViewById(R.id.getLocation);
//        buttonRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(GPSLocation.this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(GPSLocation.this,
//                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(GPSLocation.this, "You have already granted this permission!",
//                            Toast.LENGTH_SHORT).show();
//                    getLocation();
//                } else {
//                    requestStoragePermission();
//                }
//            }
//        });
    }

//    private void requestStoragePermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION)) {
//
//            new AlertDialog.Builder(this)
//                    .setTitle("Permission needed")
//                    .setMessage("You permission is needed to show your current location")
//                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(GPSLocation.this,
//                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
//                        }
//                    })
//                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .create().show();
//
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == LOCATION_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
//                getLocation();
//            } else {
//                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private void getLocation() {
//        locationTV.append("test");
//        fusedLocationClient.getLastLocation()
//                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        if (task.isSuccessful() && task.getResult() != null) {
//                            mLastLocation = task.getResult();
//                            locationTV.setText(String.format(Locale.ENGLISH, "%s: %f",
//                                    mLatitudeLabel,
//                                    mLastLocation.getLatitude()));
//                        } else {
//                            locationTV.append("no location found");
//                        }
//                    }
//                });
//    }
}