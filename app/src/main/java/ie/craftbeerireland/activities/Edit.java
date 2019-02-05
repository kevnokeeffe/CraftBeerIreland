package ie.craftbeerireland.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import ie.craftbeerireland.R;
import ie.craftbeerireland.models.CraftBeer;

public class Edit extends Base {
    public Context context;
    public boolean isFavourite;
    public CraftBeer cBeer;
    public ImageView editFavourite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        context = this;
        activityInfo = getIntent().getExtras();
        cBeer = getBeerObject(activityInfo.getString("beerId"));



        ((TextView)findViewById(R.id.editTitleTV)).setText(cBeer.beerName);

        ((EditText)findViewById(R.id.editNameET)).setText(cBeer.beerName);
        ((TextView)findViewById(R.id.editBarET)).setText(cBeer.craftBar);
        ((EditText)findViewById(R.id.editPriceET)).setText(""+cBeer.price);
        ((RatingBar) findViewById(R.id.editRatingBar)).setRating((float)cBeer.rating);

        editFavourite = findViewById(R.id.editFavourite);

        if (cBeer.favourite == true) {
            editFavourite.setImageResource(R.drawable.favourites_72_on);
            isFavourite = true;
        } else {
            editFavourite.setImageResource(R.drawable.favourites_72);
            isFavourite = false;
        }
    }

    private CraftBeer getBeerObject(String id) {

        for (CraftBeer c : beerList)
            if (c.beerId.equalsIgnoreCase(id))
                return c;

        return null;
    }

//    private int getCoffeeIndex(Coffee obj) {
//
//        for (Coffee c : coffeeList)
//            if (c.coffeeId == obj.coffeeId)
//                return coffeeList.indexOf(c);
//
//        return -1;
//    }

    public void saveCraftBeer(View v) {

        String beerName = ((EditText) findViewById(R.id.editNameET)).getText().toString();
        String cbBar = ((EditText) findViewById(R.id.editBarET)).getText().toString();
        String beerPriceStr = ((EditText) findViewById(R.id.editPriceET)).getText().toString();
        double ratingValue =((RatingBar) findViewById(R.id.editRatingBar)).getRating();
        double beerPrice;

        try {
            beerPrice = Double.parseDouble(beerPriceStr);
        } catch (NumberFormatException e) {
            beerPrice = 0.0;
        }

        if ((beerName.length() > 0) && (cbBar.length() > 0) && (beerPriceStr.length() > 0)) {
            cBeer.beerName = beerName;
            cBeer.craftBar = cbBar;
            cBeer.price = beerPrice;
            cBeer.rating = ratingValue;

            startActivity(new Intent(this,Home.class));

        } else
            Toast.makeText(this, "You must Enter Something for Name and Bar",Toast.LENGTH_SHORT).show();
    }

    public void toggle(View view) {

        if (isFavourite) {
            cBeer.favourite = false;
            Toast.makeText(this,"Removed From Favourites",Toast.LENGTH_SHORT).show();
            isFavourite = false;
            editFavourite.setImageResource(R.drawable.favourites_72);
        } else {
            cBeer.favourite = true;
            Toast.makeText(this,"Added to Favourites !!",Toast.LENGTH_SHORT).show();
            isFavourite = true;
            editFavourite.setImageResource(R.drawable.favourites_72_on);
        }
    }
}