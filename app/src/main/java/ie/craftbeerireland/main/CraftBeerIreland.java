package ie.craftbeerireland.main;

import android.app.Application;
import android.location.Location;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import java.util.List;
import ie.craftbeerireland.models.CraftBeer;

public class CraftBeerIreland extends Application {
    private RequestQueue mRequestQueue;
    public List<CraftBeer> beerList = new ArrayList<>();
    public FirebaseUser user;
    private static CraftBeerIreland mInstance;
    public Location mCurrentLocation;
    public String googleToken;
    public String googlePhotoURL;
    public static CraftBeerIreland   app = CraftBeerIreland.getInstance();
    public static synchronized CraftBeerIreland getInstance() {
        return mInstance;
    }
    public static final String TAG = CraftBeerIreland.class.getName();


    @Override
    public void onCreate()
    {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        Log.v("Craft Beer Ireland", "Craft Beer Ireland App Started");
        mInstance = this;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public <T> void add(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancel() {
        mRequestQueue.cancelAll(TAG);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
