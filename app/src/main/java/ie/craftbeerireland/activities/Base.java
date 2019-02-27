package ie.craftbeerireland.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import ie.craftbeerireland.R;
import ie.craftbeerireland.fragments.CraftBeerFragment;
import ie.craftbeerireland.main.CraftBeerIreland;
import ie.craftbeerireland.models.CraftBeer;

public class Base extends AppCompatActivity {

    public Bundle activityInfo;
    public CraftBeerFragment craftBeerFragment;
    public CraftBeerIreland app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (CraftBeerIreland) getApplication();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        return true;
    }

    public void menuHome(MenuItem m) {
        Intent intent = new Intent(getApplicationContext(), Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void menuInfo(MenuItem m) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.appAbout))
                .setMessage(getString(R.string.appDesc)
                        + "\n\n"
                        + getString(R.string.appMoreInfo))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    public void menuHelp(MenuItem m) {

    }

}

