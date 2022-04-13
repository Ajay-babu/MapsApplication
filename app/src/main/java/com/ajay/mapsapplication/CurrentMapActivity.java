package com.ajay.mapsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;

import com.ajay.mapsapplication.databinding.ActivityCurrentMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

public class CurrentMapActivity extends AppCompatActivity implements OnMapReadyCallback {
private ActivityCurrentMapBinding binding1;
SupportMapFragment smf;
FusedLocationProviderClient client;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding1=ActivityCurrentMapBinding.inflate(getLayoutInflater());
        setContentView(binding1.getRoot());

        smf=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.current_map);
        client= LocationServices.getFusedLocationProviderClient(this);


        //TODO: Check permission
        if (ActivityCompat.checkSelfPermission(CurrentMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //TODO: When permission granted, call method
            getCurrentLocation();
        } else {
            //TODO: When permission denied get request for permission
            ActivityCompat.requestPermissions(CurrentMapActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }
    public void getCurrentLocation() {
        //TODO: Initialize task location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(location -> {
            //TODO: When success
            if (location != null) {

                //TODO: Sync MAp & also see the use of lambda in small brackets
                smf.getMapAsync(googleMap -> {
                    mMap = googleMap;
                    //TODO: Initialize latitude and longitude
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    //TODO: Change map type from here also
//                    onCreateOptionsMenu(menu);
                    googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    //TODO: Create marker options
                    MarkerOptions options = new MarkerOptions().position(latLng).title("You are here");

                    //TODO: Zoom Map
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    googleMap.addMarker(options);



                });
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //TODO: When permission granted then call method
                getCurrentLocation();
            }
        }
    }





    @Override
    public void onMapReady(@NonNull GoogleMap googleMap ) {
//       getCurrentLocation();
//       mMap = googleMap;
//       Location location = null;
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        //TODO: Change map type from here also
//        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        //TODO: Create marker options
//        MarkerOptions options = new MarkerOptions().position(latLng).title("You are here");
    }
}





