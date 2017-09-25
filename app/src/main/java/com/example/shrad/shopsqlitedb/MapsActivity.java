package com.example.shrad.shopsqlitedb;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        LocationListener{

    private  int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;
    double endlatitude, endlongitude;

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentLocationMarker;
    public static final int REQUEST_PERMISSION_CODE=99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            checkPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       switch (requestCode){
           case REQUEST_PERMISSION_CODE:
               if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   //permission granted
                   if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                       if(googleApiClient==null){
                           buildGoogleApiClient();
                       }
                       mMap.setMyLocationEnabled(true);
                   }
               } else{
                   Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show();
               }
               return;
       }
    }

    public void onClick(View v){
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData =  new GetNearbyPlacesData();
        switch (v.getId()){
            case R.id.bDirection:

                TextView textView_address = (TextView) findViewById(R.id.text1);
                textView_address.setText(Information.getAddresss());
                String locationAddress = textView_address.getText().toString();
                List<Address> addressList = null;
                MarkerOptions markerOptions = new MarkerOptions();

                if (!locationAddress.equals("")) {
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        addressList = geocoder.getFromLocationName(locationAddress, 5);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < addressList.size(); i++) {
                        Address myAddress = addressList.get(i);
                        LatLng latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
                        markerOptions.position(latLng);
                        mMap.addMarker(markerOptions);
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                }
            break;
            case R.id.bRestaurant:
                mMap.clear();
                String restaurant="restuarant";
                String url = getUrl(latitude,longitude,restaurant);
                dataTransfer = new Object[2];
                dataTransfer[0]=mMap;
                dataTransfer[1]=url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this,"Showing Nearby Restaurants",Toast.LENGTH_LONG).show();
                break;
            case R.id.bCafes:
                mMap.clear();
                String cafes="school";
                url = getUrl(latitude,longitude,cafes);
                dataTransfer = new Object[2];
                dataTransfer[0]=mMap;
                dataTransfer[1]=url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this,"Showing Nearby Cafes",Toast.LENGTH_LONG).show();
                break;
            case R.id.bGetDirections:
                dataTransfer = new Object[2];
                url=getDirectionUrl();
                GetDirectionsData getDirectionsData = new GetDirectionsData();
                dataTransfer[0]=mMap;
                dataTransfer[1]=url;
                dataTransfer[2]= new LatLng(endlatitude,endlongitude);
                getDirectionsData.execute(dataTransfer);
                break;



        }

    }

    private  String getDirectionUrl(){
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googlePlaceUrl.append("origin="+latitude+","+longitude);
        googlePlaceUrl.append("&destination="+endlatitude+","+endlongitude);
        googlePlaceUrl.append("&key="+"AIzaSyD_U5LaJ_z8ce4h_OaEZRnxJQ_SQ368SIw");

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }
    private String getUrl(Double latitude, Double longitude, String nearbyPlace){
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location"+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyB1oHp05ZYnpmUxDxkVaMHEVbdINOgMmWI");

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

        }

    }

    protected synchronized void buildGoogleApiClient(){
        googleApiClient =  new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastlocation = location;

        if(currentLocationMarker!=null){
            currentLocationMarker.remove();
        }

        Log.d("lat: ",""+latitude);
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

        MarkerOptions markeroptions = new MarkerOptions();
        markeroptions.position(latLng);
        markeroptions.title("Current Location");
        markeroptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        currentLocationMarker=mMap.addMarker(markeroptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if(googleApiClient!=null){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest =  new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    public boolean checkPermission(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION_CODE);
            }
            else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION_CODE);
            }
            return false;
        }
        else
          return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        endlatitude = marker.getPosition().latitude;
        endlongitude =  marker.getPosition().longitude;

        Log.d("end_lat",""+endlatitude);
        Log.d("end_lng",""+endlongitude);
    }
}
