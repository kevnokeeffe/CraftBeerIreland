package ie.craftbeerireland.activities;

import android.content.Intent;

import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import ie.craftbeerireland.R;
import ie.craftbeerireland.models.CraftBeer;




public class Add extends Base{


    private String beerName, craftBar;
    private double beerPrice, ratingValue;
    private EditText name, bar, price;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        name = findViewById(R.id.addNameET);
        bar = findViewById(R.id.addPubET);
        price = findViewById(R.id.addPriceET);
        ratingBar = findViewById(R.id.addRatingBar);
    }



    public void addBeer(View v) {
        beerName = name.getText().toString();
        craftBar = bar.getText().toString();
        try {
            beerPrice = Double.parseDouble(price.getText().toString());
        }catch (NumberFormatException e) {
            beerPrice = 0.0;
        }
        ratingValue = ratingBar.getRating();

        if((beerName.length() > 0)
                && (craftBar.length() > 0)
                && (price.length() >0)){
            CraftBeer craft = new CraftBeer(beerName,
                    craftBar,
                    ratingValue,
                    beerPrice, false);

            app.beerList.add(craft);
            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }else
            Toast.makeText(this,
                    "You must Enter Something for "
                            + "\'Name\', \'Bar\' and \'Price\'",
                    Toast.LENGTH_SHORT).show();
    }
}
