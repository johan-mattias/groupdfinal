package com.example.nurre.beerdex;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nurre on 2018-03-07.
 */

public class BeerImageAdapter extends RecyclerView.Adapter<BeerImageAdapter.BeerCardView> {
    private Context mContext;
    private List<BeerImage> beerImages;
    public static String download_URL = "http://188.166.170.111:8080/getImage/";

    public class BeerCardView extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title, user_name;

        public BeerCardView(View view){
            super(view);
            image = (ImageView) view.findViewById(R.id.beer_image);
            title = (TextView) view.findViewById(R.id.primary_text);
            user_name = (TextView) view.findViewById(R.id.sub_text);
        }

    }

    public BeerImageAdapter(Context mContext, List<BeerImage> beerImages) {
        this.mContext = mContext;
        this.beerImages = beerImages;
    }

    @Override
    public BeerCardView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);

        return new BeerCardView(itemView);
    }

    @Override
    public void onBindViewHolder(BeerCardView holder, int position) {
        final BeerImage beerimage = beerImages.get(position);

        Picasso.with(mContext).load(download_URL + beerimage.getLink()).into(holder.image);
        holder.title.setText(beerimage.getBeerName());
        holder.user_name.setText(beerimage.getUserName());

        holder.image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Log.i("Bruv idk ################", "OnClick reached");
                showBeerImage(view, beerimage);
            }
        });

    }

    @Override
    public int getItemCount() {
        return beerImages.size();
    }

    private void showBeerImage(View view, BeerImage image){
        Log.i("Bruv idk ################", "showBeerImage reached");
        //Toast.makeText(mContext, "You pressed the image made by: " + image.getUserName(), Toast.LENGTH_LONG);
        Intent showImage = new Intent(view.getContext(), ImageViewer.class);
        showImage.putExtra("beerImage", (BeerImage) image);
        view.getContext().startActivity(showImage);

    }
}
