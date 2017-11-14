package com.example.anweshabiswas.smartnation;

/**
 * Created by Anwesha Biswas on 30/10/2017.
 */
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;






public class Library1 extends Fragment{



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        BarChart chart ;
        ArrayList<BarEntry> BARENTRY ;
        ArrayList<String> BarEntryLabels ;
        BarDataSet Bardataset ;
        BarData BARDATA ;


        View rootView = inflater.inflate(R.layout.fragment_main3_activity_library1, container, false);

        chart = (BarChart) rootView.findViewById(R.id.barchart);

        BARENTRY = new ArrayList<>();
        BARENTRY.add(new BarEntry(5f, 0));
        BARENTRY.add(new BarEntry(6f, 1));
        BARENTRY.add(new BarEntry(7f, 2));
        BARENTRY.add(new BarEntry(4f, 3));
        BARENTRY.add(new BarEntry(3f, 4));
        BARENTRY.add(new BarEntry(5f, 5));


        BarEntryLabels = new ArrayList<String>();

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


        TextView occupancy = (TextView)rootView.findViewById(R.id.level1_occupancy);

        occupancy.setText("Occupied");
        chart.invalidate();
        return rootView;

    }





}
