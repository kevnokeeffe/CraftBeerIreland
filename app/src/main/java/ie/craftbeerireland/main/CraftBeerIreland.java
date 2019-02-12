package ie.craftbeerireland.main;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ie.craftbeerireland.models.CraftBeer;

public class CraftBeerIreland extends Application {

    public List<CraftBeer> beerList = new ArrayList<>();

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("Craft Beer Ireland", "Craft Beer Ireland App Started");
    }
}
