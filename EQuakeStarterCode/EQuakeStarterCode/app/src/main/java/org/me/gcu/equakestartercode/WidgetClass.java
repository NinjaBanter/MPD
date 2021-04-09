package org.me.gcu.equakestartercode;
// Mark Feeney - MaticulationNumber : s1921828

public class WidgetClass
{
    private String title;
    private String description;
    private String link;
    private String publishDate;
    private String categoryType;
    private String coordLat;
    private String coordLong;
    private String location;
    private String magnitude;

    public WidgetClass()
    {
        title = "";
        description = "";
        link = "";
        publishDate = "";
        categoryType = "";
        coordLat = "";
        coordLong = "";
    }

    public WidgetClass(String aTitle, String aDescription, String aLink, String aPublishDate, String aCategoryType, String aCoordLat, String aCoordLong)
    {
        title = aTitle;
        description = aDescription;
        link = aLink;
        publishDate = aPublishDate;
        categoryType = aCategoryType;
        coordLat = aCoordLat;
        coordLong = aCoordLong;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String Title)
    {
        title = Title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String aDescription)
    {
        description = aDescription;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String aLink)
    {
        link = aLink;
    }

    public String getPublishDate()
    {
        return publishDate;
    }

    public void setPublishDate(String aPublishDate)
    {
        publishDate = aPublishDate;
    }

    public String getCategoryType()
    {
        return categoryType;
    }

    public void setCategoryType(String aCategoryType)
    {
        categoryType = aCategoryType;
    }

    public String getCoordLat()
    {
        return coordLat;
    }

    public void setCoordLat(String aCoordLat)
    {
        coordLat = aCoordLat;
    }

    public String getCoordLong()
    {
        return coordLong;
    }

    public void setCoordLong(String aCoordLong)
    {
        coordLong = aCoordLong;
    }


    public String toString()
    {
        String temp;

        temp = title + " " + description + " " + link + " "  + publishDate + " " + categoryType + " " + coordLat + " " + coordLong;

        return temp;
    }

} // End of class

