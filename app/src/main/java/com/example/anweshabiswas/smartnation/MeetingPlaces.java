package com.example.anweshabiswas.smartnation;

import java.io.Serializable;

/**
 * Created by Anwesha Biswas on 3/12/2017.
 */

public class MeetingPlaces implements Serializable
{
    private int id;
    private String meetingCategory,name,image,headerImage;
    private double latitude,longitude;

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
     public String getMeetingCategory()
     {
         return meetingCategory;
     }
    public String getHeaderImage()
    {
        return headerImage;
    }
}
