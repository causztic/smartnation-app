package food;

/**
 * Created by Anwesha Biswas on 2/12/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by Anwesha Biswas on 27/11/2017.
 */

import android.widget.ImageButton;


import com.example.anweshabiswas.smartnation.R;

import java.util.List;

import models.FoodPlace;


/**
 * Created by AndroidJSon.com on 6/18/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static Context context;
    public static List<FoodPlace> MainFoodInfoList=new ArrayList<>();
    public static List<FoodPlace> OriginalFoodInfoList=new ArrayList<>();

    public static final String KEY="Info";

    public RecyclerViewAdapter(Context context)
    {
        super();
        setHasStableIds(true);

        OriginalFoodInfoList=MainFoodInfoList;
        this.context = context;

    }

    public RecyclerViewAdapter(List<FoodPlace> TempList)
    {

        MainFoodInfoList=TempList;
        OriginalFoodInfoList=MainFoodInfoList;
        Log.i("Anwesha",MainFoodInfoList.toString());
        setHasStableIds(true);


    }

    public RecyclerViewAdapter(Context context, List<FoodPlace> TempList)
    {

        MainFoodInfoList=TempList;
        OriginalFoodInfoList=MainFoodInfoList;
        Log.i("Anwesha",MainFoodInfoList.toString());
        setHasStableIds(true);
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        String url=MainFoodInfoList.get(position).getImage(); // change to getIcon
        Log.i("anwesha", url);
        new DownloadImageTask(holder.img).execute(url);



    }

    @Override
    public int getItemCount() {

        return MainFoodInfoList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageButton bmImage;

        public DownloadImageTask(ImageButton bmImage) {
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


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        private ImageButton img;

        public ViewHolder(View itemView) {
            super(itemView);
            int position = getAdapterPosition();
            img=(ImageButton)itemView.findViewById(R.id.buttonView);
            img.setOnClickListener(this);


        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.i("anwesha","pos="+position);
            Intent launchloc = new Intent(view.getContext(), IndividualFoodActivity.class);
            launchloc.putExtra(KEY,position);
            view.getContext().startActivity(launchloc);
        }



        }


    public void setFilter(ArrayList<FoodPlace> list)
    {
        if(list.size()==0)
            notifyDataSetChanged();

        else {
            MainFoodInfoList = new ArrayList<>();
            MainFoodInfoList.addAll(list);
            notifyDataSetChanged();
        }
    }
}