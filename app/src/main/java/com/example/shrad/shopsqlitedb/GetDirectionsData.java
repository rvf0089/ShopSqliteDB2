package com.example.shrad.shopsqlitedb;

import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;


import java.io.IOException;

/**
 * Created by shrad on 23/09/2017.
 */

public class GetDirectionsData extends AsyncTask<Object,String,String> {

    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    String duration,distance;
    LatLng latLng;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url=(String) objects[1];
        latLng= (LatLng) objects[2];

        DownloardUrl downloardUrl = new DownloardUrl();
        try {
            googleDirectionsData = downloardUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleDirectionsData;

    }

    @Override
    protected void onPostExecute(String s) {
        String[] directionlist = null;
        DataParser parser =  new DataParser();
        directionlist = parser.parseDirection(s);
        displayDirection(directionlist);

    }

    public void displayDirection(String[] directionsList)
    {

        int count = directionsList.length;
        for(int i = 0;i<count;i++)
        {
            PolylineOptions options = new PolylineOptions();
            options.color(Color.RED);
            options.width(10);
//            options.addAll(PolyUtil.decode(directionsList[i]));

            mMap.addPolyline(options);
        }
    }

}
