package ie.craftbeerireland.adapters;

import android.widget.Filter;
import java.util.ArrayList;
import java.util.List;
import ie.craftbeerireland.models.CraftBeer;

public class BeerFilter extends Filter {

    public List<CraftBeer> originalBeerList;
    public String filterText;
    public BeerListAdapter adapter;

    public BeerFilter(List<CraftBeer> originalBeerList, String filterText,
                        BeerListAdapter adapter) {
        super();
        this.originalBeerList = originalBeerList;
        this.filterText = filterText;
        this.adapter = adapter;
    }

    public void setFilter(String filterText) {
        this.filterText = filterText;
    }

    @Override
    protected FilterResults performFiltering(CharSequence prefix) {
        FilterResults results = new FilterResults();

        List<CraftBeer> newBeers;
        String beerName;

        if (prefix == null || prefix.length() == 0) {
            newBeers = new ArrayList<>();
            if (filterText.equals("all")) {
                results.values = originalBeerList;
                results.count = originalBeerList.size();
            } else {
                if (filterText.equals("favourites")) {
                    for (CraftBeer cb : originalBeerList)
                        if (cb.favourite)
                            newBeers.add(cb);
                }
                results.values = newBeers;
                results.count = newBeers.size();
            }
        } else {
            String prefixString = prefix.toString().toLowerCase();
            newBeers = new ArrayList<>();

            for (CraftBeer cb : originalBeerList) {
                beerName = cb.beerName.toLowerCase();
                if (beerName.contains(prefixString)) {
                    if (filterText.equals("all")) {
                        newBeers.add(cb);
                    } else if (cb.favourite) {
                        newBeers.add(cb);
                    }}}
            results.values = newBeers;
            results.count = newBeers.size();
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence prefix, FilterResults results) {

        adapter.beerList = (ArrayList<CraftBeer>) results.values;

        if (results.count >= 0)
            adapter.notifyDataSetChanged();
        else {
            adapter.notifyDataSetInvalidated();
            adapter.beerList = originalBeerList;
        }
    }
}
