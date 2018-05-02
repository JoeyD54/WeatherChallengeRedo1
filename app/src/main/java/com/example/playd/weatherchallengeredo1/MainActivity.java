package com.example.playd.weatherchallengeredo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    /*
     TODO(1) Create single page app to fetch and display weather conditions using users location
        use  https://www.wunderground.com/weather/api/d/docs?d=data/conditions

        condUrl = "http://api.wunderground.com/api/90645b02d360fe14/conditions/q/" + state + "/" + city + ".json";

     */
}
