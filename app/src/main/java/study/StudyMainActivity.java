package study;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import models.MeetingPlace;

import com.example.anweshabiswas.smartnation.R;
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
    private  List<MeetingPlace> posts=new ArrayList<>();
    private ArrayList<MeetingPlace> library;
    private ArrayList<MeetingPlace> meeting;
    private ImageButton libraryButton;
    private ImageButton meetingButton;
    private ProgressBar progressBar;
    private boolean hasLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        libraryButton = (ImageButton)findViewById(R.id.librarybutton);
        meetingButton = (ImageButton)findViewById(R.id.meetingbutton);
        progressBar   = (ProgressBar) findViewById(R.id.progressBar);

        posts=new ArrayList<>();
        library=new ArrayList<>();
        meeting=new ArrayList<>();
        hasLoaded = false;

        libraryButton.setOnClickListener(this);
        meetingButton.setOnClickListener(this);


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
            hasLoaded = true;
            progressBar.setVisibility(View.GONE);
            posts = Arrays.asList(gson.fromJson(response, MeetingPlace[].class));

            for(MeetingPlace a:posts)
            {
                if(a.getMeetingCategory().equalsIgnoreCase("library"))
                    library.add(a);
                else
                    meeting.add(a);
            }
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
        if (hasLoaded)
            switch (v.getId()) {
                case R.id.librarybutton:
                    startLibrary();
                    break;
                case R.id.meetingbutton:
                    startMeeting();
                    break;
                default:
                    break;

            }
    }

    private void startLibrary() {
        Intent launchLibrary = new Intent(this, LibraryMainActivity.class);
        launchLibrary.putExtra("LIBRARY",library);
        startActivity(launchLibrary);
    }
    private void startMeeting() {
        Intent launchMeeting = new Intent(this, MeetingRoomActivity.class);
        launchMeeting.putExtra("MEETING",meeting);
        startActivity(launchMeeting);
    }
    }

