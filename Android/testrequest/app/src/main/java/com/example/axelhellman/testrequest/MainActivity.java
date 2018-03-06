package com.example.axelhellman.testrequest;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button btnChoose, btnUpload;

    public static String URL = "http://188.166.170.111:8080/image/upload"; //Add ip to our server
    static final int PICK_IMAGE_REQUEST = 1;
    String filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        btnChoose = findViewById(R.id.button_choose);
        btnUpload = findViewById(R.id.button_upload);
        //Display the image selected
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageBrowse();
            }
        });
        //If no image selected show message that a toast with "Image not selected"
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filePath != null) {
                    imageUpload(filePath);
                } else {
                    Toast.makeText(getApplicationContext(), "Image not selected!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private void imageBrowse() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if(requestCode == PICK_IMAGE_REQUEST){
                Uri picUri = data.getData();

                filePath = getPath(picUri);

                Log.d("picUri", picUri.toString());
                Log.d("filePath", filePath);
                imageView.setImageURI(picUri);

            }

        }

    }

    private void imageUpload(final String imagePath) {
        //A Simple request for making a Multi Part request whose response is retrieve as String
        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String message = jObj.getString("message");

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        // ACTUALLY GRANT THE APP TO GET PERMISSION TO ACSESS FILE
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //This will print a toast which just tells you if the permission is granted or not
            //Toast.makeText(MainActivity.this, "READ permission not granted.", Toast.LENGTH_SHORT).show();
            //requestReadPermission();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
        }
        // ACTUALLY GRANT THE APP TO GET PERMISSION TO ACSESS FILE
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //This will print a toast which just tells you if the permission is granted or not
            //Toast.makeText(MainActivity.this, "WRITE permission not granted.", Toast.LENGTH_SHORT).show();
            //requestWritePermission();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }
        //since no login is implemented we cannot actually tell the id
        smr.addStringParam("userID", "1");
        //Todo: Need to implement something that tells you what kind of beer it is
        smr.addStringParam("beerID", "1");
        smr.addStringParam("imagename", "nurre.jpeg");
        smr.addStringParam("mimetype", "image/jpeg");
        //Todo: Need to implemnent a text-field that the user can type in and upload as a description
        smr.addStringParam("description", "Hej nurreHej nurreHej nurreHej nurreHej nurreHej nurreHej nurreHej nurreHej nurreHej nurreHej nurre");
        //Adding the picture TODO: might have to cast the sending picture to .jpeg
        smr.addFile("image", imagePath);
        MyApplication.getInstance().addToRequestQueue(smr);
    }

    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}