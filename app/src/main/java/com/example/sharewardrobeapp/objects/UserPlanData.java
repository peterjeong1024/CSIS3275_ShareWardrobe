package com.example.sharewardrobeapp.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class UserPlanData implements Parcelable {
    private String _id;

    private String FItemsSerialize;

    private String UserID;

    private String OutFitsSerialize;

    protected UserPlanData(Parcel in) {
        _id = in.readString();
        FItemsSerialize = in.readString();
        UserID = in.readString();
        OutFitsSerialize = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(FItemsSerialize);
        dest.writeString(UserID);
        dest.writeString(OutFitsSerialize);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getFItemsSerialize() {
        return FItemsSerialize;
    }

    public void setFItemsSerialize(String FItemsSerialize) {
        this.FItemsSerialize = FItemsSerialize;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getOutFitsSerialize() {
        return OutFitsSerialize;
    }

    public void setOutFitsSerialize(String OutFitsSerialize) {
        this.OutFitsSerialize = OutFitsSerialize;
    }

    @Override
    public String toString() {
        return "UserPlanData [_id = " + _id + ", FItemsSerialize = " + FItemsSerialize + ", UserID = " + UserID + ", OutFitsSerialize = " + OutFitsSerialize + "]";
    }
}
