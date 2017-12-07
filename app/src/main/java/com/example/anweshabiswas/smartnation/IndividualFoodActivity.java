package com.example.anweshabiswas.smartnation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class IndividualFoodActivity extends AppCompatActivity
{
    BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;

    ImageView header;
    TextView occ;
    TextView graphTitle;

    List<Statistics> stats=new ArrayList<>();
    String statsurl;
    private RequestQueue requestQueue;
    private Gson gson;
    private final String scheme="https";
    private final String authority="www.floating-forest-82850.herokuapp.com";
    private final String ENDPOINT="http://floating-forest-82850.herokuapp.com/stats/1?from=2016-01-01%2008:03:10&to=2019-01-01%2020:03:10&part=hour";
    private final String foodpath="food";
    private final String areapath="area";
    private final String statspath="stats";
    private final String queryFrom="2016-01-01%2008:03:10";
    private final String queryto="2019-01-01%2020:03:10";
    private final String queryoart="hour";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Intent intent = getIntent();
        int id = intent.getIntExtra(RecyclerViewAdapter.KEY,0);

        Log.i("anwesha",String.valueOf(id));

        FoodPlaces location=RecyclerViewAdapter.MainFoodInfoList.get(id);

        String name= location.getName();
        Log.i("anwesharedis",name);
        int id1=location.getId(); //to set in uri builder
        String headerUrl=location.getHeaderImage();

        occ=(TextView)findViewById(R.id.location_occupancy);
        graphTitle=(TextView)findViewById(R.id.graphtitle);
        header =(ImageView)findViewById(R.id.header_image);
        double occupancy=Math.random();
        Log.i("anwesha name=",name);

        new IndividualFoodActivity.DownloadHeaderTask(header).execute(headerUrl);

        //occ.setText(name+":"+String.valueOf(occupancy));
        graphTitle.setText("Peak hours for "+name);

        RedisConnect rc=new RedisConnect(occ,name);

        chart = (BarChart) findViewById(R.id.barchart);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(scheme).authority(authority).appendPath(areapath).appendPath(foodpath);
        Uri.Builder builder1 = new Uri.Builder();
        builder1.scheme(scheme).authority(authority).appendPath(statspath).appendPath(String.valueOf(1)).appendQueryParameter("from",queryFrom).appendQueryParameter("to",queryto).appendQueryParameter("part",queryoart);
        statsurl=builder1.build().toString();
        Log.i("URL",statsurl);

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
            stats = Arrays.asList(gson.fromJson(response, Statistics[].class));

            Log.i("PostActivityGraph"+5f, stats.size() + " posts loaded.");

            BARENTRY = new ArrayList<>();
            BarEntryLabels = new ArrayList<String>();
            int count=0;
            for(Statistics s:stats)
            {

                double val = s.getAvg_count();
                float val1=(float)val;
                String label=String.valueOf(s.getInterval());


                BARENTRY.add(new BarEntry(val1, count));
                float x=Float.parseFloat(label);
                if(x<12.0)
                    BarEntryLabels.add(label+"0am");
                else
                    BarEntryLabels.add(label+"0pm");

                count++;
                Log.i("Chart loop",val1+" "+label);
            }

            Bardataset = new BarDataSet(BARENTRY, "Average number of people");

            BARDATA = new BarData(BarEntryLabels, Bardataset);

            Bardataset.setColors(ColorTemplate.PASTEL_COLORS);

            chart.setData(BARDATA);
            Log.i("Chart","set");
            chart.invalidate();
       }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivityLib", error.toString());
        }
    };

    class DownloadHeaderTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadHeaderTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            Log.i("Image","set");
        }
    }

}
