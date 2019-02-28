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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import ie.craftbeerireland.R;
import ie.craftbeerireland.activities.Home;
import ie.craftbeerireland.main.CraftBeerIreland;
import ie.craftbeerireland.models.CraftBeer;

public class EditFragment extends Fragment {

    public boolean isFavourite;
    public CraftBeer cBeer;
    public ImageView editFavourite;
    private EditText name, bar, price;
    private RatingBar ratingBar;
    public CraftBeerIreland app;

    private OnFragmentInteractionListener mListener;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(Bundle beerBundle) {
        EditFragment fragment = new EditFragment();
        fragment.setArguments(beerBundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        app = (CraftBeerIreland) getActivity().getApplication();

        if(getArguments() != null)
            cBeer = getCraftBeerObject(getArguments().getString("beerId"));
    }

    private CraftBeer getCraftBeerObject(String id) {

        for (CraftBeer c : app.beerList)
            if (c.beerId.equalsIgnoreCase(id))
                return c;

        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit, container, false);

        ((TextView)v.findViewById(R.id.editTitleTV)).setText(cBeer.beerName);

        name = v.findViewById(R.id.editNameET);
        bar = v.findViewById(R.id.editBarET);
        price = v.findViewById(R.id.editPriceET);
        ratingBar = v.findViewById(R.id.editRatingBar);
        editFavourite = v.findViewById(R.id.editFavourite);

        name.setText(cBeer.beerName);
        bar.setText(cBeer.craftBar);
        price.setText(""+cBeer.price);
        ratingBar.setRating((float)cBeer.rating);

        if (cBeer.favourite == true) {
            editFavourite.setImageResource(R.drawable.tumbs_on);
            isFavourite = true;
        } else {
            editFavourite.setImageResource(R.drawable.tumbs_neu);
            isFavourite = false;
        }
        return v;
    }

    public void saveCraftBeer(View v) {

        if (mListener != null) {
            String beerName = name.getText().toString();
            String craftBar = bar.getText().toString();
            String beerPriceStr = price.getText().toString();
            double ratingValue = ratingBar.getRating();
            double beerPrice;

            try {
                beerPrice = Double.parseDouble(beerPriceStr);
            } catch (NumberFormatException e) {
                beerPrice = 0.0;
            }

            if ((beerName.length() > 0) && (craftBar.length() > 0) && (beerPriceStr.length() > 0)) {
                cBeer.beerName = beerName;
                cBeer.craftBar = craftBar;
                cBeer.price = beerPrice;
                cBeer.rating = ratingValue;

                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                    return;
                }


            } else
                Toast.makeText(getActivity(), "You must Enter Something for Name and Bar", Toast.LENGTH_SHORT).show();
        }
    }

    public void toggle(View v) {

        if (isFavourite) {
            cBeer.favourite = false;
            Toast.makeText(getActivity(), "Removed From Favourites", Toast.LENGTH_SHORT).show();
            isFavourite = false;
            editFavourite.setImageResource(R.drawable.tumbs_neu);
        } else {
            cBeer.favourite = true;
            Toast.makeText(getActivity(), "Added to Favourites !!", Toast.LENGTH_SHORT).show();
            isFavourite = true;
            editFavourite.setImageResource(R.drawable.tumbs_on);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void toggle(View v);
        void saveCraftBeer(View v);
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
