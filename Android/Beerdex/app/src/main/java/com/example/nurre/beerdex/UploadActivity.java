package com.example.nurre.beerdex;

import android.Manifest;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
public class UploadActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button btnChoose, btnUpload;
    private EditText text_description;
    private Spinner dropdown_beerselect;
    public static String URL = "http://188.166.170.111:8080/image/upload"; //Add ip to our server
    static final int PICK_IMAGE_REQUEST = 1;
    String filePath;
    String description;
    String selected_Beer;
    //Remove the 1..4 only makes it easier for me to see what number corresponds to a specific beer.
    String[] beer = new String[]{"1. Lager", "2. Ale", "3. IPA", "4. APA"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        imageView = findViewById(R.id.imageView);
        btnChoose = findViewById(R.id.button_choose);
        btnUpload = findViewById(R.id.button_upload);
        text_description = findViewById(R.id.textfield_description);
        dropdown_beerselect = findViewById(R.id.dropdown_beerselect);
        //Limiting text input to 250 just to make sure we are not sending too long strings..
        text_description.setFilters(new InputFilter[]{new InputFilter.LengthFilter(250)});

        //The drop down for beers
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, beer);
        dropdown_beerselect.setAdapter(adapter);

        //clicking button, then using imageBrowse to display picture
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageBrowse();
            }
        });
        //If no image selected show message it shows a toast that no image has been selected
        btnUpload.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (filePath != null) {
                    Toast.makeText(getApplicationContext(), "filepath: " + filePath, Toast.LENGTH_LONG).show();
                    description = text_description.getText().toString();
                    selected_Beer = String.valueOf(dropdown_beerselect.getSelectedItemPosition() + 1);
                    imageUpload(filePath);
                } else {
                    Toast.makeText(getApplicationContext(), "No image selected!", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    //Browse the image in app
    private void imageBrowse() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == PICK_IMAGE_REQUEST) {
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
                Log.d("Response from server:", response);
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jObj = new JSONObject(response);
                    String message = jObj.getString("first message");

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
        if (ContextCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //This will print a toast which just tells you if the permission is granted or not
            //Toast.makeText(UploadActivity.this, "READ permission not granted.", Toast.LENGTH_SHORT).show();
            //requestReadPermission();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
        }
        // ACTUALLY GRANT THE APP TO GET PERMISSION TO ACSESS FILE
        if (ContextCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //This will print a toast which just tells you if the permission is granted or not
            //Toast.makeText(UploadActivity.this, "WRITE permission not granted.", Toast.LENGTH_SHORT).show();
            //requestWritePermission();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }


        //since no login is implemented we cannot actually tell the id
        //Adds param userID
        smr.addStringParam("userID", "1");
        //Adds param beerID with value from dropdown
        smr.addStringParam("beerID", "" + selected_Beer);
        //Do we really need to send this?
        smr.addStringParam("imagename", "nurre.jpeg");
        smr.addStringParam("mimetype", "image/jpeg");
        smr.addStringParam("description", "" + description);
        smr.addFile("image", imagePath);
        UploadImage.getInstance().addToRequestQueue(smr);


        //Toast.makeText(getApplicationContext(), "Enter a description!", Toast.LENGTH_LONG).show();

        //Adding the picture TODO: might have to cast the sending picture to .jpeg

    }

    private String getPath(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}