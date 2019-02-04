package ie.craftbeerireland.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import ie.craftbeerireland.activities.Base;
import ie.craftbeerireland.adapters.BeerListAdapter;

public class CraftBeerFragment extends ListFragment implements View.OnClickListener{


    public Base activity;
    public static BeerListAdapter listAdapter;
    public ListView listView;

    public CraftBeerFragment() {
        // Required empty public constructor
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
        listAdapter = new BeerListAdapter(activity,this,Base.beerList);
        setListAdapter(listAdapter);
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onClick(View view)
    {
    }

}
