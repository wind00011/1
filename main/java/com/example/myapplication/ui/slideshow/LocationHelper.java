package com.example.myapplication.utils;

import android.content.Context;
import android.location.Location;
import android.os.Looper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationHelper {

    private FusedLocationProviderClient fusedLocationClient;

    public LocationHelper(Context context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public void getCurrentLocation(final LocationCallback callback) {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);  // 每10秒更新一次

        fusedLocationClient.requestLocationUpdates(locationRequest, callback, Looper.getMainLooper());
    }

    public void getLastLocation(OnSuccessListener<Location> onSuccessListener) {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(onSuccessListener);
    }
}
