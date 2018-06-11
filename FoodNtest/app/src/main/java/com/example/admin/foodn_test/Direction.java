package com.example.admin.foodn_test;

import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Windows 10 on 09/06/2018.
 */

public class Direction {
    private LatLng origin;
    private LatLng dest;
    public int distance;
    private String duration;
    private PolylineOptions polylineOptions;

    TaskRequestDirections taskRequestDirections;
    public LatLng getOrigin() {
        return origin;
    }

    public LatLng getDest() {
        return dest;
    }

    public int getDistance() {
        return distance;
    }

    public String getDuration() {
        return duration;
    }

    public PolylineOptions getPolylineOptions() {
        return polylineOptions;
    }

    public Direction(){
        distance=-1;

    };

    public Direction(LatLng a, LatLng b){
        distance=-1;
        this.origin = a;
        this.dest = b;
        taskRequestDirections = new TaskRequestDirections();
    };


    public String getRequestUrl() {
        //Value of origin
        String str_org = "origin=" + origin.latitude +","+origin.longitude;
        //Value of destination
        String str_dest = "destination=" + dest.latitude+","+dest.longitude;
        //Set value enable the sensor
        String sensor = "sensor=false";
        //Mode for find direction
        String mode = "mode=driving";
        //Build the full param
        String param = str_org +"&" + str_dest + "&" +sensor+"&" +mode;
        //Output format
        String output = "json";
        //Create url to request
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param;
        return url;
    }

    public class TaskRequestDirections extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Parse json here
            TaskParser taskParser = new TaskParser();
            taskParser.execute(s);
        }

        private String requestDirection(String reqUrl) throws IOException {
            String responseString = "";
            InputStream inputStream = null;
            HttpURLConnection httpURLConnection = null;
            try{
                URL url = new URL(reqUrl);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                //Get the response result
                inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }

                responseString = stringBuffer.toString();
                bufferedReader.close();
                inputStreamReader.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                httpURLConnection.disconnect();
            }
            return responseString;
        }
    }

    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>> > {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jsonObject);
                distance = directionsParser.distance;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            //Get list route and display it into the map

            ArrayList points = null;


            for (List<HashMap<String, String>> path : lists) {
                points = new ArrayList();
                polylineOptions = new PolylineOptions();

                for (HashMap<String, String> point : path) {
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));

                    points.add(new LatLng(lat,lon));
                }

                polylineOptions.addAll(points);
                polylineOptions.width(8);
                polylineOptions.color(Color.rgb(66, 132, 243));
                polylineOptions.geodesic(true);
            }

            GlobalVariable.mMap.addPolyline(polylineOptions);
        }
    }
}
