package ie.craftbeerireland.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import ie.craftbeerireland.R;
import ie.craftbeerireland.fragments.CraftBeerFragment;

public class Favourites extends Base {

    TextView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);
        emptyList = findViewById(R.id.emptyList);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(app.beerList.isEmpty())
            emptyList.setText(getString(R.string.emptyMessageLbl));
        else
            emptyList.setText("");
        Log.v("Craft Beer Ireland", "Home : " + app.beerList);

        craftBeerFragment = CraftBeerFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, craftBeerFragment)
                .commit();
    }
}
