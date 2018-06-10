package com.example.admin.foodn_test;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;

//import com.google.android.gms.location.places.ui.PlacePicker;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.adapter.RecyclerViewAdapter;
import com.example.admin.config.Configuaration;
import com.example.admin.model.Position;
import com.example.admin.model.Store;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;
import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.SSLPeerUnverifiedException;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback, LocationSource.OnLocationChangedListener, RecyclerViewAdapter.ItemClickListener, RecyclerViewAdapter.ClickButtonChiDuong, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    View mapView;
    ProgressDialog progressDialog;
    RecyclerViewAdapter adapterRecycler;
    ArrayList<Store> listStore = new ArrayList<>();

    ArrayList<LatLng> listPoints;
    LatLng currentLatLng;

    Spinner spinner;
    TextView txtSpinner;
//    ImageButton imgDropDown;
    ImageButton imgSearch;
    EditText txtSearch;

    RelativeLayout relativeLay;
    RelativeLayout relativeLayoutFind;

    EditText txtStart;
    TextView txtEnd;
//    ImageButton imgFindRoute;
    ImageButton imgSearchBack;
    RecyclerView recyclerView;

    private ImageView imgMyLocation;
    private DrawerLayout mDrawerLayout;

    Location locationCurrent=null;
    Location locationDes=null;

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    static final String TAG = "HomeActivity";

    static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    static final String WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    static final float DEFAULT_ZOOM = 15f;

    private static final int PLACE_PICKER_REQUEST = 1;

    //vars
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải... Vui lòng đợi!");
        progressDialog.setCanceledOnTouchOutside(false);
        listPoints = new ArrayList<>();

        if (progressDialog != null) {
            progressDialog.show();
        }

        getLocationPermission();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        }
        View headerview = navigationView.getHeaderView(0);
        TextView accName = (TextView) headerview.findViewById(R.id.accName);
        accName.setText("Tên tài khoản");
        ImageView accAva = headerview.findViewById(R.id.accAva);
        accAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(HomeActivity.this, InfoAccount.class);
                startActivity(a);
            }
        });
        accName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(HomeActivity.this, InfoAccount.class);
                startActivity(a);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);



        txtSpinner = findViewById(R.id.txtSpinner);
        txtSearch = findViewById(R.id.txtSearch);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setVisibility(View.VISIBLE);
        
        imgMyLocation = (ImageView) findViewById(R.id.imgMyLocation);

        txtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    imgMyLocation.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                }else{
                    imgMyLocation.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                }

            }
        });

        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //Clear focus here from edittext
                    txtSearch.clearFocus();
                }
                return false;
            }
        });

        imgMyLocation.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDeviceLocation();
        }
        });

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        spinner=(Spinner) findViewById(R.id.spinner);

//        imgDropDown=findViewById(R.id.imgDropDown);

        List<String> list = new ArrayList<>();
        list.add("Quán ăn");
        list.add("Món");
        list.add("Loại món");
        list.add("Địa chỉ");

        final ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spinner.setAdapter(adapter);
        spinner.setVisibility(View.INVISIBLE);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String temp = spinner.getSelectedItem().toString();
                txtSpinner.setText(temp);
                String temp2 = temp.toLowerCase();
                String hint = "Tìm kiếm theo " + temp2;
                txtSearch.setHint(hint);
//                txtSpinner.setSelection(txtSpinner.length());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        txtSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.performClick();
            }
        });

        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.INVISIBLE);
            }
        });

        imgSearch=findViewById(R.id.imgSearch);
        relativeLay= (RelativeLayout) findViewById(R.id.relativeLayout);

        relativeLayoutFind=findViewById(R.id.relativeLayoutFind);
        relativeLayoutFind.setVisibility(View.INVISIBLE);


        txtStart = findViewById(R.id.txtStart);
        txtEnd=findViewById(R.id.txtEnd);

//        imgFindRoute=findViewById(R.id.imgFindRoute);

        imgSearchBack=findViewById(R.id.imgSearchBack);

        imgSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayoutFind.setVisibility(View.INVISIBLE);
                relativeLay.setVisibility(View.VISIBLE);
                mMap.clear();
            }
        });

