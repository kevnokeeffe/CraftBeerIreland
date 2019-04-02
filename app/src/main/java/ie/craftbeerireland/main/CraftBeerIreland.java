package ie.craftbeerireland.main;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import ie.craftbeerireland.models.CraftBeer;

public class CraftBeerIreland extends Application {

    public List<CraftBeer> beerList = new ArrayList<>();
    public FirebaseUser user;
    @Override
    public void onCreate()
    {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        Log.v("Craft Beer Ireland", "Craft Beer Ireland App Started");
    }
}
