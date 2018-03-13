package com.example.nurre.beerdex;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import java.io.IOException;
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
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new BeerImageAdapter(MainActivity.this, beerImages);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(mAdapter);

        Log.i("MainActivity", "Created recyclerview");
        myToolbar.findViewById(R.id.upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "Pressed upload button", Toast.LENGTH_SHORT).show();
                uploadImage();
            }
        });
        Log.i("MainActivity", "Created onClickListener on backbutton.");

        fetchBeerImages(0);
        Log.i("MainActivity", "fetchBeerImages(0)");
        //fetchBeerImages_false();
        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(mLayoutManager.findLastCompletely)
            }
        });*/

    }

    private void fetchBeerImages_false(){
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

    public void uploadImage(){
        Intent upload = (Intent) new Intent(this, UploadActivity.class);
        startActivity(upload);
        //Toast.makeText(MainActivity.this, "You pressed upload, but nothing is happening :P", Toast.LENGTH_LONG);
    }

    public void fetchBeerImages(final int lastIID){

        @SuppressLint("StaticFieldLeak") AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse( "application/x-www-form-urlencoded" );
                RequestBody body = RequestBody.create( mediaType, "lastImageID=" + Integer.toString(lastIID) );
                Request request = new Request.Builder()
                        .url( "http://188.166.170.111:8080/stream/" )
                        .post( body )
                        .addHeader( "content-type", "application/x-www-form-urlencoded" )
                        .build();
                try{
                    Response response = client.newCall(request).execute();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    Log.i("MainActivity", "Fetched JSON-object");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject fetchedBeer = jsonArray.getJSONObject(i);

                        BeerImage newBeerImage = new BeerImage(
                                fetchedBeer.getInt("image_id"),
                                fetchedBeer.getString("link"),
                                fetchedBeer.getString("user_name"),
                                fetchedBeer.getString("beer_name"),
                                fetchedBeer.getString("beer_type"),
                                fetchedBeer.getString("brewery_name"),
                                fetchedBeer.getString("country"),
                                fetchedBeer.getString("description")
                        );
                        Log.i("MainActivity", "Downloaded picture with ID = " + fetchedBeer.getInt("image_id"));
                        beerImages.add(newBeerImage);
                        Log.i("MainActivity", "Added to beerImages.");
                    }



                } catch (IOException e){
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("No more content. JSONException when fetching pictures from server.");
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                mAdapter.notifyDataSetChanged();
            }
        };
        task.execute(lastIID);
    }

}

