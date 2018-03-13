package com.example.nurre.beerdex;

import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.nurre.beerdex.BeerImageAdapter.download_URL;

public class ImageViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        Intent showImage = getIntent();
        BeerImage beerImage = showImage.getParcelableExtra("beerImage");

        ImageView image = findViewById(R.id.beer_image);
        TextView beer_name = findViewById(R.id.beer_name);
        TextView desc = findViewById(R.id.description);
        TextView brewery = findViewById(R.id.brewery_name);
        TextView author = findViewById(R.id.author);
        TextView country = findViewById(R.id.country);

        Picasso.with(this).load(download_URL + beerImage.getLink()).into(image);
        beer_name.setText(beerImage.getBeerName());
        desc.setText(beerImage.getDescription());
        brewery.setText("brewery: "+ beerImage.getBreweryName());
        author.setText(beerImage.getUserName());
        country.setText(beerImage.getCountry());


    }

    public void getAwayFromImageView(View view){
        this.finish();
    }
}
