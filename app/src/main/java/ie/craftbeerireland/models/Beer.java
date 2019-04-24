package ie.craftbeerireland.models;

public class Beer {

        private String name, bar;
        private double price, rating, latitude, longitude;
        private boolean favourite;

        public Beer() {
        }

        public Beer(String name, String bar, double price, double rating, boolean favourite, double latitude, double longitude) {
            this.name = name;
            this.bar = bar;
            this.price = price;
            this.rating = rating;
            this.favourite = favourite;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBar() {
            return bar;
        }

        public void setBar(String bar) {
            this.bar = bar;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getRating() {
          return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public boolean getFavourite(){
            return favourite;
        }

        public void setFavourite(boolean favourite){
            this.favourite = favourite;
        }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}

