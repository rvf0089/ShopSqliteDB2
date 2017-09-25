package com.example.shrad.shopsqlitedb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shrad on 22/09/2017.
 */

public class DataParser {

    private HashMap<String,String> getPlaces(JSONObject googlePlaceJson){
        HashMap<String,String> googlePlaceMap = new HashMap<>();
        String placeName = "--NA--";
        String vicinity = "--NA--";
        String latitude="";
        String longitude="";
        String reference ="";


            try {
                if(!googlePlaceJson.isNull("name")){
                    placeName =googlePlaceJson.getString("name");
                }
                if(!googlePlaceJson.isNull("vicinity")){
                    vicinity = googlePlaceJson.getString("vicinity");
                }
                latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
                longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");

                reference = googlePlaceJson.getString("reference");

                googlePlaceMap.put("place_name",placeName);
                googlePlaceMap.put("vicinity",vicinity);
                googlePlaceMap.put("lat",latitude);
                googlePlaceMap.put("lng",longitude);
                googlePlaceMap.put("reference",reference);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        return googlePlaceMap;
    }

    private List<HashMap<String,String>> getPlaces(JSONArray jsonArray){
        int count=jsonArray.length();
        List<HashMap<String,String>> placeslist =  new ArrayList<>();
        HashMap<String,String> placeMap = null;

        for(int i=0;i<count;i++){
            try {
                placeMap = getPlaces((JSONObject)jsonArray.get(i));
                placeslist.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placeslist;
    }

    public List<HashMap<String,String>> parse(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
             jsonObject=  new JSONObject(jsonData);
            jsonArray =  jsonObject.getJSONArray("results");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }

    public String[] parseDirection(String jsonData){

        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            jsonObject=  new JSONObject(jsonData);
            jsonArray =  jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  getPaths(jsonArray);

    }

    private HashMap<String,String> getDuration(JSONArray googleDirectionsJson){
        HashMap<String,String> googleDirectionMap = new HashMap<>();
        String duration;
        String distance;

        try{
            duration =  googleDirectionsJson.getJSONObject(0).optJSONObject("duration").getString("Text");
            distance =  googleDirectionsJson.getJSONObject(0).optJSONObject("distance").getString("Text");

            googleDirectionMap.put("duration",duration);
            googleDirectionMap.put("distance",distance);

        }catch(JSONException e){
            e.printStackTrace();
        }
        return googleDirectionMap;
    }

    public String[] getPaths(JSONArray googleStepsJson )
    {
        int count = googleStepsJson.length();
        String[] polylines = new String[count];

        for(int i = 0;i<count;i++)
        {
            try {
                polylines[i] = getPath(googleStepsJson.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return polylines;
    }

    public String getPath(JSONObject googlePathJson)
    {
        String polyline = "";
        try {
            polyline = googlePathJson.getJSONObject("polyline").getString("points");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return polyline;
    }

}
