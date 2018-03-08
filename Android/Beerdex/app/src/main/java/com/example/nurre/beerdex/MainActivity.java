package com.example.nurre.beerdex;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.request.ImageRequest;
import com.android.volley.ui.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private List<BeerImage> beerImages = new ArrayList<>();
    private RecyclerView recyclerView;
    private BeerImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ImageView imageView = (ImageView) findViewById(R.id.media_image);
        //Picasso.with(MainActivity.this).load("https://i.ytimg.com/vi/7oVSaUWeKt0/maxresdefault.jpg").into(imageView);
        //Picasso.with(MainActivity.this).load("").into(imageView);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new BeerImageAdapter(MainActivity.this, beerImages);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(mAdapter);

        fetchBeerImages();

    }

    private void fetchBeerImages(){
        BeerImage beerImage = new BeerImage("author#1", "Example pictures");
        beerImages.add(beerImage);

        beerImage = new BeerImage("author#2", "Example pictures");
        beerImages.add(beerImage);

        beerImage = new BeerImage("author#3", "Example pictures");
        beerImages.add(beerImage);

        beerImage = new BeerImage("author#3", "Example pictures");
        beerImages.add(beerImage);

        beerImage = new BeerImage("author#4", "Example pictures");
        beerImages.add(beerImage);

        beerImage = new BeerImage("author#5", "Example pictures");
        beerImages.add(beerImage);

        beerImage = new BeerImage("author#6", "Example pictures");
        beerImages.add(beerImage);
    }



}
