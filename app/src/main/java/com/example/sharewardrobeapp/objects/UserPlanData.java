package com.example.sharewardrobeapp.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class UserPlanData implements Parcelable {
    private String _id;

    private String UserID;

    private String FItemsSerialize;

    private String OutFitsSerialize;

    private String WornDate;

    public UserPlanData() {
    }

    public UserPlanData(String userID, String FItemsSerialize, String outFitsSerialize, String wornDate) {
        UserID = userID;
        this.FItemsSerialize = FItemsSerialize;
        OutFitsSerialize = outFitsSerialize;
        WornDate = wornDate;
    }

    protected UserPlanData(Parcel in) {
        _id = in.readString();
        UserID = in.readString();
        FItemsSerialize = in.readString();
        OutFitsSerialize = in.readString();
        WornDate = in.readString();
    }

    public static final Creator<UserPlanData> CREATOR = new Creator<UserPlanData>() {
        @Override
        public UserPlanData createFromParcel(Parcel in) {
            return new UserPlanData(in);
        }

        @Override
        public UserPlanData[] newArray(int size) {
            return new UserPlanData[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getFItemsSerialize() {
        return FItemsSerialize;
    }

    public void setFItemsSerialize(String FItemsSerialize) {
        this.FItemsSerialize = FItemsSerialize;
    }

    public String getOutFitsSerialize() {
        return OutFitsSerialize;
    }

    public void setOutFitsSerialize(String outFitsSerialize) {
        OutFitsSerialize = outFitsSerialize;
    }

    public String getWornDate() {
        return WornDate;
    }

    public void setWornDate(String wornDate) {
        WornDate = wornDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(UserID);
        parcel.writeString(FItemsSerialize);
        parcel.writeString(OutFitsSerialize);
        parcel.writeString(WornDate);
    }
}
