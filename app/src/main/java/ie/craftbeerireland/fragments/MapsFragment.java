package ie.craftbeerireland.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import ie.craftbeerireland.R;
import ie.craftbeerireland.main.CraftBeerIreland;
import ie.craftbeerireland.models.Beer;
import ie.craftbeerireland.models.CraftBeer;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MapsFragment extends SupportMapFragment implements
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    //private List<CraftBeer> mBeerList;
    private long                        UPDATE_INTERVAL = 5000; /* 5 secs */
    private long                        FASTEST_INTERVAL = 1000; /* 1 sec */
    private GoogleMap                   mMap;
    private float                       zoom = 13f;

    public CraftBeerIreland app = CraftBeerIreland.getInstance();

    private static final int            PERMISSION_REQUEST_CODE = 200;

    private final int[]                 MAP_TYPES = {
            GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE
    };

    private int                         curMapTypeIndex = 1;

    public MapsFragment() {
        // Required empty public constructor
    }

//    public void addBeers(List<CraftBeer> list){
//        for(CraftBeer c : list)
//            mMap.addMarker(new MarkerOptions()
//                    .position(new LatLng(c.marker.coords.latitude, c.marker.coords.longitude))
//                    .title(c.beerName + " €" + c.price)
//                    .snippet(c.craftBar + " " + c.address)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.beer)));
//    }

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
            createLocationCallback();
            createLocationRequest();
        }
        catch(SecurityException se) {
            Toast.makeText(getActivity(),"Check Your Permissions",Toast.LENGTH_SHORT).show();
        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    /* Creates a callback for receiving location events.*/
    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                app.mCurrentLocation = locationResult.getLastLocation();
                initCamera(app.mCurrentLocation);
            }
        };
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        getActivity().setTitle(R.string.title_map);
    }

    public void initListeners() {
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMapAsync(this);
        if (checkPermission()) {
            if (app.mCurrentLocation != null) {
                Toast.makeText(getActivity(), "GPS location was found!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Current location was null, Setting Default Values!", Toast.LENGTH_SHORT).show();
                app.mCurrentLocation = new Location("Waterford City Default (WIT)");
                app.mCurrentLocation.setLatitude(52.2462);
                app.mCurrentLocation.setLongitude(-7.1202);
            }
            if(mMap != null) {
                initCamera(app.mCurrentLocation);
                mMap.setMyLocationEnabled(true);
            }
            startLocationUpdates();
        }
        else if (!checkPermission()) {
            requestPermission();
        }
    }

    private void initCamera(Location location) {
        if (zoom != 13f && zoom != mMap.getCameraPosition().zoom)
            zoom = mMap.getCameraPosition().zoom;

        CameraPosition position = CameraPosition.builder()
                .target(new LatLng(location.getLatitude(),
                        location.getLongitude()))
                .zoom(zoom).bearing(0.0f)
                .tilt(0.0f).build();

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);
    }

    public void startLocationUpdates() {
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, Looper.myLooper());
        }
        catch(SecurityException se) {
            Toast.makeText(getActivity(),"Check Your Permissions on Location Updates",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(MAP_TYPES[curMapTypeIndex]);

        initListeners();
        if(checkPermission()) {
            mMap.setMyLocationEnabled(true);
            initCamera(app.mCurrentLocation);
        }
        else if (!checkPermission()) {
            requestPermission();
        }
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    //http://www.journaldev.com/10409/android-handling-runtime-permissions-example
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted) {
                        Snackbar.make(getView(), "Permission Granted, Now you can access location data.",
                                Snackbar.LENGTH_LONG).show();
                        if(checkPermission())
                            mMap.setMyLocationEnabled(true);
                        startLocationUpdates();
                    }
                    else {

                        Snackbar.make(getView(), "Permission Denied, You cannot access location data.",
                                Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
