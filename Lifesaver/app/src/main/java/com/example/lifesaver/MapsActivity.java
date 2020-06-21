package com.example.lifesaver;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private FusedLocationProviderClient fusedLocationClient;
    private Double latitude, longitude;

    SharedPreferences prefs;

    Map<String, Map<String, Double>> mapList, allList;

    public static double distance(double lat1,
                                  double lat2, double lon1,
                                  double lon2) {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return (c * r);
    }

    private void showmarkers() {

        for(String key : mapList.keySet()) {
            LatLng hospital = new LatLng(mapList.get(key).get("Latitude"), mapList.get(key).get("Longitude"));

            mMap.addMarker(new MarkerOptions().position(hospital).title("Hospital marker"));

        }

        float zoomLevel = 15.0f;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoomLevel));


    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            shouldShowRequestPermissionRationale("The app uses your current location to find hospitals near you. Allow the app to use this feature to find available hospitals near you.");

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            mMap.setMyLocationEnabled(true);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object

                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("latitude", String.valueOf(location.getLatitude()));
                                editor.putString("longitude", String.valueOf(location.getLongitude()));
                                editor.apply();
                                Log.i("TAG", "onSuccess:############################ " + location.getLatitude() + " " + location.getLongitude());
                            }
                        }
                    });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(MapsActivity.this, MainActivity.class));
            } else {
                getLocation();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mapList = new HashMap<>();
        allList = new HashMap<>();


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocation();
        // Add a marker in Sydney and move the camera

        new Handler().postDelayed(() -> {

            LatLng sydney = new LatLng(latitude, longitude);
            float zoomLevel = 16.0f; //This goes up to 21

            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));

            //add find hospital code here

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Location");
            ref.orderByChild("Latitude").startAt(latitude - 0.009009).endAt(latitude + 0.009009).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.i("TAG", "onDataChange: " + snapshot.getValue());

                    Map<String, Map<String, Double>> map = (HashMap)snapshot.getValue();

                    for(String key : map.keySet()) {
                        Double lat2 = map.get(key).get("Latitude");
                        Double lon2 = map.get(key).get("Longitude");
                        if (distance(latitude, longitude, lat2, lon2) < 1) {

                            Map<String, Double> subMap = new HashMap<>();
                            subMap.put("Latitude", lat2);
                            subMap.put("Longitude", lon2);
                            allList.put(key, subMap);
                            mapList.put(key, subMap);

                        }

                    }

                    showmarkers();
                }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }, 2000);


    }
}