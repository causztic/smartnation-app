package food;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.anweshabiswas.smartnation.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import redis.RedisConnect;
import json_objects.FoodPlaces;
import json_objects.Statistics;
import java.util.Calendar;


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
    private ProgressDialog prog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateformat.format(c.getTime());
        c.add(Calendar.DATE, -1);
        String yesterday=dateformat.format(c.getTime());
        Log.i("date",today);
        Log.i("yesterady",yesterday);
        prog=new ProgressDialog(IndividualFoodActivity.this);
        prog.setMessage("Loading information");
        prog.show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Intent intent = getIntent();
        int id = intent.getIntExtra(RecyclerViewAdapter.KEY,0);
        FoodPlaces location=RecyclerViewAdapter.MainFoodInfoList.get(id);

        String name= location.getName();
        Log.i("anwesharedis",name);
        int id1=location.getId(); //to set in uri builder
        String headerUrl=location.getHeaderImage();

        occ=(TextView)findViewById(R.id.location_occupancy);
        graphTitle=(TextView)findViewById(R.id.graphtitle);
        header =(ImageView)findViewById(R.id.header_image);

        graphTitle.setText("Peak hours for "+name);

        RedisConnect rc=new RedisConnect(occ,name);

        chart = (BarChart) findViewById(R.id.barchart);

        statsurl="http://floating-forest-82850.herokuapp.com/stats/"+String.valueOf(id1)+"?from="+yesterday+"%2008:03:10&to="+today+"%2020:03:10&part=hour";


        GsonBuilder gsonBuilder = new GsonBuilder();
        requestQueue = Volley.newRequestQueue(this);
        gson = gsonBuilder.create();
        fetchPosts();
        new IndividualFoodActivity.DownloadHeaderTask(header,getApplicationContext()).execute(headerUrl);
    }
    private void fetchPosts()
    {
        StringRequest request = new StringRequest(Request.Method.GET, statsurl, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            stats = Arrays.asList(gson.fromJson(response, Statistics[].class));

            BARENTRY = new ArrayList<>();
            BarEntryLabels = new ArrayList<String>();
            int count=0;
            for(Statistics s:stats)
            {

                double val = s.getAvg_count();
                float val1=(float)val;
                String label=String.valueOf(s.getInterval());


                BARENTRY.add(new BarEntry(val1, count));
                double x=s.getInterval()+8.0;
                if(x<12.0)
                    BarEntryLabels.add(x+"0am");
                else
                    BarEntryLabels.add(x+"0pm");

                count++;
            }

            Bardataset = new BarDataSet(BARENTRY, "Average number of people");

            BARDATA = new BarData(BarEntryLabels, Bardataset);

            Bardataset.setColors(ColorTemplate.PASTEL_COLORS);

            chart.setData(BARDATA);
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

        Context context;

        public DownloadHeaderTask(ImageView bmImage, Context ctxt) {
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
            if(prog != null && prog.isShowing()){
                prog.dismiss ( ) ;
            }

            bmImage.setImageBitmap(result);
        }
    }

}
