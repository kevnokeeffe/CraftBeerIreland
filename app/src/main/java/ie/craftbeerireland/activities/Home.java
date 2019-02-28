package ie.craftbeerireland.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import ie.craftbeerireland.R;
import ie.craftbeerireland.fragments.AddFragment;
import ie.craftbeerireland.fragments.CraftBeerFragment;
import ie.craftbeerireland.fragments.EditFragment;
import ie.craftbeerireland.fragments.SearchFragment;
import ie.craftbeerireland.models.CraftBeer;


public class Home extends Base implements NavigationView.OnNavigationItemSelectedListener,
        EditFragment.OnFragmentInteractionListener {

    FragmentTransaction fragT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        CraftBeerFragment fragment = CraftBeerFragment.newInstance();
        fragT.replace(R.id.fragment_container, fragment);
        fragT.commit();

        if(app.beerList.isEmpty()) setupBeers();
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



        craftBeerFragment = CraftBeerFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, craftBeerFragment)
                .commit();
    }

    public void setupBeers(){
        app.beerList.add(new CraftBeer("Yellow Belly","Grady's Yard",4.5,5.50,true));
        app.beerList.add(new CraftBeer("Moonbeam","Metalman",4.0,5.50,false));
        app.beerList.add(new CraftBeer("Elvis Juce","Tully's",4.5,5.50,false));
        app.beerList.add(new CraftBeer("12 Acres","An Uisce Beatha",4.5,5.50,true));
        app.beerList.add(new CraftBeer("Yellow Moon","Grady's Yard",4.5,5.50,true));
        app.beerList.add(new CraftBeer("22 Acres","An Uisce Beatha",4.5,5.50,true));
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
}
