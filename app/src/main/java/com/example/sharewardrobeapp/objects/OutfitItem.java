package com.example.sharewardrobeapp.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

public class OutfitItem implements Parcelable {
    private String _id;

    private String OutfitImg;

    private String FItemsSerialize;

    private String OutfitOwnerID;

    private String OutfitCateName;

    public OutfitItem() {
    }

    protected OutfitItem(Parcel in) {
        _id = in.readString();
        OutfitImg = in.readString();
        FItemsSerialize = in.readString();
        OutfitOwnerID = in.readString();
        OutfitCateName = in.readString();
    }

    public static final Creator<OutfitItem> CREATOR = new Creator<OutfitItem>() {
        @Override
        public OutfitItem createFromParcel(Parcel in) {
            return new OutfitItem(in);
        }

        @Override
        public OutfitItem[] newArray(int size) {
            return new OutfitItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(OutfitImg);
        parcel.writeString(FItemsSerialize);
        parcel.writeString(OutfitOwnerID);
        parcel.writeString(OutfitCateName);
    }
}
