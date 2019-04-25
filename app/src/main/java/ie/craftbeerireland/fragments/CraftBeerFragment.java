package ie.craftbeerireland.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import ie.craftbeerireland.R;
import ie.craftbeerireland.activities.Base;
import ie.craftbeerireland.adapters.BeerFilter;
import ie.craftbeerireland.adapters.BeerListAdapter;
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
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    public CraftBeerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle activityInfo = new Bundle();
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

        View v = inflater.inflate(R.layout.fragment_home, parent, false);
        getActivity().setTitle(R.string.app_name);
        listAdapter = new BeerListAdapter(activity, this, activity.app.beerList);
        beerFilter = new BeerFilter(activity.app.beerList,"all",listAdapter);

        if (favourites) {
            getActivity().setTitle(R.string.favouritesBeerLbl);
            beerFilter.setFilter("favourites");
            beerFilter.filter(null);
            listAdapter.notifyDataSetChanged();
        }


        listView = v.findViewById(R.id.homeList);

        setListView(v);

        //getting database instance
        database = FirebaseDatabase.getInstance();

        //getting the refrence from the database
        myRef = database.getReference("Database").child(activity.app.user.getUid()).child("beers");
        myRef.addValueEventListener(new ValueEventListener(){
            @Override
            //populating the list
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    List<CraftBeer> friends = new ArrayList<>();
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        String friend = ds.getKey();
                        CraftBeer beer = ds.getValue(CraftBeer.class);
                        friends.add(beer);
                    }
                    activity.app.beerList = friends;
                    listAdapter.beerList = activity.app.beerList;
                    listAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
            }
        });
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
            onBeerDelete ((CraftBeer) view.getTag());
        }
    }

    public void onBeerDelete(final CraftBeer beer)
    {
        String stringName = beer.beerName;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to Delete the \'Beer\' " + stringName + "?");
        builder.setCancelable(false);
        //Removes the beer item from firebase
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                myRef.child(beer.beerId).removeValue();
                activity.app.beerList.remove(beer);
                listAdapter.beerList.remove(beer);
                listAdapter.notifyDataSetChanged();
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
                deleteBeers(actionMode);
                return true;
            default:
                return false;
        }
    }

    public void deleteBeers(ActionMode actionMode)
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

        listAdapter.notifyDataSetChanged();
        actionMode.finish();
    }


    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {}

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
    }

}