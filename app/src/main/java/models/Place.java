package models;

import java.io.Serializable;

/**
 * Created by yaojie on 12/12/17.
 */

public abstract class Place implements Serializable {
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
