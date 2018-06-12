package com.getingstarted.pogba.gsonandvolley;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class PostActivity extends Activity {

    private static final String ENDPOINT = "https://kylewbanks.com/rest/posts.json";

    //private RequestQueue requestQueue;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //The first thing we’ll need to do is instantiate a RequestQueue
        // which will handle running our request in a background thread:
        //requestQueue = Volley.newRequestQueue(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

       // requestQueue = Volley.newRequestQueue(getApplicationContext());
        fetchPosts();

    }

    private void fetchPosts() {

        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);
        MySingletonForReq.getIstance(getApplicationContext()).addToRequestQueue(request);
    }

    private  final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            //Deserializing the JSON
            List<Post> posts = Arrays.asList(gson.fromJson(response, Post[].class));

           Log.i("PostActivity ", response);

            Log.i("  PostActivity ", posts.size() + " posts loaded.");
            for(Post post : posts){
                Log.i("PostActivity ", post.getID() + ": " + post.getTitle());
                Log.d("URL: ",post.getUrl());
            }
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i(getApplication().getPackageName(), error.toString());
        }
    };
}
