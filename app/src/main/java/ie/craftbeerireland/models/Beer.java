package ie.craftbeerireland.models;

public class Beer {

        private String name, bar;
        private double price, rating;
        private boolean favourite;

        public Beer() {
        }

        public Beer(String name, String bar, double price, double rating, boolean favourite) {
            this.name = name;
            this.bar = bar;
            this.price = price;
            this.rating = rating;
            this.favourite = favourite;
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
}

