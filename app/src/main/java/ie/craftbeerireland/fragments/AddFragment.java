package ie.craftbeerireland.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

import ie.craftbeerireland.R;
import ie.craftbeerireland.activities.Home;
import ie.craftbeerireland.main.CraftBeerIreland;
import ie.craftbeerireland.models.CraftBeer;

public class AddFragment extends Fragment {



    private String 		beerName, craftBar;
    private double 		beerPrice, ratingValue;
    private EditText name, craftbar, price;
    private RatingBar ratingBar;
    private ImageButton saveButton;
    private CraftBeerIreland app;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add, container, false);

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
            CraftBeer c = new CraftBeer(beerName, craftBar, ratingValue,
                    beerPrice, false);

            app.beerList.add(c);
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


}