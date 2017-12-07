package com.example.anweshabiswas.smartnation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudyMainActivity extends AppCompatActivity implements View.OnClickListener
{

    private static final String ENDPOINT="https://floating-forest-82850.herokuapp.com/area/meeting";
    private RequestQueue requestQueue;
    private Gson gson;
    private  List<MeetingPlaces> posts=new ArrayList<>();
    private ArrayList<MeetingPlaces> library=new ArrayList<>();
    private ArrayList<MeetingPlaces> meeting=new ArrayList<>();
    private ImageButton LibraryButton;
    private ImageButton MeetingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        LibraryButton = (ImageButton)findViewById(R.id.librarybutton);
        MeetingButton = (ImageButton)findViewById(R.id.meetingbutton2);
        posts=new ArrayList<>();
        library=new ArrayList<>();
        meeting=new ArrayList<>();


        LibraryButton.setOnClickListener(this);
        MeetingButton.setOnClickListener(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        requestQueue = Volley.newRequestQueue(this);
        gson = gsonBuilder.create();
        fetchPosts();
    }
    private void fetchPosts()
    {   Log.i("PostActivityLib","fetching");
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            posts = Arrays.asList(gson.fromJson(response, MeetingPlaces[].class));

            Log.i("PostActivityLib", posts.size() + " posts loaded.");
            ArrayList<MeetingPlaces> library1=new ArrayList<>();
            ArrayList<MeetingPlaces> meeting2=new ArrayList<>();

            for(MeetingPlaces a:posts)
            {
                if(a.getMeetingCategory().equalsIgnoreCase("library"))
                    library1.add(a);
                else
                    meeting2.add(a);
            }
            library=library1;
            meeting=meeting2;

        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivityLib", error.toString());
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.librarybutton:
                startLibrary();
                break;
            case R.id.meetingbutton2:
                startMeeting();
                break;
            default:
                break;

        }
    }

    private void startLibrary() {
        Intent launchLibrary = new Intent(this, LibraryMainActivity.class);
        launchLibrary.putExtra("LIBRARY",library);
        Log.i("startLibrary",library.toString());
        startActivity(launchLibrary);
    }
    private void startMeeting() {
        Intent launchMeeting = new Intent(this, MeetingRoomActivity.class);
        launchMeeting.putExtra("MEETING",meeting);
        startActivity(launchMeeting);
    }
    }

