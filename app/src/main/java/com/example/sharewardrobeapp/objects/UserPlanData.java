package com.example.sharewardrobeapp.objects;

public class UserPlanData
{
    private String FItemsSerialize;

    private String UserID;

    private String OutFitsSerialize;

    public String getFItemsSerialize ()
    {
        return FItemsSerialize;
    }

    public void setFItemsSerialize (String FItemsSerialize)
    {
        this.FItemsSerialize = FItemsSerialize;
    }

    public String getUserID ()
    {
        return UserID;
    }

    public void setUserID (String UserID)
    {
        this.UserID = UserID;
    }

    public String getOutFitsSerialize ()
    {
        return OutFitsSerialize;
    }

    public void setOutFitsSerialize (String OutFitsSerialize)
    {
        this.OutFitsSerialize = OutFitsSerialize;
    }

    @Override
    public String toString()
    {
        return "UserPlanData [FItemsSerialize = "+FItemsSerialize+", UserID = "+UserID+", OutFitsSerialize = "+OutFitsSerialize+"]";
    }
}
