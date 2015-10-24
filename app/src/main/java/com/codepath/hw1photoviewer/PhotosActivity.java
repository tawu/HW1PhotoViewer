package com.codepath.hw1photoviewer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class PhotosActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "fd46d48e009e4ad19335aa6da38bbf76";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        //Send out API request to popular photos
        fetchPopularPhotos();
    }

    public void fetchPopularPhotos()
    {
        /*
        * https://api.instagram.com/v1/media/popular?client_id=fd46d48e009e4ad19335aa6da38bbf76
    - response:
        - Type: {"data" => [X] //array  => "type"}: "image" or "video"
        - URL: {"data" => [X] //array  => "images" => "standard_resolution" => "url"}
        - Caption: {"data" => [X] //array  => "caption" => "text"}
        - Author Name: {"data" => [X] //array  => "user" => "username"}
        * */

        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;


        //create the network client
        AsyncHttpClient client = new AsyncHttpClient();
        // trigger the GET query
        //since this is a async connection, we need to define a response handler
        //the response handler will be triggered when the request response is back
        //if the response is expected to be a string, then define a string handler
        //here a JSON format response is expected, so we need to define a JSON handler
        client.get(url, null, new JsonHttpResponseHandler()
            {
                // onSuccess (worked, 200)


                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response)
                    {
                        //expect a JSON object in following format
                        /*
                        - Type: {"data" => [X] //array  => "type"}: "image" or "video"
                        - URL: {"data" => [X] //array  => "images" => "standard_resolution" => "url"}
                        - Caption: {"data" => [X] //array  => "caption" => "text"}
                        - Author Name: {"data" => [X] //array  => "user" => "username"}
                        * */

                        Log.i("DEBUG", response.toString());

                    }


                // onFailure (fail)


                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
                    {
                        //Do something
                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
