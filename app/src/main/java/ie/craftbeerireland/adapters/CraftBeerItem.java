package ie.craftbeerireland.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

import ie.craftbeerireland.R;
import ie.craftbeerireland.models.CraftBeer;

public class CraftBeerItem {
    //private DatabaseReference mDatabase;
    //DatabaseReference beerRef;
    View view;

    public CraftBeerItem(Context context, ViewGroup parent,
                         View.OnClickListener deleteListener, CraftBeer craftBeer)
    {
        //TODO Something not right...
        //mDatabase = FirebaseDatabase.getInstance().getReference();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.craft_beer_card, parent, false);
        view.setTag(craftBeer.beerId);
        updateControls(craftBeer);

        ImageView imgDelete = view.findViewById(R.id.rowDeleteImg);
        imgDelete.setTag(craftBeer);
        imgDelete.setOnClickListener(deleteListener);
    }

    private void updateControls(CraftBeer beer) {
        ((TextView) view.findViewById(R.id.rowBeerName)).setText(beer.beerName);
        ((TextView) view.findViewById(R.id.rowCraftBar)).setText(beer.craftBar);
        ((TextView) view.findViewById(R.id.rowRating)).setText(beer.rating + " *");
        ((TextView) view.findViewById(R.id.rowPrice)).setText("€" +
                new DecimalFormat("0.00").format(beer.price));

        ImageView imgIcon = view.findViewById(R.id.rowFavouriteImg);

        if (beer.favourite == true)
            imgIcon.setImageResource(R.drawable.tumbs_on);
        else
            imgIcon.setImageResource(R.drawable.tumbs_neu);


    }

//    public void dataSnapshot() {
//
//        ValueEventListener messageListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    CraftBeer craftBeer = dataSnapshot.getValue(CraftBeer.class);
//
//                    ((TextView) view.findViewById(R.id.rowBeerName)).setText(craftBeer.beerName);
//                    ((TextView) view.findViewById(R.id.rowCraftBar)).setText(craftBeer.craftBar);
//                    ((TextView) view.findViewById(R.id.rowRating)).setText(craftBeer.rating + " *");
//                    ((TextView) view.findViewById(R.id.rowPrice)).setText("€" +
//                            new DecimalFormat("0.00").format(craftBeer.price));
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Failed to read value
//            }
//        };
//
//        mDatabase.addValueEventListener(messageListener); }


}