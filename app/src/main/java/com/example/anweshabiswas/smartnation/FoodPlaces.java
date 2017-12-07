package com.example.anweshabiswas.smartnation;

/**
 * Created by Anwesha Biswas on 2/12/2017.
 */
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodPlaces implements Serializable{
    private int id;
    private double longitude,latitude;
    private String name,image,headerImage;

    public String getName()
    {
        return name;
    }
    public int getId()
    {
        return id;
    }

    public String getImage()
    {
        return image;
    }
    public String getHeaderImage()
    {
        return headerImage;
    }
}
