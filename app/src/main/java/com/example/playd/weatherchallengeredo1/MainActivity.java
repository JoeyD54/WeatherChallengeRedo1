package com.example.playd.weatherchallengeredo1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements LocationListener{

    private TextView printCity, printTemperature;
    private Button weatherButton;
    public final int MY_LOCATION_PERMISSION = 100;
    boolean locationChecked;

    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        printCity = (TextView) findViewById(R.id.cityText);
        printTemperature = (TextView) findViewById(R.id.temperatureText);

        locationChecked = checkLocationPermission();

        weatherButton = (Button) findViewById(R.id.weatherButton);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(locationChecked){
                    //get location again somehow.
                }
            }
        });

    }

    /*
     TODO(1) Create single page app to fetch and display weather conditions using users location
        use  https://www.wunderground.com/weather/api/d/docs?d=data/conditions

        condUrl = "http://api.wunderground.com/api/90645b02d360fe14/conditions/q/" + state + "/" + city + ".json";

     */

    public boolean checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            //Check if we should show an explanation
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_LOCATION_PERMISSION);
                                }})
                        .create()
                        .show();
            } else {
                //No explanation needed. Just show request
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_LOCATION_PERMISSION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                          String Permissions[], int[] grantResults){
        switch(requestCode){
            case MY_LOCATION_PERMISSION: {
                //if request is cancelled, the result arrays are empty.
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission granted, get mah location.
                    if(ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED){

                        fusedLocationProviderClient.getLastLocation()
                                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        if(location != null){
                                            //Do location logic here.
                                            onLocationChanged(location);
                                        }
                                    }
                                });
                }
            } else {
                //Permission denied. Disable location shit.

                }
                return;
            }
        }
    }


    @Override
    public void onProviderDisabled(String provider){

    }

    @Override
    public void onProviderEnabled(String provider){

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){

    }

    @Override
    public void onLocationChanged(Location location) {
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();
        printCity.setText(Double.toString(latitude));
        printTemperature.setText(Double.toString(longitude));
    }

//    @Override
//    protected void onHandleIntent(Intent intent){
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//    }
}