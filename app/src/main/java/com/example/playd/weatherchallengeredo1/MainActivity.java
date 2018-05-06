package com.example.playd.weatherchallengeredo1;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements LocationListener{

    private TextView printCity, printTemperature;
    private Button weatherButton;
    public final int MY_LOCATION_PERMISSION = 0;
    public LocationManager locationManager;
    public String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);

        Location location = locationManager.getLastKnownLocation(provider);

        printCity = (TextView) findViewById(R.id.cityText);
        printTemperature = (TextView) findViewById(R.id.temperatureText);
        weatherButton = (Button) findViewById(R.id.weatherButton);

        checkLocationPermission();

        buttonPressed(location);

//        if (weatherButton.isPressed()) {
//            printCity.setText(location.toString());
//        }
    }

    public void buttonPressed(Location location){
        if(weatherButton.isPressed()){
            printCity.setText(location.toString());
        }
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
                        locationManager.requestLocationUpdates(provider, 400, 1, this);
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
    public void onLocationChanged(Location location){
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        String msg = "New latitude: " + latitude + " New longitude: " + longitude;
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}