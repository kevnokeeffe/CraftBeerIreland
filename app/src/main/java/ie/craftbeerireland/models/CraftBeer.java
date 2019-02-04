package ie.craftbeerireland.models;

import java.io.Serializable;
import java.util.UUID;

public class CraftBeer implements Serializable {

    public String beerId;
    public String beerName;
    public String craftBar;
    public double rating;
    public double price;
    public boolean favourite;


    public CraftBeer() {}

    public CraftBeer( String name, String bar, double rating, double price, boolean fav)
    {
        this.beerId = UUID.randomUUID().toString();
        this.beerName = name;
        this.craftBar = bar;
        this.rating = rating;
        this.price = price;
        this.favourite = fav;
    }

    @Override
    public String toString() {
        return beerId + "" +beerName + ", " + craftBar + ", " + rating
                + ", " + price + ", fav =" + favourite;
    }
}
