package com.example.axelhellman.testrequest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResult = (TextView) findViewById(R.id.tv_result);

        //make get request
        //new GetDataTask().execute("http://192.168.10.154:8000/api/status");
        //new PostDataTask().execute("http://192.168.10.154:8000/api/status");
        new PostDataTask().execute("http://130.238.93.101:8000/api/status");
    }
    class GetDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading data..");
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params){
           try{
            return  getData(params[0]);
           } catch (IOException e) {
               return "Network error1!";
           }
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            //set data response to textView
            mResult.setText(result);
            //cancel progress
            if (progressDialog != null){
               progressDialog.dismiss();
            }
        }

        private String getData(String urlPath) throws IOException{
            StringBuilder result = new StringBuilder();
            BufferedReader bufferedReader = null;
            //Initialize and config request, then connect to server
            try {
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000); //miliseconds
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json"); //set header
                urlConnection.connect();

                //Read data response from server
                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    result.append(line).append("\n");
                }

            }finally {
                if (bufferedReader != null){
                    bufferedReader.close();
                }
            }
            return result.toString();
        }
 }

    class PostDataTask extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Inserting data..");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params){
            try {
                return postData(params[0]);
            }catch (IOException ex){
                return "1337Network error2!";
            } catch (JSONException ex){
                return "Invalid data!";
            }
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            mResult.setText(result);
            if(progressDialog != null){
                progressDialog.dismiss();
            }
        }

        private String postData(String urlPath) throws IOException, JSONException{

            StringBuilder result = new StringBuilder();
            BufferedWriter bufferedWriter = null;
            BufferedReader bufferedReader = null;

            try{
            //create data to send to server
            JSONObject dataToSend = new JSONObject();
            dataToSend.put("fbname", "Axel is the best");
            dataToSend.put("content", "True");
            dataToSend.put("likes", 12);
            dataToSend.put("comments", 12);

            //intialize and config request then connect to server
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000); //miliseconds
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("Post");
            urlConnection.setDoOutput(true); //enable output (body data)
            urlConnection.setRequestProperty("Content-Type", "application/json"); //set header
            urlConnection.connect();


            //write data into server
            OutputStream outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(dataToSend.toString());
            bufferedWriter.flush();

            //Read data response from server
            InputStream inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                result.append(line).append("\n");
            }
            } finally {
                if(bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            }
            return result.toString();
        }
    }

    //class PutDataTask
}