//        imgFindRoute.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imgFindRoute.setVisibility(View.INVISIBLE);
//                imgSearchBack.setVisibility(View.VISIBLE);
//                if (listPoints.size() > 0) {
//                    listPoints.clear();
//                    mMap.clear();
//                }
//                LatLng latLng1=new LatLng(locationCurrent.getLatitude(), locationCurrent.getLongitude());
//                //Save first point select
//                listPoints.add(latLng1);
//
//                //có địa điểm thì lưu vô locationDes rồi thay cho 2 số này
//                listPoints.add(currentLatLng);
//
//                //Create marker
//                MarkerOptions markerOptionsCurrent = new MarkerOptions();
//                markerOptionsCurrent.position(latLng1);
//
//                MarkerOptions markerOptionsDes = new MarkerOptions();
//                markerOptionsDes.position(currentLatLng);
//
//
//                if (listPoints.size() == 1) {
//                    //Add first marker to the map
//                    markerOptionsCurrent.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_marker));
//                } else {
//                    //Add second marker to the map
//                    markerOptionsCurrent.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_marker));
//                    markerOptionsDes.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                }
//                mMap.addMarker(markerOptionsCurrent);
//                mMap.addMarker(markerOptionsDes);
//
//                if (listPoints.size() == 2) {
//                    //Create the URL to get request from first marker to second marker
//                    String url = getRequestUrl(listPoints.get(0), listPoints.get(1));
//                    TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
//                    taskRequestDirections.execute(url);
//                    moveCamera(currentLatLng,14);
//                }
//            }
//        });

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.nav_TrangChu:
                Intent a = new Intent(this, HomeActivity.class);
                startActivity(a);
                break;
            case R.id.nav_YeuThich:
                Intent b = new Intent(this, FavoriteActivity.class);
                startActivity(b);
                break;
            case R.id.nav_TuyChinh:
                Intent c = new Intent(this, SettingUpdate.class);
                startActivity(c);
                break;
            case R.id.nav_DangXuat:
                Intent e = new Intent(this, LoginActivity.class);
                startActivity(e);
                this.finish();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed()
    {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.leave)
                .setPositiveButton("Có", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("Không", null)
                .show();
    }

    @Override
    public void onClickButtonChiDuong(RecyclerViewAdapter.ViewHolder view, int position) {
        relativeLayoutFind.setVisibility(View.VISIBLE);
//        imgFindRoute.setVisibility(View.VISIBLE);
        imgSearchBack.setVisibility(View.VISIBLE);
        relativeLay.setVisibility(View.INVISIBLE);
        currentLatLng = new LatLng(adapterRecycler.getItem(position).getListPoint().get(0).getV(), adapterRecycler.getItem(position).getListPoint().get(0).getV1());
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(currentLatLng).title(adapterRecycler.getItem(position).getTenCuaHang()).snippet(adapterRecycler.getItem(position).getDiaChi()));
        txtEnd.setText(adapterRecycler.getItem(position).getTenCuaHang().toString());
        moveCamera(currentLatLng,15);
                if (listPoints.size() > 0) {
                    listPoints.clear();
                    mMap.clear();
                }
                LatLng latLng1=new LatLng(locationCurrent.getLatitude(), locationCurrent.getLongitude());
                //Save first point select
                listPoints.add(latLng1);

                //có địa điểm thì lưu vô locationDes rồi thay cho 2 số này
                listPoints.add(currentLatLng);

                //Create marker
                MarkerOptions markerOptionsCurrent = new MarkerOptions();
                markerOptionsCurrent.position(latLng1);

                MarkerOptions markerOptionsDes = new MarkerOptions();
                markerOptionsDes.position(currentLatLng);


                if (listPoints.size() == 1) {
                    //Add first marker to the map
                    markerOptionsCurrent.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_marker));
                } else {
                    //Add second marker to the map
                    markerOptionsCurrent.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_marker));
                    markerOptionsDes.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
                mMap.addMarker(markerOptionsCurrent);
                mMap.addMarker(markerOptionsDes);

                if (listPoints.size() == 2) {
                    //Create the URL to get request from first marker to second marker
                    String url = getRequestUrl(listPoints.get(0), listPoints.get(1));
                    TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
                    taskRequestDirections.execute(url);
                    moveCamera(currentLatLng,14);
                }
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            //Get list route and display it into the map

            ArrayList points = null;

            PolylineOptions polylineOptions = null;

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

            if (polylineOptions!=null) {
                mMap.addPolyline(polylineOptions);
            } else {
                Toast.makeText(getApplicationContext(), "Direction not found!", Toast.LENGTH_SHORT).show();
            }

        }
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


    private String getRequestUrl(LatLng origin , LatLng dest) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });


        LocationManager mlocManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        boolean enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!enabled) {
                showDialogGPS();
            }

        enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (enabled==true) {
            getDeviceLocation();
        }

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(layoutManager);
        adapterRecycler = new RecyclerViewAdapter(this, listStore);
        adapterRecycler.setClickListener(this);
        adapterRecycler.setmClickButtonChiDuong(this);
        recyclerView.setAdapter(adapterRecycler);
        PositionBestNear positionBestNear = new PositionBestNear();
        positionBestNear.execute();

        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

        } else {
        }





        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 200, 100);
        }

    }

    private void showDialogGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Enable GPS");
        builder.setMessage("Please enable GPS");
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(
                        new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        Marker mMarker = mMap.addMarker(new MarkerOptions().position(loc));
        if (mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
        }
    }


    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        Task location = mFusedLocationProviderClient.getLastLocation();

                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            locationCurrent=currentLocation;
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);
                        } else {
                        }
                    }

                });
    }



    private void moveCamera(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            return;
                        }
                    }

                }
            }
        }
    }
    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("ID",adapterRecycler.getItem(position).getID());
        Intent intentDetail = new Intent(HomeActivity.this, StoreDetailActivity.class);
        intentDetail.putExtras(bundle);
        startActivity(intentDetail);
    }
    Position pos=null;
    String tenCuaHang, diaChiCuaHang;

    class PositionBestNear extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            for(int i=0; i<listStore.size();i++)
            {
                listStore.get(i).setMarker(mMap.addMarker(new MarkerOptions().position(new LatLng(listStore.get(i).getListPoint().get(0).getV(),listStore.get(i).getListPoint().get(0).getV1())).title(listStore.get(i).getTenCuaHang()).snippet(listStore.get(i).getDiaChi())));
            }
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                SoapObject request = new SoapObject(Configuaration.NAME_SPACE,Configuaration.METHOD_GET_LIST_POSITION);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransportSE= new HttpTransportSE(Configuaration.SERVER_URL);
                httpTransportSE.call(Configuaration.SOAP_ACTION_GET_LIST_POSITION, envelope);
                SoapObject listSoapObject= (SoapObject) envelope.getResponse();
                SoapObject so = null;
                for (int i = 0; i < listSoapObject.getPropertyCount(); i++) {
                    so = (SoapObject) listSoapObject.getProperty(i);
                    Store store = new Store();
                    pos= new Position(Double.parseDouble(so.getPropertyAsString("HoanhDo")),Double.parseDouble(so.getPropertyAsString("TungDo")));
                    store.getListPoint().add(pos);
                    so = (SoapObject) so.getProperty("CuaHang");
                    store.setID(Integer.parseInt(so.getPropertyAsString("ID_CuaHang")));
                    store.setTenCuaHang(so.getPropertyAsString("TenCuaHang"));
                    store.setDiaChi(so.getPropertyAsString("DiaChi"));
                    store.setHinhAnh(StringToBitMap(so.getPropertyAsString("HinhAnh")));
                    listStore.add(store);
                }
            }
            catch (Exception ex)
            {
                Log.e("Lỗi", ex.toString());
            }
            return null;
        }
    }

    private Bitmap StringToBitMap(String hinhAnh) {
        try {
            byte[] encodeByte = Base64.decode(hinhAnh);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}

