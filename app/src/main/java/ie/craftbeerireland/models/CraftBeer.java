package ie.craftbeerireland.models;

import java.io.Serializable;

public class CraftBeer implements Serializable {

    public String beerId;
    public String beerName;
    public String craftBar;
    public double rating;
    public double price;
    public boolean favourite;
    public String googlephoto;
    public String usertoken;
    public String address;
    public Marker marker = new Marker();


    public CraftBeer() {}

    public CraftBeer( String name, String bar, double rating, double price, boolean fav,String photo, String token,
                      String address, double lat, double lng, String id)
    {

        this.beerName = name;
        this.craftBar = bar;
        this.rating = rating;
        this.price = price;
        this.favourite = fav;
        this.googlephoto = photo;
        this.usertoken = token;
        this.address = address;
        this.marker.coords.latitude = lat;
        this.marker.coords.longitude = lng;
        this.beerId = id;
    }

    @Override
    public String toString() {
        return  beerName + ", " + craftBar + ", " + rating
                + ", " + price + ", fav =" + favourite+ " "
                + usertoken + " " + address + " " + marker.coords.latitude
                + " " + marker.coords.longitude;
    }
}
