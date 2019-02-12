package ie.craftbeerireland.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ie.craftbeerireland.R;
import ie.craftbeerireland.models.CraftBeer;

public class BeerListAdapter extends ArrayAdapter<CraftBeer> {


    private Context context;
    private View.OnClickListener deleteListener;
    public List<CraftBeer> beerList;

    public BeerListAdapter(Context context, View.OnClickListener deleteListener, List<CraftBeer> beerList)
    {
        // TODO: 12/02/2019 ONE
        super(context, R.layout.beer_row, beerList);

        this.context = context;
        this.deleteListener = deleteListener;
        this.beerList = beerList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CraftBeerItem item = new CraftBeerItem(context, parent,
                deleteListener, beerList.get(position));
        return item.view;
    }

    @Override
    public int getCount()
    {
        return beerList.size();
    }

    @Override
    public CraftBeer getItem(int position) {
        return beerList.get(position);
    }


}
