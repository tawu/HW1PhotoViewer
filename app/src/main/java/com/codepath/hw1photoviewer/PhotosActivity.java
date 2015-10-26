package com.codepath.hw1photoviewer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class PhotosActivity extends ActionBarActivity {

    public static final String InstagramPopularURL = "https://api.instagram.com/v1/media/popular?client_id=";
    public static final String CLIENT_ID = "fd46d48e009e4ad19335aa6da38bbf76";

    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotoAdapter aPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        //Send out API request to popular photos
        photos = new ArrayList<InstagramPhoto>();

        //1. Create the adapter linking it to the source
        aPhotos = new InstagramPhotoAdapter(this, photos);
        //2. Find the Listview from teh layout
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        //3. Set the adapter binding it to the ListView object
        lvPhotos.setAdapter(aPhotos);

        //Send out API request to popular photos
        fetchPopularPhotos();
    }

    public void fetchPopularPhotos()
    {

        String url = InstagramPopularURL + CLIENT_ID;

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
                //Log.i("DEBUG", response.toString());
                //iterate each of the photo items and decode the item into a java object
                JSONArray photosJSON = null;
                try {
                    photosJSON = response.getJSONArray("data");
                    for (int i = 0; i < photosJSON.length(); i++) {
                        //get json object at that position
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        //decode attributes of the json into a data model
                        InstagramPhoto photo = new InstagramPhoto();
                        //- Author Name: {"data" => [X] //array  => "user" => "username"}
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        //- Caption: {"data" => [X] //array  => "caption" => "text"}
                        photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        //- Type: {"data" => [X] //array  => "type"}: "image" or "video"
                        photo.type = photoJSON.getString("type");
                        //- URL: {"data" => [X] //array  => "images" => "standard_resolution" => "url"}
                        photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        //- Height: {"data" => [X] //array  => "images" => "standard_resolution" => "height"}
                        photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        //- Likes count: {"data" => [X] //array  => "likes" => "count"}
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");

                        //add photo to photos array
                        photos.add(photo);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //callback
                //when the dataset photos is changed, we need to notify Array adapter aPhotos
                aPhotos.notifyDataSetChanged();
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
