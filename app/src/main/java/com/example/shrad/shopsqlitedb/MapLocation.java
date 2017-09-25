package com.example.shrad.shopsqlitedb;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * This class basically shows the current mobile location address to the user if the permission is granted.
 * The address showed on the screen would be used then to show the cafes, restaurant based upo on that address picker.
 */
public class MapLocation extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Geocoder geocoder;
    private List<Address> addressList;

    /**The onCreate method basically update the textview with current mobile location & its using Location Manager
       class to get the location co-ordinates which are then converted to an address using Geocoder class.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_location);button = (Button) findViewById(R.id.button);
 
        textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openMapAcitivity();
            }
        });
         locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
         geocoder = new Geocoder(this, Locale.getDefault());

        /**
         * locationListener would update the textview with the current mobile location address whenever called.
         */
        locationListener = new LocationListener() {
             @Override
             public void onLocationChanged(Location location) {
                 try {
                     addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                     String address = addressList.get(0).getAddressLine(0);
                     String area = addressList.get(0).getLocality();
                     String full_address =address + ", "+area;
                     textView.append("\n "+full_address+"\n");
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
 
             @Override
             public void onStatusChanged(String s, int i, Bundle bundle) {
 
             }
 
             @Override
             public void onProviderEnabled(String s) {
 
             }
 
             @Override
             public void onProviderDisabled(String s) {
                 Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                 startActivity(intent);
 
             }
         };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
         if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                 requestPermissions(new String[]{
                         Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
                         Manifest.permission.INTERNET
                 },10);
             }
             return;
         }else {
            configureButton();
         }
    }

    /**
     * This method is used to check if the app has the permission enabled or not, if it is
     * then the permission result is  the configureButton would be called.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }

    /**
     * The configureButton() is used basically to add the listener to the button so that when its pressed,
     * it would ask for the location updates with the use of LocationManager object created in the onCreated method.
     * The parameters set for requestLocationUpdates are set to the given below because the provider(first paramter)
     * the app would use is the gps, the second parameter is the time the location would be updated so have set to
     * 5 sec(5000millsec) to show location address if same for every 5 second
     * , the third parameter is the location distance would be updated after every 5 meters distance, the last
     * is the listener which we have already created in the onCreate method
     *
     */
    public void configureButton(){
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                locationManager.requestLocationUpdates("gps", 5000, 5, locationListener);
            }
        });

    }

    /**
     *  This method opens the map activity when pressed to get directions to the selected place.
     */
    public void openMapAcitivity(){
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }

}
