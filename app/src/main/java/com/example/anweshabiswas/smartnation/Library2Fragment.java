

package com.example.anweshabiswas.smartnation;

/**
 * Created by Anwesha Biswas on 30/10/2017.
 */
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.AsyncTask;
        import android.support.v4.app.Fragment;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.io.InputStream;
        import java.util.ArrayList;
        import com.github.mikephil.charting.charts.BarChart;
        import com.github.mikephil.charting.data.BarData;
        import com.github.mikephil.charting.data.BarDataSet;
        import com.github.mikephil.charting.data.BarEntry;
        import com.github.mikephil.charting.utils.ColorTemplate;






public class Library2Fragment extends Fragment{

    MeetingPlaces obj;

    ImageView header;
    TextView occ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_main3_activity_library2, container, false);
        BarChart chart ;
        ArrayList<BarEntry> BARENTRY ;
        ArrayList<String> BarEntryLabels ;
        BarDataSet Bardataset ;
        BarData BARDATA ;

        obj=LibraryMainActivity.getObj(1);
        String name= obj.getName();
        Log.i("Level2",name);
        String headerUrl=obj.getHeaderImage();


        occ=(TextView)rootView.findViewById(R.id.level2_occupancy);
        header =(ImageView)rootView.findViewById(R.id.librarylevel2button);

        new Library2Fragment.DownloadHeaderTask(header).execute(headerUrl);

        occ.setText(name+": BUSY");




        chart = (BarChart) rootView.findViewById(R.id.barchart);

        BARENTRY = new ArrayList<>();
        BARENTRY.add(new BarEntry(6f, 0));
        BARENTRY.add(new BarEntry(2f, 1));
        BARENTRY.add(new BarEntry(2f, 2));
        BARENTRY.add(new BarEntry(3f, 3));
        BARENTRY.add(new BarEntry(5f, 4));
        BARENTRY.add(new BarEntry(2f, 5));


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


        /*TextView occupancy = (TextView)rootView.findViewById(R.id.level2_occupancy);

        occupancy.setText("Vacancies available");*/
        chart.invalidate();
        return rootView;

    }

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
        }
    }




}

