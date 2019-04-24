package ie.craftbeerireland.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.UUID;

import ie.craftbeerireland.R;
import ie.craftbeerireland.activities.Home;
import ie.craftbeerireland.main.CraftBeerIreland;
import ie.craftbeerireland.models.CraftBeer;

public class AddFragment extends Fragment {

    String uniqueId = null;
    private String 		beerName, craftBar;
    private double 		beerPrice, ratingValue;
    private EditText name, craftbar, price;
    private RatingBar ratingBar;
    private ImageButton saveButton;
    private CraftBeerIreland app;
    private DatabaseReference mDatabase;
    private GoogleMap mMap;
    private MapsFragment mapFragment;


    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();

        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (CraftBeerIreland) getActivity().getApplication();
        mDatabase = FirebaseDatabase.getInstance().getReference("Database");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add, container, false);
        mapFragment = (MapsFragment) getChildFragmentManager().findFragmentById(R.id.addmap);

        if (mapFragment != null) {
            mapFragment.isAddBeer = true;
        }

        getActivity().setTitle(R.string.addABeerLbl);
        name = v.findViewById(R.id.addNameET);
        craftbar =  v.findViewById(R.id.addPubET);
        price =  v.findViewById(R.id.addPriceET);
        ratingBar =  v.findViewById(R.id.addRatingBar);
        saveButton = v.findViewById(R.id.addBeerBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBeer();

            }
        });

        return v;
    }

    public void addBeer() {

        beerName = name.getText().toString();

        craftBar = craftbar.getText().toString();
        try {
            beerPrice = Double.parseDouble(price.getText().toString());
        } catch (NumberFormatException e) {
            beerPrice = 0.0;
        }
        ratingValue = ratingBar.getRating();

        if ((beerName.length() > 0) && (craftBar.length() > 0)
                && (price.length() > 0)) {
            String beerID = UUID.randomUUID().toString();
            CraftBeer c = new CraftBeer(beerName, craftBar, ratingValue,
                    beerPrice, false,app.googlePhotoURL,
                    app.googleToken,getAddressFromLocation(app.mCurrentLocation),
                    mapFragment.beerLocation.latitude,mapFragment.beerLocation.longitude, beerID);
            DatabaseReference cineIndustryRef = mDatabase.child(app.user.getUid());
            cineIndustryRef.child("beers").child(beerID).setValue(c);
            cineIndustryRef.push();

            startActivity(new Intent(this.getActivity(), Home.class));
        } else
            Toast.makeText(
                    this.getActivity(),
                    "You must Enter Something for "
                            + "\'Name\', \'Bar\' and \'Price\'",
                    Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private String getAddressFromLocation( Location location ) {
        Geocoder geocoder = new Geocoder( getActivity() );

        String strAddress = "";
        Address address;
        try {
            address = geocoder
                    .getFromLocation( location.getLatitude(), location.getLongitude(), 1 )
                    .get( 0 );
            strAddress = address.getAddressLine(0) +
                    " " + address.getAddressLine(1) +
                    " " + address.getAddressLine(2);
        }
        catch (IOException e ) {
        }

        return strAddress;
    }

}