package com.example.sharewardrobeapp.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class OutfitItem {
    private String _id;

    private String OutfitImg;

    private String FItemsSerialize;

    private String OutfitOwnerID;

    private String OutfitCateName;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFItemsSerialize() {
        return FItemsSerialize;
    }

    public void setFItemsSerialize(String FItemsSerialize) {
        this.FItemsSerialize = FItemsSerialize;
    }

    public String getOutfitOwnerID() {
        return OutfitOwnerID;
    }

    public String getOutfitImg() {
        return OutfitImg;
    }

    public void setOutfitImg(String outfitImg) {
        OutfitImg = outfitImg;
    }

    public void setOutfitOwnerID(String OutfitOwnerID) {
        this.OutfitOwnerID = OutfitOwnerID;
    }

    public String getOutfitCateName() {
        return OutfitCateName;
    }

    public void setOutfitCateName(String OutfitCateName) {
        this.OutfitCateName = OutfitCateName;
    }

    public Bitmap getOutfitImgBitmap() {
        if (OutfitImg == null || OutfitImg == "") {
            return null;
        }

        byte[] bytes = Base64.decode(OutfitImg, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    @Override
    public String toString() {
        return "OutfitItem [_id = " + _id + ", FItemsSerialize = " + FItemsSerialize + ", OutfitOwnerID = " + OutfitOwnerID + ", OutfitCateName = " + OutfitCateName + "]";
    }
}
