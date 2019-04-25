package ie.craftbeerireland.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import ie.craftbeerireland.R;
import ie.craftbeerireland.fragments.AddFragment;
import ie.craftbeerireland.fragments.CraftBeerFragment;
import ie.craftbeerireland.fragments.EditFragment;
import ie.craftbeerireland.fragments.MapsFragment;
import ie.craftbeerireland.fragments.SearchFragment;
import ie.craftbeerireland.main.CraftBeerIreland;
import ie.craftbeerireland.models.CraftBeer;


public class Home extends Base implements NavigationView.OnNavigationItemSelectedListener,
        EditFragment.OnFragmentInteractionListener {
        private DatabaseReference myRef;
        private FirebaseDatabase database;
        public List<CraftBeer> beerList;
        CraftBeer craftBeer;
        FragmentTransaction fragT;
        CraftBeerIreland app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.app = (CraftBeerIreland) getApplication();
        app.user = FirebaseAuth.getInstance().getCurrentUser();
        Log.i("user",app.user.toString());

        //getting database instance
        database = FirebaseDatabase.getInstance();

        //getting the refrence from the database
        myRef = database.getReference("Database");

        // instantiating the beer array list
        beerList = new ArrayList<CraftBeer>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragT = getSupportFragmentManager().beginTransaction();

        craftBeerFragment = CraftBeerFragment.newInstance();
        fragT.replace(R.id.fragment_container, craftBeerFragment);
        fragT.commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
                            Fragment fragment;
                            fragT = getSupportFragmentManager().beginTransaction();
                            fragment = CraftBeerFragment.newInstance();
                            fragT.replace(R.id.fragment_container, fragment);
                            fragT.addToBackStack(null);
                            fragT.commit();
                }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_info) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Navigation Drawer elements
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        Fragment fragment;
        fragT = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_home) {

            this.setTitle(R.string.recentlyViewedLbl);
            fragment = CraftBeerFragment.newInstance();
            fragT.replace(R.id.fragment_container, fragment);
            fragT.addToBackStack(null);
            fragT.commit();

        } else if (id == R.id.nav_add) {

            this.setTitle(R.string.addABeerLbl);
            fragment = AddFragment.newInstance();
            fragT.replace(R.id.fragment_container, fragment);
            fragT.addToBackStack(null);
            fragT.commit();

        } else if (id == R.id.nav_search) {
            fragment = SearchFragment.newInstance();
            fragT.replace(R.id.fragment_container, fragment);
            fragT.addToBackStack(null);
            fragT.commit();
        }
        else if (id == R.id.nav_map) {
            fragment = MapsFragment.newInstance();
            fragT.replace(R.id.fragment_container, fragment);
            fragT.addToBackStack(null);
            fragT.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void toggle(View v) {
        EditFragment editFrag = (EditFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (editFrag != null) {
            editFrag.toggle(v);
        }
    }

    @Override
    public void saveCraftBeer(View v) {
        EditFragment editFrag = (EditFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (editFrag != null) {
            editFrag.saveCraftBeer(v);
        }
    }
    
    
    public void dataSnapshot() {
        super.onStart();



    }



}

