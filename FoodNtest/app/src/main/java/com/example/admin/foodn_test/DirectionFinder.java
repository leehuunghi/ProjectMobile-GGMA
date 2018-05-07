package com.example.admin.foodn_test;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

class DirectionFinder {
        private static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?";
        private DirectionFinderListener listener;
        private String origin;
        private String destination;
        Position position1 = new Position();
        Position position2 = new Position();


    public DirectionFinder(DirectionFinderListener listener, String origin, String destination) {
            this.listener = listener;
            this.origin = origin;
            this.destination = destination;
        }

        public void execute() throws UnsupportedEncodingException {
            listener.onDirectionFinderStart();
            new DownloadRawData().execute(createUrl());
        }

        private String createUrl() throws UnsupportedEncodingException {
            String urlOrigin = URLEncoder.encode(origin, "utf-8");
            String urlDestination = URLEncoder.encode(destination, "utf-8");

            new DownloadRawData1().execute(createUrlOrigin());
            new DownloadRawData2().execute(createUrlDes());


            return DIRECTION_URL_API  +"origin=" + position1.lat +","+position1.lng+ "&destination=" + position2.lat+","+position2.lng + "&sensor=true" + "&mode=driving";
        }

        private String createUrlOrigin() throws UnsupportedEncodingException {
        String urlOrigin = URLEncoder.encode(origin, "utf-8");

        return "http://maps.googleapis.com/maps/api/geocode/json?address="+urlOrigin+"&sensor=false";
        }

        @NonNull
        private String createUrlDes() throws UnsupportedEncodingException {
        String urlDestination = URLEncoder.encode(destination, "utf-8");
        String save="http://maps.googleapis.com/maps/api/geocode/json?address="+urlDestination+"&sensor=false";
        return save;
        }


    private class DownloadRawData extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String link = params[0];
                try {
                    URL url = new URL(link);
                    InputStream is = url.openConnection().getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }

                    return buffer.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String res) {
                try {
                    parseJSon(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }


    private class DownloadRawData1 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            try {
                URL url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            try {
                    parseJSon1(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private class DownloadRawData2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            try {
                URL url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            try {
                parseJSon2(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

        private void parseJSon(String data) throws JSONException {
            if (data == null)
                return;

            List<Route> routes = new ArrayList<Route>();
            JSONObject jsonData = new JSONObject(data);


            JSONArray jsonRoutes = jsonData.getJSONArray("routes");
            for (int i = 0; i < jsonRoutes.length(); i++) {
                JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
                Route route = new Route();

                JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
                JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
                JSONObject jsonLeg = jsonLegs.getJSONObject(0);
                JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
                JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
                JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
                JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");

                route.distance = new Distance(jsonDistance.getString("text"), jsonDistance.getInt("value"));
                route.duration = new Duration(jsonDuration.getString("text"), jsonDuration.getInt("value"));
                route.endAddress = jsonLeg.getString("end_address");
                route.startAddress = jsonLeg.getString("start_address");
                route.startLocation = new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
                route.endLocation = new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"));
                route.points = decodePolyLine(overview_polylineJson.getString("points"));

                routes.add(route);
            }

            listener.onDirectionFinderSuccess(routes);
        }

    private Position parseJSon1(String data) throws JSONException {
        if (data == null)
            return null;

        JSONObject jsonData = new JSONObject(data);
        JSONArray jsResult=jsonData.getJSONArray("results");

            JSONObject jsonRes = jsResult.getJSONObject(2);

            JSONArray jsGeo = jsonRes.getJSONArray("geometry");
            JSONObject jsLocation = jsGeo.getJSONObject(0);

            position1.lat = jsLocation.getDouble("lat");
            position1.lng = jsLocation.getDouble("lng");

        return position1;
    }

    private Position parseJSon2(String data) throws JSONException {
        if (data == null)
            return null;

        JSONObject jsonData = new JSONObject(data);
        JSONArray jsResult=jsonData.getJSONArray("results");
        for (int i=0;i<jsResult.length();i++) {
            JSONObject jsonRes = jsResult.getJSONObject(i);

            JSONArray jsGeo = jsonRes.getJSONArray("geometry");
            JSONObject jsLocation = jsGeo.getJSONObject(0);

            position2.lat = jsLocation.getDouble("lat");
            position2.lng = jsLocation.getDouble("lng");
        }
        return position2;

    }


    private List<LatLng> decodePolyLine(final String poly) {
            int len = poly.length();
            int index = 0;
            List<LatLng> decoded = new ArrayList<LatLng>();
            int lat = 0;
            int lng = 0;

            while (index < len) {
                int b;
                int shift = 0;
                int result = 0;
                do {
                    b = poly.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = poly.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                decoded.add(new LatLng(
                        lat / 100000d, lng / 100000d
                ));
            }
            return decoded;
        }
    }

