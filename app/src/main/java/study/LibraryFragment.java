package study;

/**
 * Created by Anwesha Biswas on 30/10/2017.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.anweshabiswas.smartnation.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import json_objects.MeetingPlaces;


public class LibraryFragment extends Fragment
{
    private MeetingPlaces obj;
    private ImageView header;
    private TextView occ;
    private BarChart chart;
    private List<BarEntry> BARENTRY;
    private List<String> BarEntryLabels;
    private BarDataSet Bardataset;
    private BarData BARDATA;
    private TextView chartTitle;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity){
            a=(Activity) context;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_main3_activity_library, container, false);

        obj = (MeetingPlaces) getArguments().get("LIBRARY");

        String name= obj.getName();
        String headerUrl=obj.getHeaderImage();

        occ=(TextView)rootView.findViewById(R.id.level_occupancy);
        header =(ImageView)rootView.findViewById(R.id.librarylevelbutton);
        chartTitle = (TextView) rootView.findViewById(R.id.level_graphtitle);
        chartTitle.setText("Peak hours for " + obj.getName());


        new LibraryFragment.DownloadHeaderTask(header).execute(headerUrl);

        occ.setText(name+": BUSY");


        chart = (BarChart) rootView.findViewById(R.id.barchart);

        BARENTRY = new ArrayList<>();
        BARENTRY.add(new BarEntry(5f, 0));
        BARENTRY.add(new BarEntry(6f, 1));
        BARENTRY.add(new BarEntry(7f, 2));
        BARENTRY.add(new BarEntry(4f, 3));
        BARENTRY.add(new BarEntry(3f, 4));
        BARENTRY.add(new BarEntry(5f, 5));

        BarEntryLabels = new ArrayList<>();

        BarEntryLabels.add("3pm");
        BarEntryLabels.add("4pm");
        BarEntryLabels.add("5pm");
        BarEntryLabels.add("6pm");
        BarEntryLabels.add("7pm");
        BarEntryLabels.add("8pm");


        Bardataset = new BarDataSet(BARENTRY, "Occupancy");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(BARDATA);
        chart.invalidate();
        return rootView;

    }

    class DownloadHeaderTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadHeaderTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }





}
