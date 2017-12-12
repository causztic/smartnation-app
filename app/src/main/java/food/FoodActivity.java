package food;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anweshabiswas.smartnation.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import json_objects.FoodPlaces;

public class FoodActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    RecyclerView recyclerView;
    RecyclerViewAdapter adapt;
    private static final String ENDPOINT="https://floating-forest-82850.herokuapp.com/area/food";
    private RequestQueue requestQueue;
    private Gson gson;
    List<FoodPlaces> posts=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placestoeat);

        GsonBuilder gsonBuilder = new GsonBuilder();
        requestQueue = Volley.newRequestQueue(this);
        gson = gsonBuilder.create();
        fetchPosts();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapt=new RecyclerViewAdapter(this,posts);
        recyclerView.setAdapter(adapt); //adapted

    }
    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            posts = Arrays.asList(gson.fromJson(response, FoodPlaces[].class));

            Log.i("PostActivity", posts.size() + " posts loaded.");
            adapt=new RecyclerViewAdapter(posts);
            recyclerView.setAdapter(adapt);

        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuitem=menu.findItem(R.id.action_search);
        SearchView searchView =(SearchView) MenuItemCompat.getActionView(menuitem);
        searchView.setOnQueryTextListener(this);
        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {



        newText=newText.toLowerCase();
        ArrayList<FoodPlaces> newlist=new ArrayList<>();
        for(FoodPlaces info: RecyclerViewAdapter.OriginalFoodInfoList)
        {
            String name=info.getName().toLowerCase();
            Log.i("anwesha","all names="+name);


            if(name.contains(newText))
            {
                Log.i("anwesha","found names="+name);
                newlist.add(info);
                continue;
            }


        }
        adapt.setFilter(newlist);

        return true;
    }


}
