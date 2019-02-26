package ie.craftbeerireland.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import ie.craftbeerireland.R;
import ie.craftbeerireland.activities.Base;
import ie.craftbeerireland.activities.Edit;
import ie.craftbeerireland.activities.Favourites;
import ie.craftbeerireland.adapters.BeerFilter;
import ie.craftbeerireland.adapters.BeerListAdapter;
import ie.craftbeerireland.main.CraftBeerIreland;
import ie.craftbeerireland.models.CraftBeer;

public class CraftBeerFragment  extends Fragment implements
        AdapterView.OnItemClickListener,
        View.OnClickListener,
        AbsListView.MultiChoiceModeListener
{
    public Base activity;
    public static BeerListAdapter listAdapter;
    public ListView listView;
    public BeerFilter beerFilter;
    public boolean favourites = false;

    public CraftBeerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle activityInfo = new Bundle(); // Creates a new Bundle object
        activityInfo.putString("beerId", (String) view.getTag());

        Fragment fragment = EditFragment.newInstance(activityInfo);
        getActivity().setTitle(R.string.editBeerLbl);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }


    public static CraftBeerFragment newInstance() {
        CraftBeerFragment fragment = new CraftBeerFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.activity = (Base) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, parent, false);
        getActivity().setTitle(R.string.recentlyViewedLbl);
        listAdapter = new BeerListAdapter(activity, this, activity.app.beerList);
        beerFilter = new BeerFilter(activity.app.beerList,"all",listAdapter);

        if (favourites) {
            getActivity().setTitle(R.string.favouritesBeerLbl);
            beerFilter.setFilter("favourites"); // Set the filter text field from 'all' to 'favourites'
            beerFilter.filter(null); // Filter the data, but don't use any prefix
            listAdapter.notifyDataSetChanged(); // Update the adapter
        }
        // setRandomCoffee();

        listView = v.findViewById(R.id.homeList);

        setListView(v);

        return v;
    }

    public void setListView(View view)
    {
        listView.setAdapter (listAdapter);
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
        listView.setEmptyView(view.findViewById(R.id.emptyList));
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onClick(View view)
    {
        if (view.getTag() instanceof CraftBeer)
        {
            onCoffeeDelete ((CraftBeer) view.getTag());
        }
    }

    public void onCoffeeDelete(final CraftBeer beer)
    {
        String stringName = beer.beerName;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to Delete the \'Coffee\' " + stringName + "?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                activity.app.beerList.remove(beer); // remove from our list
                listAdapter.beerList.remove(beer); // update adapters data
                setRandomCoffee();
                listAdapter.notifyDataSetChanged(); // refresh adapter
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /* ************ MultiChoiceModeListener methods (begin) *********** */
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu)
    {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.delete_list_context, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.menu_item_delete_beer:
                deleteCoffees(actionMode);
                return true;
            default:
                return false;
        }
    }

    public void deleteCoffees(ActionMode actionMode)
    {
        for (int i = listAdapter.getCount() -1 ; i >= 0; i--)
        {
            if (listView.isItemChecked(i))
            {
                activity.app.beerList.remove(listAdapter.getItem(i));
                if (favourites)
                    listAdapter.beerList.remove(listAdapter.getItem(i));
            }
        }
        setRandomCoffee();
        listAdapter.notifyDataSetChanged(); // refresh adapter

        actionMode.finish();
    }

    public void setRandomCoffee() {

        ArrayList<CraftBeer> coffeeList = new ArrayList<>();

        for(CraftBeer c : activity.app.beerList)
            if (c.favourite)
                coffeeList.add(c);

        if (favourites)
            if( !coffeeList.isEmpty()) {
                CraftBeer randomBeer = coffeeList.get(new Random()
                        .nextInt(coffeeList.size()));

                ((TextView) getActivity().findViewById(R.id.favouriteBeerName)).setText(randomBeer.beerName);
                ((TextView) getActivity().findViewById(R.id.favouriteCraftBar)).setText(randomBeer.craftBar);
                ((TextView) getActivity().findViewById(R.id.favouriteBeerPrice)).setText("€ " + randomBeer.price);
                ((TextView) getActivity().findViewById(R.id.favouriteBeerRating)).setText(randomBeer.rating + " *");
            }
            else {
                ((TextView) getActivity().findViewById(R.id.favouriteBeerName)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteCraftBar)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteBeerPrice)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteBeerRating)).setText("N/A");
            }
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {}

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
    }

    /* ************ MultiChoiceModeListener methods (end) *********** */
}