package ie.craftbeerireland.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ie.craftbeerireland.R;
import ie.craftbeerireland.adapters.RecyclerViewAdapter;
import ie.craftbeerireland.fragments.AddFragment;
import ie.craftbeerireland.fragments.CraftBeerFragment;
import ie.craftbeerireland.fragments.EditFragment;
import ie.craftbeerireland.fragments.SearchFragment;
import ie.craftbeerireland.main.CraftBeerIreland;
import ie.craftbeerireland.models.CraftBeer;

import static android.os.Build.ID;


public class Home extends Base implements NavigationView.OnNavigationItemSelectedListener,
        EditFragment.OnFragmentInteractionListener {
        private DatabaseReference myRef;
        private FirebaseDatabase database;
        public List<CraftBeer> beerList;
        //TODO Recycler View!!
        // RecyclerView recyclerView;
//        CraftBeerIreland app = new CraftBeerIreland();
        CraftBeer craftBeer;
        FragmentTransaction fragT;
        ArrayAdapter<String> arrayAdapter;
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



        //ValueEventListener messageListener = new ValueEventListener() {
        myRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    craftBeer = dataSnapshot.getValue(CraftBeer.class);
                    beerList.add(craftBeer);
                    app.beerList = beerList;
//                    beer.setText((CharSequence) beerList);

//                    bar.setText(craftBeer.craftBar);
//                    rating.setText(craftBeer.rating + " *");
//                    price.setText("€" +
//                           new DecimalFormat("0.00").format(craftBeer.price));

//                    if (craftBeer.favourite == true)
//                        fav.setImageResource(R.drawable.tumbs_on);
//                    else
//                        fav.setImageResource(R.drawable.tumbs_neu);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
            }
        });

       // database.addValueEventListener(messageListener);


        //TODO The recycler view!!
        //recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter(new RecyclerViewAdapter(this, app.beerList));

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to Quit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                    Home.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
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

    @SuppressWarnings("StatementWithEmptyBody")
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

//        craftBeerFragment = CraftBeerFragment.newInstance();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, craftBeerFragment)
//                .commit();
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

