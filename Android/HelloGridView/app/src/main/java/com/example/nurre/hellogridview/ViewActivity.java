package com.example.nurre.hellogridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

//import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int position = extras.getInt("position");
            Toast.makeText(ViewActivity.this, "Picture used: " + position,
                    Toast.LENGTH_SHORT).show();
            //The key argument here must match that used in the other activity
        }

    }

    public void imageClick(View view){
        ImageView image = (ImageView) view;
        try {
            Picasso.with(ViewActivity.this).load("https://images.pexels.com/photos/247932/pexels-photo-247932.jpeg").into(image); //http://i.imgur.com/DvpvklR.png
        } catch (Exception e) {
            Toast.makeText(ViewActivity.this, "That didn't go well. Picasso!", Toast.LENGTH_SHORT).show();
        }
    }
}
