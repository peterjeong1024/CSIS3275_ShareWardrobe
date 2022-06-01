package com.example.sharewardrobeapp.objects;

public class OutfitItem
{
    private String FItemsSerialize;

    private String OutfitOwnerID;

    private String OutfitCateName;

    public String getFItemsSerialize ()
    {
        return FItemsSerialize;
    }

    public void setFItemsSerialize (String FItemsSerialize)
    {
        this.FItemsSerialize = FItemsSerialize;
    }

    public String getOutfitOwnerID ()
    {
        return OutfitOwnerID;
    }

    public void setOutfitOwnerID (String OutfitOwnerID)
    {
        this.OutfitOwnerID = OutfitOwnerID;
    }

    public String getOutfitCateName ()
    {
        return OutfitCateName;
    }

    public void setOutfitCateName (String OutfitCateName)
    {
        this.OutfitCateName = OutfitCateName;
    }

    @Override
    public String toString()
    {
        return "OutfitItem [FItemsSerialize = "+FItemsSerialize+", OutfitOwnerID = "+OutfitOwnerID+", OutfitCateName = "+OutfitCateName+"]";
    }
}
