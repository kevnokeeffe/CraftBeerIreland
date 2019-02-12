package ie.craftbeerireland.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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

public class CraftBeerFragment extends ListFragment implements View.OnClickListener, AbsListView.MultiChoiceModeListener{


    public Base activity;
    public static BeerListAdapter listAdapter;
    public ListView listView;
    public BeerFilter beerFilter;

    public CraftBeerFragment() {

    }

    public static CraftBeerFragment newInstance() {
        CraftBeerFragment fragment = new CraftBeerFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        listView = v.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);

        return v;
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
        listAdapter = new BeerListAdapter(activity,this, activity.app.beerList);
        beerFilter = new BeerFilter(activity.app.beerList,"all",listAdapter);
        if (getActivity() instanceof Favourites) {
            beerFilter.setFilter("favourites");
            beerFilter.filter(null);
            listAdapter.notifyDataSetChanged();
        }
        setListAdapter(listAdapter);
        setRandomBeer();
        checkEmptyList();
    }

    public void checkEmptyList()
    {
        TextView recentList = getActivity().findViewById(R.id.emptyList);

        if(activity.app.beerList.isEmpty())
            recentList.setText(getString(R.string.emptyMessageLbl));
        else
            recentList.setText("");
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
            onCraftBeerDelete ((CraftBeer) view.getTag());
        }
    }

    public void onCraftBeerDelete(final CraftBeer craftBeer)
    {
        //not beer name could be error!!
        String stringName = craftBeer.beerName;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to Delete the \'Craft Beer\' " + stringName + "?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                activity.app.beerList.remove(craftBeer);
                listAdapter.beerList.remove(craftBeer);
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
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Bundle activityInfo = new Bundle();
        activityInfo.putString("beerId",(String) v.getTag());

        Intent goEdit = new Intent(getActivity(), Edit.class);
        goEdit.putExtras(activityInfo);
        getActivity().startActivity(goEdit);
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
        for (int i = listAdapter.getCount() - 1; i >= 0; i--)
        {
            if (listView.isItemChecked(i))
            {
                activity.app.beerList.remove(listAdapter.getItem(i));
            }
        }
        actionMode.finish();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {}

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked)
    {}

    public void setRandomBeer() {

        ArrayList<CraftBeer> beerList = new ArrayList<>();

        for(CraftBeer c : activity.app.beerList)
            if (c.favourite)
                beerList.add(c);

        if (activity instanceof Favourites)
            if( !beerList.isEmpty()) {
                CraftBeer randomCoffee = beerList.get(new Random()
                        .nextInt(beerList.size()));

                ((TextView) getActivity().findViewById(R.id.favouriteBeerName)).setText(randomCoffee.beerName);
                ((TextView) getActivity().findViewById(R.id.favouriteCraftBar)).setText(randomCoffee.craftBar);
                ((TextView) getActivity().findViewById(R.id.favouriteBeerPrice)).setText("€ " + randomCoffee.price);
                ((TextView) getActivity().findViewById(R.id.favouriteBeerRating)).setText(randomCoffee.rating + " *");
            }
            else {
                ((TextView) getActivity().findViewById(R.id.favouriteBeerName)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteCraftBar)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteBeerPrice)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteBeerRating)).setText("N/A");
            }
    }
}
